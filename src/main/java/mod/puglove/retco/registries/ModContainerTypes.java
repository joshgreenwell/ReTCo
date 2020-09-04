package mod.puglove.retco.registries;

import mod.puglove.retco.ReTCo;
import mod.puglove.retco.container.DimensionalEnergySiphonerContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModContainerTypes {
  public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(
      ForgeRegistries.CONTAINERS, ReTCo.MODID);

  public static final RegistryObject<ContainerType<DimensionalEnergySiphonerContainer>> DIMENSIONAL_ENERGY_SIPHONER = CONTAINER_TYPES
      .register("dimensional_energy_siphoner",
          () -> IForgeContainerType.create(DimensionalEnergySiphonerContainer::new));
}
