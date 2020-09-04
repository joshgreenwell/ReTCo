package mod.puglove.retco.registries;

import javax.annotation.Nonnull;

import com.google.common.base.Supplier;

import mod.puglove.retco.ReTCo;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModItemGroups {

  public static final ItemGroup MOD_ITEM_GROUP = new ModItemGroup(ReTCo.MODID, () -> new ItemStack(ModItems.DIMENSIONAL_RESIDUE.get()));

  public static class ModItemGroup extends ItemGroup {

    @Nonnull
    private final Supplier<ItemStack> iconSupplier;

    public ModItemGroup(final String name, final Supplier<ItemStack> iconSupplier) {
      super(name);
      this.iconSupplier = iconSupplier;
    }

    @Override
    @Nonnull
    public ItemStack createIcon() {
      return iconSupplier.get();
    }

  }

}
