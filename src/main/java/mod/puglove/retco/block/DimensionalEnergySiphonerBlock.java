package mod.puglove.retco.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.api.distmarker.Dist;

import javax.annotation.Nullable;

import mod.puglove.retco.ReTCoModUtil;
import mod.puglove.retco.config.ReTCoConfig;
import mod.puglove.retco.registries.ReTCoTileEntityTypes;
import mod.puglove.retco.tile.DimensionalEnergySiphonerTileEntity;

import java.util.List;

public class DimensionalEnergySiphonerBlock extends HorizontalBlock {
  public DimensionalEnergySiphonerBlock(final Properties properties) {
    super(properties);
    
    this.setDefaultState(this.getDefaultState()
				.with(HORIZONTAL_FACING, Direction.NORTH)
		);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void addInformation(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    tooltip.add(new TranslationTextComponent("message:dimensional_energy_siphoner",
        Integer.toString(ReTCoConfig.machines.dimensionalEnergySiphonerGenerate.get())).mergeStyle(TextFormatting.GRAY));
  }

  @Override
  public boolean hasTileEntity(final BlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
    return ReTCoTileEntityTypes.DIMENSIONAL_ENERGY_SIPHONER.get().create();
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
      if (tileEntity instanceof DimensionalEnergySiphonerTileEntity)
        NetworkHooks.openGui((ServerPlayerEntity) player, (DimensionalEnergySiphonerTileEntity) tileEntity, pos);
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
    if (tileEntity instanceof DimensionalEnergySiphonerTileEntity)
      return ReTCoModUtil.calcRedstoneFromEnergyStorage(((DimensionalEnergySiphonerTileEntity) tileEntity).energy);
    return super.getComparatorInputOverride(blockState, worldIn, pos);
  }

  	/**
	 * Makes the block face the player when placed
	 */
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
  }
  
  /**
	 * Called from inside the constructor {@link Block#Block(Properties)} to add all the properties to our blockstate
	 */
	@Override
	protected void fillStateContainer(final StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(HORIZONTAL_FACING);
  }
  
  /**
	 * Returns the blockstate with the given rotation from the passed blockstate.
	 * If inapplicable, returns the passed blockstate.
	 *
	 * @deprecated call via {@link BlockState#rotate(Rotation)} whenever possible. Implementing/overriding is fine.
	 */
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(HORIZONTAL_FACING, rot.rotate(state.get(HORIZONTAL_FACING)));
  }
  
  /**
	 * Returns the blockstate with the given mirror of the passed blockstate.
	 * If inapplicable, returns the passed blockstate.
	 *
	 * @deprecated call via {@link BlockState#mirror(Mirror)} whenever possible. Implementing/overriding is fine.
	 */
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING)));
	}

}
