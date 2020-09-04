package mod.puglove.retco.world;

import java.util.Random;
import javax.annotation.Nullable;
import mod.puglove.retco.config.ReTCoConfig;
import mod.puglove.retco.config.WorldConfig.OreConfig;
import mod.puglove.retco.registries.ModBlocks;
import mod.puglove.retco.registries.ReTCoFeatures;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

public class GenHandler {

  private static ConfiguredFeature<?, ?> DIMENSIONAL_RESIDUE_FEATURE;
  private static ConfiguredFeature<?, ?> DIMENSIONAL_RESIDUE_RETROGEN_FEATURE;

  public static void setupWorldGeneration() {
    DIMENSIONAL_RESIDUE_FEATURE = getOreFeature(ModBlocks.DIMENSIONAL_RESIDUE_ORE.get(),
        ReTCoConfig.world.dimensional_residue, Feature.ORE);

    // Retrogen features
    if (ReTCoConfig.world.enableRegeneration.get()) {
      DIMENSIONAL_RESIDUE_RETROGEN_FEATURE = getOreFeature(ModBlocks.DIMENSIONAL_RESIDUE_ORE.get(),
          ReTCoConfig.world.dimensional_residue, ReTCoFeatures.ORE_RETROGEN.getFeature());
    }
    ForgeRegistries.BIOMES.forEach(biome -> {
      if (isValidBiome(biome)) {
        // Add ores
        addFeature(biome, DIMENSIONAL_RESIDUE_FEATURE);
      }
    });
  }

  private static boolean isValidBiome(Biome biome) {
    // If this does weird things to unclassified biomes (Category.NONE), then we
    // should also mark that biome as invalid
    return biome.getCategory() != Category.THEEND && biome.getCategory() != Category.NETHER;
  }

  private static void addFeature(Biome biome, @Nullable ConfiguredFeature<?, ?> feature) {
    if (feature != null) {
      biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, feature);
    }
  }

  @Nullable
  private static ConfiguredFeature<?, ?> getOreFeature(Block blockProvider, OreConfig oreConfig,
      Feature<OreFeatureConfig> feature) {
    if (oreConfig.shouldGenerate.get()) {
      return feature
          .withConfiguration(new OreFeatureConfig(FillerBlockType.NATURAL_STONE,
              blockProvider.getBlock().getDefaultState(), oreConfig.maxVeinSize.get()))
          .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(oreConfig.perChunk.get(),
              oreConfig.bottomOffset.get(), oreConfig.topOffset.get(), oreConfig.maxHeight.get())));
    }
    return null;
  }

  /**
   * @return True if some retro-generation happened, false otherwise
   */
  public static boolean generate(ServerWorld world, Random random, int chunkX, int chunkZ) {
    BlockPos blockPos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
    Biome biome = world.getBiome(blockPos);
    boolean generated = false;
    if (isValidBiome(biome) && world.chunkExists(chunkX, chunkZ)) {
      generated = placeFeature(DIMENSIONAL_RESIDUE_RETROGEN_FEATURE, world, random, blockPos);
    }
    return generated;
  }

  private static boolean placeFeature(@Nullable ConfiguredFeature<?, ?> feature, ServerWorld world, Random random,
      BlockPos blockPos) {
    if (feature != null) {
      feature.place(world, world.getChunkProvider().getChunkGenerator(), random, blockPos);
      return true;
    }
    return false;
  }
}