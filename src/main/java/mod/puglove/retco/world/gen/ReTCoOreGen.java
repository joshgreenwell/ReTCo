package mod.puglove.retco.world.gen;

import mod.puglove.retco.ReTCo;
import mod.puglove.retco.config.ReTCoConfig;
import mod.puglove.retco.registries.ReTCoBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ReTCo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReTCoOreGen {

  @SubscribeEvent
  public static void generateOres(FMLLoadCompleteEvent event) {
    for (Biome biome : ForgeRegistries.BIOMES) {
      if (biome.getCategory() == Biome.Category.THEEND) {
      } else if (biome.getCategory() == Biome.Category.NETHER) {
      } else {
        genOre(biome, ReTCoConfig.world.dimensional_residue.perChunk.get(),
            ReTCoConfig.world.dimensional_residue.bottomOffset.get(),
            ReTCoConfig.world.dimensional_residue.topOffset.get(),
            ReTCoConfig.world.dimensional_residue.maxHeight.get(), OreFeatureConfig.FillerBlockType.NATURAL_STONE,
            ReTCoBlocks.DIMENSIONAL_RESIDUE_ORE.get().getDefaultState(),
            ReTCoConfig.world.dimensional_residue.maxVeinSize.get());
      }
    }
  }

  private static void genOre(Biome biome, int count, int bottomOffset, int topOffset, int max,
      OreFeatureConfig.FillerBlockType filler, BlockState defaultBlockState, int size) {
    CountRangeConfig range = new CountRangeConfig(count, bottomOffset, topOffset, max);
    OreFeatureConfig feature = new OreFeatureConfig(filler, defaultBlockState, size);
    ConfiguredPlacement config = Placement.COUNT_RANGE.configure(range);

    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
        Feature.ORE.withConfiguration(feature).withPlacement(config));
  }
}
