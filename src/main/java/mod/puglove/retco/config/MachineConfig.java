package mod.puglove.retco.config;

import mod.puglove.retco.config.value.CachedIntValue;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig.Type;

public final class MachineConfig extends BaseReTCoConfig {
  private final ForgeConfigSpec configSpec;

  public CachedIntValue dimensionalEnergySiphonerGenerate;
  public CachedIntValue dimensionalEnergySiphonerCapacity;
  public CachedIntValue dimensionalEnergySiphonerMaxInput;
  public CachedIntValue dimensionalEnergySiphonerMaxOutput;
  public CachedIntValue dimensionalEnergySiphonerTransferRate;

  public CachedIntValue dimensionalEnergySiphonerMK2Generate;
  public CachedIntValue dimensionalEnergySiphonerMK2Capacity;
  public CachedIntValue dimensionalEnergySiphonerMK2MaxInput;
  public CachedIntValue dimensionalEnergySiphonerMK2MaxOutput;
  public CachedIntValue dimensionalEnergySiphonerMK2TransferRate;

  public CachedIntValue dimensionalEnergySiphonerMK3Generate;
  public CachedIntValue dimensionalEnergySiphonerMK3Capacity;
  public CachedIntValue dimensionalEnergySiphonerMK3MaxInput;
  public CachedIntValue dimensionalEnergySiphonerMK3MaxOutput;
  public CachedIntValue dimensionalEnergySiphonerMK3TransferRate;

  public CachedIntValue compressorCostPerTick;
  public CachedIntValue compressorCapacity;
  public CachedIntValue compressorMaxInput;

  MachineConfig() {
    ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    builder.comment("Machine settings config. This config is synced from server to client.").push("machines");

    dimensionalEnergySiphonerGenerate = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner will generate per tick.")
            .defineInRange("dimensionalEnergySiphonerGenerate", 20, 0, 100));
    dimensionalEnergySiphonerCapacity = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner can hold.")
            .defineInRange("dimensionalEnergySiphonerCapacity", 100_000, 10_000, 1_000_000));
    dimensionalEnergySiphonerMaxInput = CachedIntValue.wrap(this,
        builder.comment("Determines the maximum energy the dimensional energy siphoner can receive.")
            .defineInRange("dimensionalEnergySiphonerMaxInput", 1_000, 100, 10_000));
    dimensionalEnergySiphonerMaxOutput = CachedIntValue.wrap(this,
        builder.comment("Determines the maximum energy the dimensional energy siphoner can output.")
            .defineInRange("dimensionalEnergySiphonerMaxOutput", 1_000, 100, 10_000));
    dimensionalEnergySiphonerTransferRate = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner will generate per tick.")
            .defineInRange("dimensionalEnergySiphonerTransferRate", 1_000, 100, 10_000));

    dimensionalEnergySiphonerMK2Generate = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk2 will generate per tick.")
            .defineInRange("dimensionalEnergySiphonerMK2Generate", 100, 0, 1_000));
    dimensionalEnergySiphonerMK2Capacity = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk2 can hold.")
            .defineInRange("dimensionalEnergySiphonerMK2Capacity", 1_000_000, 100_000, 10_000_000));
    dimensionalEnergySiphonerMK2MaxInput = CachedIntValue.wrap(this,
        builder.comment("Determines the maximum energy the dimensional energy siphoner mk2 can receive.")
            .defineInRange("dimensionalEnergySiphonerMK2MaxInput", 10_000, 1_000, 100_000));
    dimensionalEnergySiphonerMK2MaxOutput = CachedIntValue.wrap(this,
        builder.comment("Determines the maximum energy the dimensional energy siphoner mk2 can output.")
            .defineInRange("dimensionalEnergySiphonerMK2MaxOutput", 10_000, 1_000, 100_000));
    dimensionalEnergySiphonerMK2TransferRate = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk2 will generate per tick.")
            .defineInRange("dimensionalEnergySiphonerMK2TransferRate", 10_000, 1_000, 100_000));

    dimensionalEnergySiphonerMK3Generate = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk3 will generate per tick.")
            .defineInRange("dimensionalEnergySiphonerMK3Generate", 500, 0, 100_000));
    dimensionalEnergySiphonerMK3Capacity = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk3 can hold.")
            .defineInRange("dimensionalEnergySiphonerMK3Capacity", 3_000_000, 1_000_000, 100_000_000));
    dimensionalEnergySiphonerMK3MaxInput = CachedIntValue.wrap(this,
        builder.comment("Determines the maximum energy the dimensional energy siphoner mk3 can receive.")
            .defineInRange("dimensionalEnergySiphonerMK3MaxInput", 25_000, 10_000, 100_000));
    dimensionalEnergySiphonerMK3MaxOutput = CachedIntValue.wrap(this,
        builder.comment("Determines the maximum energy the dimensional energy siphoner mk3 can output.")
            .defineInRange("dimensionalEnergySiphonerMK3MaxOutput", 25_000, 10_000, 100_000));
    dimensionalEnergySiphonerMK3TransferRate = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk3 will generate per tick.")
            .defineInRange("dimensionalEnergySiphonerMK3TransferRate", 25_000, 10_000, 100_000));

    compressorCostPerTick = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner will generate per tick.")
            .defineInRange("dimensionalEnergySiphonerMK3Generate", 20, 0, 1_000));
    compressorCapacity = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner can hold.")
            .defineInRange("dimensionalEnergySiphonerMK3Capacity", 50_000, 10_000, 100_000));
    compressorMaxInput = CachedIntValue.wrap(this,
        builder.comment("Determines the maximum energy the dimensional energy siphoner can receive.")
            .defineInRange("dimensionalEnergySiphonerMK3MaxInput", 1_000, 100, 10_000));

    builder.pop();
    configSpec = builder.build();
  }

  @Override
  public String getFileName() {
    return "machines";
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
}
