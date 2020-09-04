package mod.puglove.retco;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import mod.puglove.retco.config.ReTCoConfig;
import mod.puglove.retco.config.ReTCoModConfig;
import mod.puglove.retco.registries.ModBlocks;
import mod.puglove.retco.registries.ModContainerTypes;
import mod.puglove.retco.registries.ModItems;
import mod.puglove.retco.registries.ModTileEntityTypes;
import mod.puglove.retco.world.GenHandler;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ReTCo.MODID)
public class ReTCo {

  public static final String MODID = "retco";
  public static final String MOD_NAME = "ReTCo";
  public static final String LOG_TAG = '[' + MOD_NAME + ']';

  public static Logger logger = LogManager.getLogger(MOD_NAME);

  public static ReTCo instance;
  public final Version versionNumber;

  public ReTCo() {
    instance = this;
    ReTCoConfig.registerConfigs(ModLoadingContext.get());
    logger.info("ReTCo Initializing!");

    final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    // Register Deferred Registers (Does not need to be before Configs)
    ModBlocks.BLOCKS.register(modEventBus);
    ModItems.ITEMS.register(modEventBus);
    ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
		ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);

    MinecraftForge.EVENT_BUS.addListener(this::chunkSave);
    MinecraftForge.EVENT_BUS.addListener(this::onChunkDataLoad);
    modEventBus.addListener(this::commonSetup);
    modEventBus.addListener(this::onConfigLoad);

    versionNumber = new Version(ModLoadingContext.get().getActiveContainer().getModInfo().getVersion());
  }

  private void commonSetup(FMLCommonSetupEvent event) {
    //Register the mod's world generators
    //noinspection deprecation
    DeferredWorkQueue.runLater(GenHandler::setupWorldGeneration);

    //Initialization notification
    logger.info("Version " + versionNumber + " initializing...");

    //Completion notification
    logger.info("Loading complete.");

    //Success message
    logger.info("Mod loaded.");
}

  private void chunkSave(ChunkDataEvent.Save event) {
    if (event.getWorld() != null && !event.getWorld().isRemote()) {
      // Note: ChunkDataLoad uses the level tag, but chunk save uses the parent
      // compound
      // we need to get the level compound so that we read the correct value
      // CompoundNBT levelTag = event.getData().getCompound(NBTConstants.LEVEL);
      // levelTag.putInt(NBTConstants.WORLD_GEN_VERSION, MekanismConfig.world.userGenVersion.get());
    }
  }

  private synchronized void onChunkDataLoad(ChunkDataEvent.Load event) {
    if (event.getWorld() != null && !event.getWorld().isRemote()) {
      // if (MekanismConfig.world.enableRegeneration.get()
      //     && event.getData().getInt(NBTConstants.WORLD_GEN_VERSION) < MekanismConfig.world.userGenVersion.get()) {
      //   worldTickHandler.addRegenChunk(event.getWorld().getDimension().getType(), event.getChunk().getPos());
      // }
    }
  }

  private void onConfigLoad(ModConfig.ModConfigEvent configEvent) {
    // Note: We listen to both the initial load and the reload, so as to make sure
    // that we fix any accidentally
    // cached values from calls before the initial loading
    ModConfig config = configEvent.getConfig();
    // Make sure it is for the same modid as us
    if (config.getModId().equals(MODID) && config instanceof ReTCoModConfig) {
      ((ReTCoModConfig) config).clearCache();
    }
  }
}
