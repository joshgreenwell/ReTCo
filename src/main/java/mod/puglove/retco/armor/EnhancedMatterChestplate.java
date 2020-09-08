package mod.puglove.retco.armor;

import java.util.function.Consumer;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

public class EnhancedMatterChestplate extends ArmorItem {

  public EnhancedMatterChestplate(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
    super(materialIn, slot, builder.setNoRepair().maxStackSize(1));
  }

  @Override
  public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
      // safety check
      return 0;
  }

  @Override
  public boolean showDurabilityBar(ItemStack stack) {
      return true;
  }

}
