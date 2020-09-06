package mod.puglove.retco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.puglove.retco.armor.EnhancedMatterChestplate;
import mod.puglove.retco.registries.ModBlocks;
import mod.puglove.retco.registries.ModItemGroups;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = ReTCo.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber {
  private static final Logger LOGGER = LogManager.getLogger(ReTCo.MODID + " Mod Event Subscriber");

  @SubscribeEvent
  public static void onRegisterItems(RegistryEvent.Register<Item> event) {
    final IForgeRegistry<Item> registry = event.getRegistry();
		// Automatically register BlockItems for all our Blocks
		ModBlocks.BLOCKS.getEntries().stream()
				.map(RegistryObject::get)
				// You can do extra filtering here if you don't want some blocks to have an BlockItem automatically registered for them
				// .filter(block -> needsItemBlock(block))
				// Register the BlockItem for the block
				.forEach(block -> {
					// Make the properties, and make it so that the item will be on our ItemGroup (CreativeTab)
					final Item.Properties properties = new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP);
					// Create the new BlockItem with the block and it's properties
					final BlockItem blockItem = new BlockItem(block, properties);
					// Set the new BlockItem's registry name to the block's registry name
					blockItem.setRegistryName(block.getRegistryName());
					// Register the BlockItem
					registry.register(blockItem);
        });
    LOGGER.debug("Registered BlockItems");
  }

  @SubscribeEvent
  public void onPlayerEvent(PlayerEvent event) {
    LOGGER.warn("---");
    // if(event.phase == TickEvent.Phase.START) {
    //   LOGGER.warn("Tick Start");
    //   ItemStack armorItem = event.player.inventory.armorItemInSlot(1);
    //   LOGGER.warn(armorItem.getItem().getRegistryName());
    //   event.player.abilities.allowFlying = armorItem.getItem() instanceof EnhancedMatterChestplate;
    // }
  }
  
}
