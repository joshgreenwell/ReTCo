package mod.puglove.retco.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mod.puglove.retco.ModUtil;
import mod.puglove.retco.registries.ModTileEntityTypes;
import mod.puglove.retco.tileentity.DimensionalEnergySiphonerMK2TileEntity;

import java.util.stream.Stream;

public class DimensionalEnergySiphonerMK2Block extends Block {
  private static final VoxelShape SHAPE = Stream
      .of(Block.makeCuboidShape(1, 0, 1, 15, 13, 15), Block.makeCuboidShape(2, 13, 2, 14, 14, 14))
      .reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

  public DimensionalEnergySiphonerMK2Block(final Properties properties) {
		super(properties);
	}

  @Override
  public boolean hasTileEntity(final BlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
    // Always use TileEntityType#create to allow registry overrides to work.
    return ModTileEntityTypes.DIMENSIONAL_ENERGY_SIPHONER_MK2.get().create();
  }

  /**
   * @deprecated Call via
   *             {@link BlockState#getShape(IBlockReader, BlockPos, ISelectionContext)}
   *             Implementing/overriding is fine.
   */
  @Nonnull
  @Override
  public VoxelShape getShape(final BlockState state, final IBlockReader worldIn, final BlockPos pos,
      final ISelectionContext context) {
    return SHAPE;
  }

  /**
   * Called on the logical server when a BlockState with a TileEntity is replaced
   * by another BlockState. We use this method to drop all the items from our tile
   * entity's inventory and update comparators near our block.
   *
   * @deprecated Call via
   *             {@link BlockState#onReplaced(World, BlockPos, BlockState, boolean)}
   *             Implementing/overriding is fine.
   */
  @Override
  public void onReplaced(BlockState oldState, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
    super.onReplaced(oldState, worldIn, pos, newState, isMoving);
  }

  /**
   * Called when a player right clicks our block. We use this method to open our
   * gui.
   *
   * @deprecated Call via
   *             {@link BlockState#onBlockActivated(World, PlayerEntity, Hand, BlockRayTraceResult)}
   *             whenever possible. Implementing/overriding is fine.
   */
  @Override
  public ActionResultType onBlockActivated(final BlockState state, final World worldIn, final BlockPos pos,
      final PlayerEntity player, final Hand handIn, final BlockRayTraceResult hit) {
    if (!worldIn.isRemote) {
      final TileEntity tileEntity = worldIn.getTileEntity(pos);
      if (tileEntity instanceof DimensionalEnergySiphonerMK2TileEntity)
        NetworkHooks.openGui((ServerPlayerEntity) player, (DimensionalEnergySiphonerMK2TileEntity) tileEntity, pos);
    }
    return ActionResultType.SUCCESS;
  }

  /**
   * We return the redstone calculated from our energy
   *
   * @deprecated call via
   *             {@link BlockState#getComparatorInputOverride(World, BlockPos)}
   *             whenever possible. Implementing/overriding is fine.
   */
  @Override
  public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
    final TileEntity tileEntity = worldIn.getTileEntity(pos);
    if (tileEntity instanceof DimensionalEnergySiphonerMK2TileEntity)
      return ModUtil.calcRedstoneFromEnergyStorage(((DimensionalEnergySiphonerMK2TileEntity) tileEntity).energy);
    return super.getComparatorInputOverride(blockState, worldIn, pos);
  }

}
