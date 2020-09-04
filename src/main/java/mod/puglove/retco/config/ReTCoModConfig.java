package mod.puglove.retco.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import mod.puglove.retco.ReTCo;

import java.nio.file.Path;
import java.util.function.Function;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.config.ConfigFileTypeHandler;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

/**
 * Custom {@link ModConfig} implementation that allows for rerouting the server config from being in the worlds folder to being in the normal config folder. This allows
 * for us to use the built in sync support, without the extra hassle of having to explain to people where the config file is, or require people in single player to edit
 * the config each time they make a new world.
 */
public class ReTCoModConfig extends ModConfig {

    private static final ReTCoConfigFileTypeHandler MEK_TOML = new ReTCoConfigFileTypeHandler();

    private final IReTCoConfig retcoConfig;

    public ReTCoModConfig(ModContainer container, IReTCoConfig config) {
        super(config.getConfigType(), config.getConfigSpec(), container, ReTCo.MOD_NAME + "/" + config.getFileName() + ".toml");
        this.retcoConfig = config;
    }

    @Override
    public ConfigFileTypeHandler getHandler() {
        return MEK_TOML;
    }

    public void clearCache() {
      retcoConfig.clearCache();
    }

    private static class ReTCoConfigFileTypeHandler extends ConfigFileTypeHandler {

        private static Path getPath(Path configBasePath) {
            //Intercept server config path reading for Mekanism configs and reroute it to the normal config directory
            if (configBasePath.endsWith("serverconfig")) {
                return FMLPaths.CONFIGDIR.get();
            }
            return configBasePath;
        }

        @Override
        public Function<ModConfig, CommentedFileConfig> reader(Path configBasePath) {
            return super.reader(getPath(configBasePath));
        }

        @Override
        public void unload(Path configBasePath, ModConfig config) {
            super.unload(getPath(configBasePath), config);
        }
    }
}