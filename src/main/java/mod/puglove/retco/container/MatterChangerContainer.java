package mod.puglove.retco.container;

import java.util.Objects;

import javax.annotation.Nonnull;

import mod.puglove.retco.registries.ModBlocks;
import mod.puglove.retco.registries.ModContainerTypes;
import mod.puglove.retco.tileentity.MatterChangerTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.items.SlotItemHandler;

public class MatterChangerContainer extends Container{
  public final MatterChangerTileEntity tileEntity;
	private final IWorldPosCallable canInteractWithCallable;

	/**
	 * Logical-client-side constructor, called from {@link ContainerType#create(IContainerFactory)}
	 * Calls the logical-server-side constructor with the TileEntity at the pos in the PacketBuffer
	 */
	public MatterChangerContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}

	/**
	 * Constructor called logical-server-side from {@link MatterChangerTileEntity#createMenu}
	 * and logical-client-side from {@link #MatterChangerContainer(int, PlayerInventory, PacketBuffer)}
	 */
	public MatterChangerContainer(final int windowId, final PlayerInventory playerInventory, final MatterChangerTileEntity tileEntity) {
		super(ModContainerTypes.MATTER_CHANGER.get(), windowId);
		this.tileEntity = tileEntity;
		this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

		// Add tracking for data (Syncs to client/updates value when it changes)
		this.trackInt(new FunctionalIntReferenceHolder(() -> tileEntity.smeltTimeLeft, v -> tileEntity.smeltTimeLeft = (short) v));
		this.trackInt(new FunctionalIntReferenceHolder(() -> tileEntity.maxSmeltTime, v -> tileEntity.maxSmeltTime = (short) v));

		// Add all the slots for the tileEntity's inventory and the playerInventory to this container

		// Tile inventory slot(s)
		this.addSlot(new SlotItemHandler(tileEntity.inventory, MatterChangerTileEntity.INPUT_SLOT, 56, 35));
		this.addSlot(new SlotItemHandler(tileEntity.inventory, MatterChangerTileEntity.OUTPUT_SLOT, 116, 35));

		final int playerInventoryStartX = 8;
		final int playerInventoryStartY = 84;
		final int slotSizePlus2 = 18; // slots are 16x16, plus 2 (for spacing/borders) is 18x18

		// Player Top Inventory slots
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, playerInventoryStartX + (column * slotSizePlus2), playerInventoryStartY + (row * slotSizePlus2)));
			}
		}

		final int playerHotbarY = playerInventoryStartY + slotSizePlus2 * 3 + 4;
		// Player Hotbar slots
		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(playerInventory, column, playerInventoryStartX + (column * slotSizePlus2), playerHotbarY));
		}
	}

	private static MatterChangerTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
		Objects.requireNonNull(data, "data cannot be null!");
		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof MatterChangerTileEntity)
			return (MatterChangerTileEntity) tileAtPos;
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}

	/**
	 * Generic & dynamic version of {@link Container#transferStackInSlot(PlayerEntity, int)}.
	 * Handle when the stack in slot {@code index} is shift-clicked.
	 * Normally this moves the stack between the player inventory and the other inventory(s).
	 *
	 * @param player the player passed in
	 * @param index  the index passed in
	 * @return the {@link ItemStack}
	 */
	@Nonnull
	@Override
	public ItemStack transferStackInSlot(final PlayerEntity player, final int index) {
		ItemStack returnStack = ItemStack.EMPTY;
		final Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			final ItemStack slotStack = slot.getStack();
			returnStack = slotStack.copy();

			final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
			if (index < containerSlots) {
				if (!mergeItemStack(slotStack, containerSlots, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(slotStack, 0, containerSlots, false)) {
				return ItemStack.EMPTY;
			}
			if (slotStack.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (slotStack.getCount() == returnStack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, slotStack);
		}
		return returnStack;
	}

	@Override
	public boolean canInteractWith(@Nonnull final PlayerEntity player) {
		return isWithinUsableDistance(canInteractWithCallable, player, ModBlocks.MATTER_CHANGER.get());
	}

}