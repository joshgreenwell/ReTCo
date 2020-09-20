package mod.puglove.retco.config;

import java.nio.file.Path;
import mod.puglove.retco.ReTCo;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.loading.FMLPaths;

public class ReTCoConfigHelper {
  public static final Path CONFIG_DIR;

  static {
    CONFIG_DIR = FMLPaths.getOrCreateGameRelativePath(FMLPaths.CONFIGDIR.get().resolve(ReTCo.MOD_NAME), ReTCo.MOD_NAME);
  }

  /**
   * Creates a mod config so that
   * {@link net.minecraftforge.fml.config.ConfigTracker} will track it and sync
   * server configs from server to client.
   */
  public static void registerConfig(ModContainer modContainer, IReTCoConfig config) {
    ReTCoModConfig modConfig = new ReTCoModConfig(modContainer, config);
    if (config.addToContainer()) {
      modContainer.addConfig(modConfig);
    }
  }
}
