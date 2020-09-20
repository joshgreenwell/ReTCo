package mod.puglove.retco.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class DimensionalResidueOre extends OreBlock {

  public DimensionalResidueOre(Properties properties) {
    super(properties);
  }

  @Override
  public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
    return super.getExpDrop(state, reader, pos, fortune, silktouch);
  }
}
