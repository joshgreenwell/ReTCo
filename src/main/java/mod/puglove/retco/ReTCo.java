package mod.puglove.retco;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import mod.puglove.retco.config.ReTCoConfig;
import mod.puglove.retco.config.ReTCoModConfig;
import mod.puglove.retco.registries.ReTCoBlocks;
import mod.puglove.retco.registries.ReTCoContainerTypes;
import mod.puglove.retco.registries.ReTCoItems;
import mod.puglove.retco.registries.ReTCoTileEntityTypes;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ReTCo.MODID)
public class ReTCo {

  public static final String MODID = "retco";
  public static final String MOD_NAME = "ReTCo";
  public static final String LOG_TAG = '[' + MOD_NAME + ']';

  // Directly reference a log4j logger.
  private static final Logger LOGGER = LogManager.getLogger(LOG_TAG);

  public static ReTCo instance;
  public final Version versionNumber;

  public ReTCo() {
    instance = this;
    ReTCoConfig.registerConfigs(ModLoadingContext.get());
    LOGGER.info("ReTCo Initializing!");

    final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    // Register Deferred Registers (Does not need to be before Configs)
    ReTCoBlocks.BLOCKS.register(modEventBus);
    ReTCoItems.ITEMS.register(modEventBus);
    ReTCoContainerTypes.CONTAINER_TYPES.register(modEventBus);
    ReTCoTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);

    modEventBus.addListener(this::commonSetup);
    modEventBus.addListener(this::onConfigLoad);

    versionNumber = new Version(ModLoadingContext.get().getActiveContainer().getModInfo().getVersion());
  }

  private void commonSetup(FMLCommonSetupEvent event) {
    LOGGER.info("Version " + versionNumber + " initializing...");

    // Register the mod's world generators
    // noinspection deprecation
    // DeferredWorkQueue.runLater(GenHandler::setupWorldGeneration);
  }

  private void onConfigLoad(ModConfig.ModConfigEvent configEvent) {
    LOGGER.info("Setting up Config...");

    // Note: We listen to both the initial load and the reload, so as to make sure
    // that we fix any accidentally
    // cached values from calls before the initial loading
    ModConfig config = configEvent.getConfig();
    // Make sure it is for the same modid as us
    if (config.getModId().equals(MODID) && config instanceof ReTCoModConfig) {
      ((ReTCoModConfig) config).clearCache();
    }

    LOGGER.info("Config done.");
  }
}
