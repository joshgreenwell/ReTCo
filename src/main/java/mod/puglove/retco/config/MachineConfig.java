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

  public CachedIntValue dimensionalEnergySiphonerMK4Generate;
  public CachedIntValue dimensionalEnergySiphonerMK4Capacity;
  public CachedIntValue dimensionalEnergySiphonerMK4MaxInput;
  public CachedIntValue dimensionalEnergySiphonerMK4MaxOutput;
  public CachedIntValue dimensionalEnergySiphonerMK4TransferRate;

  public CachedIntValue dimensionalEnergySiphonerMK5Generate;
  public CachedIntValue dimensionalEnergySiphonerMK5Capacity;
  public CachedIntValue dimensionalEnergySiphonerMK5MaxInput;
  public CachedIntValue dimensionalEnergySiphonerMK5MaxOutput;
  public CachedIntValue dimensionalEnergySiphonerMK5TransferRate;

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
            .defineInRange("dimensionalEnergySiphonerMK2Generate", 100, 0, 500));
    dimensionalEnergySiphonerMK2Capacity = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk2 can hold.")
            .defineInRange("dimensionalEnergySiphonerMK2Capacity", 1_000_000, 100_000, 10_000_000));
    dimensionalEnergySiphonerMK2MaxInput = CachedIntValue.wrap(this,
        builder.comment("Determines the maximum energy the dimensional energy siphoner mk2 can receive.")
            .defineInRange("dimensionalEnergySiphonerMK2MaxInput", 10_000, 1000, 25_000));
    dimensionalEnergySiphonerMK2MaxOutput = CachedIntValue.wrap(this,
        builder.comment("Determines the maximum energy the dimensional energy siphoner mk2 can output.")
            .defineInRange("dimensionalEnergySiphonerMK2MaxOutput", 10_000, 1000, 25_000));
    dimensionalEnergySiphonerMK2TransferRate = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk2 will generate per tick.")
            .defineInRange("dimensionalEnergySiphonerMK2TransferRate", 10_000, 100, 25_000));

    dimensionalEnergySiphonerMK3Generate = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk3 will generate per tick.")
            .defineInRange("dimensionalEnergySiphonerMK3Generate", 500, 100, 1000));
    dimensionalEnergySiphonerMK3Capacity = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk3 can hold.")
            .defineInRange("dimensionalEnergySiphonerMK3Capacity", 3_000_000, 100_000, 50_000_000));
    dimensionalEnergySiphonerMK3MaxInput = CachedIntValue.wrap(this,
        builder.comment("Determines the maximum energy the dimensional energy siphoner mk3 can receive.")
            .defineInRange("dimensionalEnergySiphonerMK3MaxInput", 25_000, 10_000, 100_000));
    dimensionalEnergySiphonerMK3MaxOutput = CachedIntValue.wrap(this,
        builder.comment("Determines the maximum energy the dimensional energy siphoner mk3 can output.")
            .defineInRange("dimensionalEnergySiphonerMK3MaxOutput", 25_000, 10_000, 100_000));
    dimensionalEnergySiphonerMK3TransferRate = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk3 will generate per tick.")
            .defineInRange("dimensionalEnergySiphonerMK3TransferRate", 25_000, 10_000, 100_000));

    dimensionalEnergySiphonerMK4Generate = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk4 will generate per tick.")
            .defineInRange("dimensionalEnergySiphonerMK4Generate", 2500, 500, 5000));
    dimensionalEnergySiphonerMK4Capacity = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk4 can hold.")
            .defineInRange("dimensionalEnergySiphonerMK4Capacity", 10_000_000, 1_000_000, 100_000_000));
    dimensionalEnergySiphonerMK4MaxInput = CachedIntValue.wrap(this,
        builder.comment("Determines the maximum energy the dimensional energy siphoner mk4 can receive.")
            .defineInRange("dimensionalEnergySiphonerMK4MaxInput", 100_000, 50_000, 100_000));
    dimensionalEnergySiphonerMK4MaxOutput = CachedIntValue.wrap(this,
        builder.comment("Determines the maximum energy the dimensional energy siphoner mk4 can output.")
            .defineInRange("dimensionalEnergySiphonerMK4MaxOutput", 100_000, 50_000, 100_000));
    dimensionalEnergySiphonerMK4TransferRate = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk4 will generate per tick.")
            .defineInRange("dimensionalEnergySiphonerMK4TransferRate", 100_000, 50_000, 100_000));

    dimensionalEnergySiphonerMK5Generate = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk5 will generate per tick.")
            .defineInRange("dimensionalEnergySiphonerMK5Generate", 12500, 5000, 50000));
    dimensionalEnergySiphonerMK5Capacity = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk5 can hold.")
            .defineInRange("dimensionalEnergySiphonerMK5Capacity", 100_000_000, 10_000_000, 1_000_000_000));
    dimensionalEnergySiphonerMK5MaxInput = CachedIntValue.wrap(this,
        builder.comment("Determines the maximum energy the dimensional energy siphoner mk5 can receive.")
            .defineInRange("dimensionalEnergySiphonerMK5MaxInput", 250_000, 100_000, 500_000));
    dimensionalEnergySiphonerMK5MaxOutput = CachedIntValue.wrap(this,
        builder.comment("Determines the maximum energy the dimensional energy siphoner mk5 can output.")
            .defineInRange("dimensionalEnergySiphonerMK5MaxOutput", 250_000, 100_000, 500_000));
    dimensionalEnergySiphonerMK5TransferRate = CachedIntValue.wrap(this,
        builder.comment("Determines how much energy the dimensional energy siphoner mk5 will generate per tick.")
            .defineInRange("dimensionalEnergySiphonerMK5TransferRate", 250_000, 100_000, 500_000));

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
