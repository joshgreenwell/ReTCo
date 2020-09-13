package mod.puglove.retco.items;

import mod.puglove.retco.registries.ModItemGroups;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class Grub extends Item {

  public Grub() {
    super(new Item.Properties()
      .group(ModItemGroups.MOD_ITEM_GROUP)
      .food(new Food.Builder()
        .hunger(10)
        .saturation(0.8f)
        .build()
      )
    );
  }
  
}
