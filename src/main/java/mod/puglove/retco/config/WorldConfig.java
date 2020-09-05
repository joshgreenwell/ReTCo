package mod.puglove.retco.config;

import mod.puglove.retco.config.value.CachedBooleanValue;
import mod.puglove.retco.config.value.CachedIntValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig.Type;

public final class WorldConfig extends BaseReTCoConfig {
  private final ForgeConfigSpec configSpec;
  public final CachedBooleanValue enableRegeneration;
  public final CachedIntValue userGenVersion;

  public final OreConfig dimensional_residue;

  WorldConfig() {
    ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    builder.comment("World generation settings for Mekanism. This config is not synced from server to client")
        .push("world_generation");
    enableRegeneration = CachedBooleanValue.wrap(this, builder.comment("Allows chunks to retrogen Mekanism ore blocks.")
        .worldRestart().define("enableRegeneration", false));
    userGenVersion = CachedIntValue.wrap(this,
        builder.comment("Change this value to cause Mekanism to regen its ore in all loaded chunks.")
            .defineInRange("userWorldGenVersion", 0, 0, Integer.MAX_VALUE));
    dimensional_residue = new OreConfig(this, builder, "dimensional_residue", true, 2, 3, 0, 0, 30);
    builder.pop();
    configSpec = builder.build();
  }

  @Override
  public String getFileName() {
    return "world";
  }

  @Override
  public ForgeConfigSpec getConfigSpec() {
    return configSpec;
  }

  @Override
  public Type getConfigType() {
    return Type.COMMON;
  }

  @Override
  public boolean addToContainer() {
    return false;
  }

  public static class OreConfig {

    public final CachedBooleanValue shouldGenerate;
    public final CachedIntValue perChunk;
    public final CachedIntValue maxVeinSize;
    public final CachedIntValue bottomOffset;
    public final CachedIntValue topOffset;
    public final CachedIntValue maxHeight;

    private OreConfig(IReTCoConfig config, ForgeConfigSpec.Builder builder, String ore, boolean shouldGenerate,
        int perChunk, int maxVeinSize, int bottomOffset, int topOffset, int maxHeight) {
      builder.comment("Generation Settings for " + ore + " ore.").push(ore);
      this.shouldGenerate = CachedBooleanValue.wrap(config,
          builder.comment("Determines if " + ore + " ore should be added to world generation.").define("shouldGenerate",
              shouldGenerate));
      // The max for perChunk and vein size are the values of the max number of blocks
      // in a chunk.
      // TODO: Improve upon it at some point so that the max vein size then gets
      // determined by per chunk as well
      this.perChunk = CachedIntValue.wrap(config,
          builder.comment("Chance that " + ore + " generates in a chunk.").defineInRange("perChunk", perChunk, 1, 512));
      this.maxVeinSize = CachedIntValue.wrap(config,
          builder.comment("Maximum number of blocks in a vein of " + ore + ".").defineInRange("maxVeinSize",
              maxVeinSize, 1, 512));
      // TODO: See if we can use world.getHeight() somehow
      this.maxHeight = CachedIntValue.wrap(config,
          builder
              .comment("Base maximum height (exclusive) that veins of " + ore
                  + " can spawn. Height is calculated by: random.nextInt(maxHeight - topOffset) + bottomOffset")
              .defineInRange("maxHeight", maxHeight, 1, 256));
      this.topOffset = CachedIntValue.wrap(config,
          builder
              .comment("Top offset for calculating height that veins of " + ore
                  + " can spawn. Height is calculated by: random.nextInt(maxHeight - topOffset) + bottomOffset")
              .define("topOffset", topOffset, value -> {
                if (value instanceof Integer) {
                  int val = (int) value;
                  return val >= 0 && val < this.maxHeight.get();
                }
                return false;
              }));
      this.bottomOffset = CachedIntValue.wrap(config,
          builder
              .comment("Bottom offset for calculating height that veins of " + ore
                  + " can spawn. Height is calculated by: random.nextInt(maxHeight - topOffset) + bottomOffset")
              .define("bottomOffset", bottomOffset, value -> {
                if (value instanceof Integer) {
                  int val = (int) value;
                  return val >= 0 && val <= 256 - this.maxHeight.get() + this.topOffset.get();
                }
                return false;
              }));
      builder.pop();
    }
  }
}
