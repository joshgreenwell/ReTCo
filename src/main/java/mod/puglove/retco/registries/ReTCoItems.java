package mod.puglove.retco.registries;

import mod.puglove.retco.ReTCo;
import mod.puglove.retco.item.FlightControlUnit;
import mod.puglove.retco.item.Grub;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ReTCoItems {

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ReTCo.MODID);

  // Ingredients
  public static final RegistryObject<Item> DIMENSIONAL_RESIDUE = ITEMS.register("dimensional_residue",
      () -> new Item(new Item.Properties().group(ReTCoItemGroups.RETCO_ITEM_GROUP)));

  public static final RegistryObject<Item> DIMENSIONAL_INGOT = ITEMS.register("dimensional_ingot",
      () -> new Item(new Item.Properties().group(ReTCoItemGroups.RETCO_ITEM_GROUP)));

  public static final RegistryObject<Item> ENHANCED_DIMENSIONAL_INGOT = ITEMS.register("enhanced_dimensional_ingot",
      () -> new Item(new Item.Properties().group(ReTCoItemGroups.RETCO_ITEM_GROUP)));

  public static final RegistryObject<FlightControlUnit> FLIGHT_CONTROL_UNIT = ITEMS.register("flight_control_unit",
      () -> new FlightControlUnit(new Item.Properties().group(ReTCoItemGroups.RETCO_ITEM_GROUP)));

  public static final RegistryObject<Item> FLIGHT_CIRCUIT = ITEMS.register("flight_circuit",
      () -> new Item(new Item.Properties().group(ReTCoItemGroups.RETCO_ITEM_GROUP)));

  public static final RegistryObject<Item> ENERGY_CORE = ITEMS.register("energy_core",
      () -> new Item(new Item.Properties().group(ReTCoItemGroups.RETCO_ITEM_GROUP)));

  // Food
  public static final RegistryObject<Grub> GRUB = ITEMS.register("grub", Grub::new);
}
