package mod.puglove.retco.registries;

import javax.annotation.Nonnull;

import com.google.common.base.Supplier;

import mod.puglove.retco.ReTCo;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ReTCoItemGroups {

  public static final ItemGroup RETCO_ITEM_GROUP = new ReTCoItemGroup(ReTCo.MODID, () -> new ItemStack(ReTCoItems.DIMENSIONAL_INGOT.get()));

  public static class ReTCoItemGroup extends ItemGroup {

    @Nonnull
    private final Supplier<ItemStack> iconSupplier;

    public ReTCoItemGroup(final String name, final Supplier<ItemStack> iconSupplier) {
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
