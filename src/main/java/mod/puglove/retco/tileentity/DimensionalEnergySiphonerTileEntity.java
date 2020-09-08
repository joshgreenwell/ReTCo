package mod.puglove.retco.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.puglove.retco.ModUtil;
import mod.puglove.retco.ReTCo;
import mod.puglove.retco.config.ReTCoConfig;
import mod.puglove.retco.container.DimensionalEnergySiphonerContainer;
import mod.puglove.retco.energy.SettableEnergyStorage;
import mod.puglove.retco.registries.ModBlocks;
import mod.puglove.retco.registries.ModTileEntityTypes;

public class DimensionalEnergySiphonerTileEntity extends TileEntity
    implements ITickableTileEntity, INamedContainerProvider {

  public static Logger logger = LogManager.getLogger(ReTCo.MOD_NAME);

  private static final String ENERGY_TAG = "energy";

  private final int energyGenerated = ReTCoConfig.machines.dimensionalEnergySiphonerGenerate.get();

  public final SettableEnergyStorage energy = new SettableEnergyStorage(
      ReTCoConfig.machines.dimensionalEnergySiphonerCapacity.get(),
      ReTCoConfig.machines.dimensionalEnergySiphonerMaxInput.get(),
      ReTCoConfig.machines.dimensionalEnergySiphonerMaxOutput.get());

  private final LazyOptional<EnergyStorage> energyCapabilityExternal = LazyOptional.of(() -> this.energy);
  private int lastEnergy = -1;

  public DimensionalEnergySiphonerTileEntity() {
    super(ModTileEntityTypes.DIMENSIONAL_ENERGY_SIPHONER.get());
  }

  @Override
  public void tick() {

    final World world = this.world;
    if (world == null || world.isRemote)
      return;

    final BlockPos pos = this.pos;
    final SettableEnergyStorage energy = this.energy;

    energy.receiveEnergy(energyGenerated, false);

    final int transferAmountPerTick = ReTCoConfig.machines.dimensionalEnergySiphonerTransferRate.get();
    for (Direction direction : ModUtil.DIRECTIONS) {
      final TileEntity te = world.getTileEntity(pos.offset(direction));
      if (te == null) {
        continue;
      }
      te.getCapability(CapabilityEnergy.ENERGY, direction).ifPresent(otherTileEnergy -> {
        if (!otherTileEnergy.canReceive()) {
          return;
        }
        energy.extractEnergy(otherTileEnergy.receiveEnergy(
            energy.extractEnergy(Math.min(energy.getEnergyStored(), transferAmountPerTick), true), false), false);
      });
    }

    if (lastEnergy != energy.getEnergyStored()) {
      this.markDirty();
      final BlockState blockState = this.getBlockState();
      world.notifyBlockUpdate(pos, blockState, blockState, 2);
      lastEnergy = energy.getEnergyStored();
    }

  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
    if (cap == CapabilityEnergy.ENERGY)
      return energyCapabilityExternal.cast();
    return super.getCapability(cap, side);
  }

  /**
   * Handle a packet created in {@link #getUpdatePacket()}
   */
  @Override
  public void onDataPacket(final NetworkManager net, final SUpdateTileEntityPacket pkt) {
    this.energy.setEnergy(pkt.getNbtCompound().getInt(ENERGY_TAG));
  }

  @Override
  public void onLoad() {
    super.onLoad();
    // We set this in onLoad instead of the constructor so that TileEntities
    // constructed from NBT (saved tile entities) have this set to the proper value
    if (world != null && !world.isRemote)
      lastEnergy = energy.getEnergyStored();
  }

  /**
   * Read saved data from disk into the tile.
   */
  @Override
  public void read(final CompoundNBT compound) {
    super.read(compound);
    this.energy.setEnergy(compound.getInt(ENERGY_TAG));
  }

  /**
   * Write data from the tile into a compound tag for saving to disk.
   */
  @Nonnull
  @Override
  public CompoundNBT write(final CompoundNBT compound) {
    super.write(compound);
    compound.putInt(ENERGY_TAG, this.energy.getEnergyStored());
    return compound;
  }

  /**
   * Retrieves packet to send to the client whenever this Tile Entity is re-synced
   * via World#notifyBlockUpdate. This packet comes back client-side in
   * {@link #onDataPacket}
   */
  @Nullable
  public SUpdateTileEntityPacket getUpdatePacket() {
    final CompoundNBT tag = new CompoundNBT();
    tag.putInt(ENERGY_TAG, this.energy.getEnergyStored());
    return new SUpdateTileEntityPacket(this.pos, 0, tag);
  }

  /**
   * Get an NBT compound to sync to the client with SPacketChunkData, used for
   * initial loading of the chunk or when many blocks change at once. This
   * compound comes back to you client-side in {@link #handleUpdateTag} The
   * default implementation ({@link TileEntity#handleUpdateTag}) calls
   * {@link #writeInternal)} which doesn't save any of our extra data so we
   * override it to call {@link #write} instead
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
    // We need to invalidate our capability references so that any cached references
    // (by other mods) don't
    // continue to reference our capabilities and try to use them and/or prevent
    // them from being garbage collected
    energyCapabilityExternal.invalidate();
  }

  @Nonnull
  @Override
  public ITextComponent getDisplayName() {
    return new TranslationTextComponent(ModBlocks.DIMENSIONAL_ENERGY_SIPHONER.get().getTranslationKey());
  }

  /**
   * Called from {@link NetworkHooks#openGui} (which is called from
   * {@link HeatCollectorBlock#onBlockActivated} on the logical server)
   *
   * @return The logical-server-side Container for this TileEntity
   */
  @Nonnull
  @Override
  public Container createMenu(final int windowId, final PlayerInventory inventory, final PlayerEntity player) {
    return new DimensionalEnergySiphonerContainer(windowId, inventory, this);
  }

}
