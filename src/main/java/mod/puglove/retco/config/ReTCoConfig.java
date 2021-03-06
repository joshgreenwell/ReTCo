package mod.puglove.retco.config;

import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;

public final class ReTCoConfig {
  public static final MachineConfig machines = new MachineConfig();
  public static final WorldConfig world = new WorldConfig();

  public static void registerConfigs(ModLoadingContext modLoadingContext) {
    ModContainer modContainer = modLoadingContext.getActiveContainer();
    
    ReTCoConfigHelper.registerConfig(modContainer, machines);
    ReTCoConfigHelper.registerConfig(modContainer, world);
}
}
