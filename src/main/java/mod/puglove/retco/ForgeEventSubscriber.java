package mod.puglove.retco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.puglove.retco.item.FlightControlUnit;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = ReTCo.MODID, bus = EventBusSubscriber.Bus.FORGE)
public final class ForgeEventSubscriber {

  private static final Logger LOGGER = LogManager.getLogger(ReTCo.LOG_TAG + " Forge Event Subscriber");

  @SubscribeEvent
  public static void onPlayerEvent(PlayerEvent event) {
    boolean canFly = false;
    if (event.getPlayer() != null) {
      // Check the player inventory
      for (ItemStack item : event.getPlayer().inventory.mainInventory) {
        if (item.getItem() instanceof FlightControlUnit) {
          canFly = true;
        }
      }

      // Check edge cases
      if (event.getPlayer().inventory.offHandInventory.get(0).getItem() instanceof FlightControlUnit
          || event.getPlayer().isCreative()) {
            canFly = true;
      }
      event.getPlayer().abilities.allowFlying = canFly;
      if (!canFly) {
        event.getPlayer().abilities.isFlying = false;
      }
    }
  }

}