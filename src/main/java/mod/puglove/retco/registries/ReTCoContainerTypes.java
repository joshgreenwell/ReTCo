package mod.puglove.retco.registries;

import mod.puglove.retco.ReTCo;
import mod.puglove.retco.container.DimensionalEnergySiphonerContainer;
import mod.puglove.retco.container.DimensionalEnergySiphonerMK2Container;
import mod.puglove.retco.container.DimensionalEnergySiphonerMK3Container;
import mod.puglove.retco.container.DimensionalEnergySiphonerMK4Container;
import mod.puglove.retco.container.DimensionalEnergySiphonerMK5Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ReTCoContainerTypes {
  public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister
      .create(ForgeRegistries.CONTAINERS, ReTCo.MODID);

  public static final RegistryObject<ContainerType<DimensionalEnergySiphonerContainer>> DIMENSIONAL_ENERGY_SIPHONER = CONTAINER_TYPES
      .register("dimensional_energy_siphoner",
          () -> IForgeContainerType.create(DimensionalEnergySiphonerContainer::new));

  public static final RegistryObject<ContainerType<DimensionalEnergySiphonerMK2Container>> DIMENSIONAL_ENERGY_SIPHONER_MK2 = CONTAINER_TYPES
      .register("dimensional_energy_siphoner_mk2",
          () -> IForgeContainerType.create(DimensionalEnergySiphonerMK2Container::new));

  public static final RegistryObject<ContainerType<DimensionalEnergySiphonerMK3Container>> DIMENSIONAL_ENERGY_SIPHONER_MK3 = CONTAINER_TYPES
      .register("dimensional_energy_siphoner_mk3",
          () -> IForgeContainerType.create(DimensionalEnergySiphonerMK3Container::new));

  public static final RegistryObject<ContainerType<DimensionalEnergySiphonerMK4Container>> DIMENSIONAL_ENERGY_SIPHONER_MK4 = CONTAINER_TYPES
      .register("dimensional_energy_siphoner_mk4",
          () -> IForgeContainerType.create(DimensionalEnergySiphonerMK4Container::new));

  public static final RegistryObject<ContainerType<DimensionalEnergySiphonerMK5Container>> DIMENSIONAL_ENERGY_SIPHONER_MK5 = CONTAINER_TYPES
      .register("dimensional_energy_siphoner_mk5",
          () -> IForgeContainerType.create(DimensionalEnergySiphonerMK5Container::new));
}
