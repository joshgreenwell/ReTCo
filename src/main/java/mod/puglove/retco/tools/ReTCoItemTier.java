package mod.puglove.retco.tools;

import java.util.function.Supplier;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

import mod.puglove.retco.registries.ModItems;

public enum ReTCoItemTier implements IItemTier {
  
  DIMENSIONAL(4, 2000, 9.0f, 2.0f, 12, () -> {
    // Can be any item that uses this tier material
    return Ingredient.fromItems(ModItems.DIMENSIONAL_INGOT.get());
  });

  private final int harvestLevel;
  private final int maxUses;
  private final float efficiency;
  private final float attackDamage;
  private final int enchantability;
  private final Supplier<Ingredient> repairMaterial;

  ReTCoItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
    this.harvestLevel = harvestLevel;
    this.maxUses = maxUses;
    this.efficiency = efficiency;
    this.attackDamage = attackDamage;
    this.enchantability = enchantability;
    this.repairMaterial = repairMaterial;
  }

  @Override
  public int getMaxUses() {
    return maxUses;
  }

  @Override
  public float getEfficiency() {
    return efficiency;
  }

  @Override
  public float getAttackDamage() {
    return attackDamage;
  }

  @Override
  public int getHarvestLevel() {
    return harvestLevel;
  }

  @Override
  public int getEnchantability() {
    return enchantability;
  }

  @Override
  public Ingredient getRepairMaterial() {
    return repairMaterial.get();
  }
  
}
