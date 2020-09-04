package mod.puglove.retco.registries;

import mod.puglove.retco.ReTCo;
import mod.puglove.retco.registration.impl.FeatureDeferredRegister;
import mod.puglove.retco.registration.impl.FeatureRegistryObject;
import mod.puglove.retco.world.OreRetrogenFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class ReTCoFeatures {
  public static final FeatureDeferredRegister FEATURES = new FeatureDeferredRegister(ReTCo.MODID);

  public static final FeatureRegistryObject<OreFeatureConfig, OreRetrogenFeature> ORE_RETROGEN = FEATURES
      .register("ore_retrogen", () -> new OreRetrogenFeature(OreFeatureConfig::deserialize));
}
