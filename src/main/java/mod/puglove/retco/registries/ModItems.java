package mod.puglove.retco.registries;

import mod.puglove.retco.ReTCo;
import mod.puglove.retco.armor.EnhancedMatterChestplate;
import mod.puglove.retco.armor.ReTCoArmorMaterial;
import mod.puglove.retco.items.Grub;
import mod.puglove.retco.tools.ReTCoItemTier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

  public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, ReTCo.MODID);

  // Ingredients
  public static final RegistryObject<Item> DIMENSIONAL_RESIDUE = ITEMS.register("dimensional_residue",
      () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  public static final RegistryObject<Item> DIMENSIONAL_INGOT = ITEMS.register("dimensional_ingot",
      () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  public static final RegistryObject<Item> ENHANCED_DIMENSIONAL_INGOT = ITEMS.register("enhanced_dimensional_ingot",
      () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  public static final RegistryObject<Item> DIMENSIONAL_ESSENCE = ITEMS.register("dimensional_essence",
      () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  public static final RegistryObject<Item> FLIGHT_CONTROL_UNIT = ITEMS.register("flight_control_unit",
      () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  public static final RegistryObject<Item> FLIGHT_CIRCUIT = ITEMS.register("flight_circuit",
      () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  // Custom tools
  public static final RegistryObject<Item> FORMING_TOOL = ITEMS.register("forming_tool",
      () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  // Tools
  public static final RegistryObject<SwordItem> DIMENSIONAL_SWORD = ITEMS.register("dimensional_sword",
      () -> new SwordItem(ReTCoItemTier.DIMENSIONAL, 5, -2.2f,
          new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  public static final RegistryObject<AxeItem> DIMENSIONAL_AXE = ITEMS.register("dimensional_axe",
      () -> new AxeItem(ReTCoItemTier.DIMENSIONAL, 2, -2.4f,
          new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  public static final RegistryObject<PickaxeItem> DIMENSIONAL_PICKAXE = ITEMS.register("dimensional_pickaxe",
      () -> new PickaxeItem(ReTCoItemTier.DIMENSIONAL, 1, -2.6f,
          new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  public static final RegistryObject<ShovelItem> DIMENSIONAL_SHOVEL = ITEMS.register("dimensional_shovel",
      () -> new ShovelItem(ReTCoItemTier.DIMENSIONAL, 0, -2.6f,
          new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  public static final RegistryObject<HoeItem> DIMENSIONAL_HOE = ITEMS.register("dimensional_hoe",
      () -> new HoeItem(ReTCoItemTier.DIMENSIONAL, -2.6f, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  // Armor
  public static final RegistryObject<ArmorItem> DIMENSIONAL_HELMET = ITEMS.register("dimensional_helmet",
      () -> new ArmorItem(ReTCoArmorMaterial.DIMENSIONAL, EquipmentSlotType.HEAD,
          new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  public static final RegistryObject<ArmorItem> DIMENSIONAL_CHESTPLATE = ITEMS.register("dimensional_chestplate",
      () -> new ArmorItem(ReTCoArmorMaterial.DIMENSIONAL, EquipmentSlotType.CHEST,
          new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  public static final RegistryObject<ArmorItem> DIMENSIONAL_LEGGINGS = ITEMS.register("dimensional_leggings",
      () -> new ArmorItem(ReTCoArmorMaterial.DIMENSIONAL, EquipmentSlotType.LEGS,
          new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  public static final RegistryObject<ArmorItem> DIMENSIONAL_BOOTS = ITEMS.register("dimensional_boots",
      () -> new ArmorItem(ReTCoArmorMaterial.DIMENSIONAL, EquipmentSlotType.FEET,
          new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  public static final RegistryObject<EnhancedMatterChestplate> ENHANCED_MATTER_CHESTPLATE = ITEMS
      .register("enhanced_matter_chestplate", () -> new EnhancedMatterChestplate(ReTCoArmorMaterial.ENHANCED_MATTER,
          EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));

  // Food
  public static final RegistryObject<Grub> GRUB = ITEMS.register("grub", Grub::new);
}
