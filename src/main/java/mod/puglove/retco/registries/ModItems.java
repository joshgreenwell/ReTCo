package mod.puglove.retco.registries;

import mod.puglove.retco.ReTCo;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

  public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, ReTCo.MODID);
  
  public static final RegistryObject<Item> DIMENSIONAL_RESIDUE = ITEMS.register("dimensional_residue", () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
  public static final RegistryObject<Item> DIMENSIONAL_INGOT = ITEMS.register("dimensional_ingot", () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
  public static final RegistryObject<Item> ENHANCED_DIMENSIONAL_INGOT = ITEMS.register("enhanced_dimensional_ingot", () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
  
  public static final RegistryObject<Item> FORMING_TOOL = ITEMS.register("forming_tool", () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  public static final RegistryObject<Item> DECONSTRUCTED_DIMENSIONAL_ENERGY_SIPHONER = ITEMS.register("deconstructed_dimensional_energy_siphoner", () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

}
