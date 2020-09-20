package mod.puglove.retco.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.fml.network.IContainerFactory;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.puglove.retco.ReTCo;
import mod.puglove.retco.registries.ReTCoBlocks;
import mod.puglove.retco.registries.ReTCoContainerTypes;
import mod.puglove.retco.tile.DimensionalEnergySiphonerMK4TileEntity;

import java.util.Objects;

public class DimensionalEnergySiphonerMK4Container extends Container {

  public static Logger logger = LogManager.getLogger(ReTCo.MOD_NAME);

  public final DimensionalEnergySiphonerMK4TileEntity tileEntity;
  private final IWorldPosCallable canInteractWithCallable;

  /**
   * Logical-client-side constructor, called from
   * {@link ContainerType#create(IContainerFactory)} Calls the logical-server-side
   * constructor with the TileEntity at the pos in the PacketBuffer
   */
  public DimensionalEnergySiphonerMK4Container(final int windowId, final PlayerInventory playerInventory,
      final PacketBuffer data) {
    this(windowId, playerInventory, getTileEntity(playerInventory, data));
  }

  /**
   * Constructor called logical-server-side from
   * {@link HeatCollectorTileEntity#createMenu} and logical-client-side from
   * {@link #HeatCollectorContainer(int, PlayerInventory, PacketBuffer)}
   */
  public DimensionalEnergySiphonerMK4Container(final int windowId, final PlayerInventory playerInventory,
      final DimensionalEnergySiphonerMK4TileEntity tileEntity) {
    super(ReTCoContainerTypes.DIMENSIONAL_ENERGY_SIPHONER_MK4.get(), windowId);
    this.tileEntity = tileEntity;
    this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

    // Add all the slots for the tileEntity's inventory and the playerInventory to
    // this container

    final int playerInventoryStartX = 8;
    final int playerInventoryStartY = 84;
    final int slotSizePlus2 = 18; // slots are 16x16, plus 2 (for spacing/borders) is 18x18

    // Player Top Inventory slots
    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 9; ++column) {
        this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, playerInventoryStartX + (column * slotSizePlus2),
            playerInventoryStartY + (row * slotSizePlus2)));
      }
    }

    final int playerHotbarY = playerInventoryStartY + slotSizePlus2 * 3 + 4;
    // Player Hotbar slots
    for (int column = 0; column < 9; ++column) {
      this.addSlot(new Slot(playerInventory, column, playerInventoryStartX + (column * slotSizePlus2), playerHotbarY));
    }
  }

  private static DimensionalEnergySiphonerMK4TileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
    Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
    Objects.requireNonNull(data, "data cannot be null!");
    final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
    if (tileAtPos instanceof DimensionalEnergySiphonerMK4TileEntity) {
      return (DimensionalEnergySiphonerMK4TileEntity) tileAtPos;
    }
    throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
  }

  @Override
  public boolean canInteractWith(@Nonnull final PlayerEntity player) {
    return isWithinUsableDistance(canInteractWithCallable, player, ReTCoBlocks.DIMENSIONAL_ENERGY_SIPHONER_MK4.get());
  }

}
