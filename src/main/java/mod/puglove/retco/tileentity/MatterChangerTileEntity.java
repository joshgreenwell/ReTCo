package mod.puglove.retco.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import mod.puglove.retco.container.MatterChangerContainer;
import mod.puglove.retco.energy.SettableEnergyStorage;
import mod.puglove.retco.registries.ModBlocks;
import mod.puglove.retco.registries.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

public class MatterChangerTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
  public static final int INPUT_SLOT = 0;
	public static final int OUTPUT_SLOT = 1;

	private static final String INVENTORY_TAG = "inventory";
	private static final String SMELT_TIME_LEFT_TAG = "smeltTimeLeft";
  private static final String MAX_SMELT_TIME_TAG = "maxSmeltTime";
  private static final String MATTER_COLLECTED_TAG = "matterCollected";
	public final ItemStackHandler inventory = new ItemStackHandler(2) {
		@Override
		public boolean isItemValid(final int slot, @Nonnull final ItemStack stack) {
			switch (slot) {
				case INPUT_SLOT:
					return isInput(stack);
				case OUTPUT_SLOT:
					return isOutput(stack);
				default:
					return false;
			}
		}

		@Override
		protected void onContentsChanged(final int slot) {
			super.onContentsChanged(slot);
			MatterChangerTileEntity.this.markDirty();
		}
	};

	// Store the capability lazy optionals as fields to keep the amount of objects we use to a minimum
	private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
	// Machines (hoppers, pipes) connected to this furnace's top can only insert/extract items from the input slot
	private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalUpAndSides = LazyOptional.of(() -> new RangedWrapper(this.inventory, INPUT_SLOT, INPUT_SLOT + 1));
	// Machines (hoppers, pipes) connected to this furnace's bottom can only insert/extract items from the output slot
	private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalDown = LazyOptional.of(() -> new RangedWrapper(this.inventory, OUTPUT_SLOT, OUTPUT_SLOT + 1));

	public short smeltTimeLeft = -1;
	public short maxSmeltTime = -1;
  private int lastMatterCollected = 0;
  private int matterCollected = 0;

	public MatterChangerTileEntity() {
		super(ModTileEntityTypes.MATTER_CHANGER.get());
  }
  
  public int getMatterStored() {
    return matterCollected;
  }

	/**
	 * @return If the stack is not empty and has a smelting recipe associated with it
	 */
	private boolean isInput(final ItemStack stack) {
		if (stack.isEmpty())
			return false;
		return true; // getRecipe(stack).isPresent();
	}

	/**
	 * @return If the stack's item is equal to the result of smelting our input
	 */
	private boolean isOutput(final ItemStack stack) {
		final Optional<ItemStack> result = getResult(inventory.getStackInSlot(INPUT_SLOT));
		return result.isPresent() && ItemStack.areItemsEqual(result.get(), stack);
	}

	/**
	 * @return The smelting recipe for the input stack
	 */
	private Optional<FurnaceRecipe> getRecipe(final ItemStack input) {
		// Due to vanilla's code we need to pass an IInventory into RecipeManager#getRecipe so we make one here.
		return getRecipe(new Inventory(input));
	}

	/**
	 * @return The smelting recipe for the inventory
	 */
	private Optional<FurnaceRecipe> getRecipe(final IInventory inventory) {
		return world.getRecipeManager().getRecipe(IRecipeType.SMELTING, inventory, world);
	}

	/**
	 * @return The result of smelting the input stack
	 */
	private Optional<ItemStack> getResult(final ItemStack input) {
		// Due to vanilla's code we need to pass an IInventory into RecipeManager#getRecipe and
		// AbstractCookingRecipe#getCraftingResult() so we make one here.
		final Inventory dummyInventory = new Inventory(input);
		return getRecipe(dummyInventory).map(recipe -> recipe.getCraftingResult(dummyInventory));
	}

	/**
	 * Called every tick to update our tile entity
	 */
	@Override
	public void tick() {
		if (world == null || world.isRemote)
			return;

		final ItemStack input = inventory.getStackInSlot(INPUT_SLOT).copy();

		if (!input.isEmpty()) {
      if (smeltTimeLeft == -1) {
        smeltTimeLeft = maxSmeltTime = 1;
      } else {
        --smeltTimeLeft;
        if (smeltTimeLeft == 0) {
          ++matterCollected;
          if (input.hasContainerItem())
            inventory.setStackInSlot(INPUT_SLOT, input.getContainerItem());
          else {
            input.shrink(1);
            inventory.setStackInSlot(INPUT_SLOT, input); // Update the data
          }
          smeltTimeLeft = -1; // Set to -1 so we smelt the next stack on the next tick
        }
      }
    }

		if (lastMatterCollected != this.getMatterStored()) {
			this.markDirty();
			final BlockState blockState = this.getBlockState();
			world.notifyBlockUpdate(pos, blockState, blockState, 2);
      lastMatterCollected = this.getMatterStored();
		}

	}

	/**
	 * Mimics the code in {@link AbstractFurnaceTileEntity#func_214005_h()}
	 *
	 * @return The custom smelt time or 200 if there is no recipe for the input
	 */
	private short getSmeltTime(final ItemStack input) {
		return getRecipe(input)
				.map(AbstractCookingRecipe::getCookTime)
				.orElse(200)
				.shortValue();
	}

	/**
	 * Retrieves the Optional handler for the capability requested on the specific side.
	 *
	 * @param cap  The capability to check
	 * @param side The Direction to check from. CAN BE NULL! Null is defined to represent 'internal' or 'self'
	 * @return The requested an optional holding the requested capability.
	 */
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (side == null)
				return inventoryCapabilityExternal.cast();
			switch (side) {
				case DOWN:
					return inventoryCapabilityExternalDown.cast();
				case UP:
				case NORTH:
				case SOUTH:
				case WEST:
				case EAST:
					return inventoryCapabilityExternalUpAndSides.cast();
			}
		}
		return super.getCapability(cap, side);
  }
  
  /**
	 * Handle a packet created in {@link #getUpdatePacket()}
	 */
	@Override
	public void onDataPacket(final NetworkManager net, final SUpdateTileEntityPacket pkt) {
		this.matterCollected = pkt.getNbtCompound().getInt(MATTER_COLLECTED_TAG);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		// We set this in onLoad instead of the constructor so that TileEntities
		// constructed from NBT (saved tile entities) have this set to the proper value
		if (world != null && !world.isRemote)
			lastMatterCollected = this.getMatterStored();
	}

	/**
	 * Read saved data from disk into the tile.
	 */
	@Override
	public void read(final CompoundNBT compound) {
		super.read(compound);
		this.inventory.deserializeNBT(compound.getCompound(INVENTORY_TAG));
		this.smeltTimeLeft = compound.getShort(SMELT_TIME_LEFT_TAG);
		this.maxSmeltTime = compound.getShort(MAX_SMELT_TIME_TAG);
		this.matterCollected = compound.getInt(MATTER_COLLECTED_TAG);
	}

	/**
	 * Write data from the tile into a compound tag for saving to disk.
	 */
	@Nonnull
	@Override
	public CompoundNBT write(final CompoundNBT compound) {
		super.write(compound);
		compound.put(INVENTORY_TAG, this.inventory.serializeNBT());
		compound.putShort(SMELT_TIME_LEFT_TAG, this.smeltTimeLeft);
		compound.putShort(MAX_SMELT_TIME_TAG, this.maxSmeltTime);
		compound.putInt(MATTER_COLLECTED_TAG, this.getMatterStored());
		return compound;
	}

	/**
	 * Retrieves packet to send to the client whenever this Tile Entity is re-synced via World#notifyBlockUpdate.
	 * This packet comes back client-side in {@link #onDataPacket}
	 */
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		final CompoundNBT tag = new CompoundNBT();
		tag.putInt(MATTER_COLLECTED_TAG, this.getMatterStored());
		// We pass 0 for tileEntityTypeIn because we have a modded TE. See ClientPlayNetHandler#handleUpdateTileEntity(SUpdateTileEntityPacket)
		return new SUpdateTileEntityPacket(this.pos, 0, tag);
	}

	/**
	 * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the
	 * chunk or when many blocks change at once.
	 * This compound comes back to you client-side in {@link #handleUpdateTag}
	 * The default implementation ({@link TileEntity#handleUpdateTag}) calls {@link #writeInternal)}
	 * which doesn't save any of our extra data so we override it to call {@link #write} instead
	 */
	@Nonnull
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	/**
	 * Invalidates our tile entity
	 */
	@Override
	public void remove() {
		super.remove();
		// We need to invalidate our capability references so that any cached references (by other mods) don't
		// continue to reference our capabilities and try to use them and/or prevent them from being garbage collected
		inventoryCapabilityExternal.invalidate();
	}

	@Nonnull
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent(ModBlocks.MATTER_CHANGER.get().getTranslationKey());
	}

	/**
	 * Called from {@link NetworkHooks#openGui}
	 * (which is called from {@link ElectricFurnaceBlock#onBlockActivated} on the logical server)
	 *
	 * @return The logical-server-side Container for this TileEntity
	 */
	@Nonnull
	@Override
	public Container createMenu(final int windowId, final PlayerInventory inventory, final PlayerEntity player) {
		return new MatterChangerContainer(windowId, inventory, this);
	}
}
