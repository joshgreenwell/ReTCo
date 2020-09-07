package mod.puglove.retco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.puglove.retco.armor.EnhancedMatterChestplate;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = ReTCo.MODID, bus = EventBusSubscriber.Bus.FORGE)
public final class ForgeEventSubscriber {

  private static final Logger LOGGER = LogManager.getLogger(ReTCo.MODID + " Forge Event Subscriber");

  @SubscribeEvent
  public static void onPlayerEvent(PlayerEvent event) {
    ItemStack armorItem = event.getPlayer().inventory.armorItemInSlot(2);
    boolean canFly = armorItem.getItem() instanceof EnhancedMatterChestplate || event.getPlayer().isCreative();
    event.getPlayer().abilities.allowFlying = canFly;
    if(!canFly) {
      event.getPlayer().abilities.isFlying = false;
    }
  }

}