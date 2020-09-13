package mod.puglove.retco.registries;

import mod.puglove.retco.ReTCo;
import mod.puglove.retco.tileentity.CompressorTileEntity;
import mod.puglove.retco.tileentity.DimensionalEnergySiphonerMK2TileEntity;
import mod.puglove.retco.tileentity.DimensionalEnergySiphonerMK3TileEntity;
import mod.puglove.retco.tileentity.DimensionalEnergySiphonerTileEntity;
import mod.puglove.retco.tileentity.MatterChangerTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Holds a list of all our {@link TileEntityType}s. Suppliers that create
 * TileEntityTypes are added to the DeferredRegister. The DeferredRegister is
 * then added to our mod event bus in our constructor. When the TileEntityType
 * Registry Event is fired by Forge and it is time for the mod to register its
 * TileEntityTypes, our TileEntityTypes are created and registered by the
 * DeferredRegister. The TileEntityType Registry Event will always be called
 * after the Block and Item registries are filled. Note: This supports registry
 * overrides.
 */
public final class ModTileEntityTypes {

  public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(
      ForgeRegistries.TILE_ENTITIES, ReTCo.MODID);

  // We don't have a datafixer for our TileEntities, so we pass null into build.
  public static final RegistryObject<TileEntityType<DimensionalEnergySiphonerTileEntity>> DIMENSIONAL_ENERGY_SIPHONER = TILE_ENTITY_TYPES
      .register("dimensional_energy_siphoner", () -> TileEntityType.Builder
          .create(DimensionalEnergySiphonerTileEntity::new, ModBlocks.DIMENSIONAL_ENERGY_SIPHONER.get()).build(null));

  public static final RegistryObject<TileEntityType<DimensionalEnergySiphonerMK2TileEntity>> DIMENSIONAL_ENERGY_SIPHONER_MK2 = TILE_ENTITY_TYPES
      .register("dimensional_energy_siphoner_mk2",
          () -> TileEntityType.Builder
              .create(DimensionalEnergySiphonerMK2TileEntity::new, ModBlocks.DIMENSIONAL_ENERGY_SIPHONER_MK2.get())
              .build(null));

  public static final RegistryObject<TileEntityType<DimensionalEnergySiphonerMK3TileEntity>> DIMENSIONAL_ENERGY_SIPHONER_MK3 = TILE_ENTITY_TYPES
      .register("dimensional_energy_siphoner_mk3",
          () -> TileEntityType.Builder
              .create(DimensionalEnergySiphonerMK3TileEntity::new, ModBlocks.DIMENSIONAL_ENERGY_SIPHONER_MK3.get())
              .build(null));

  public static final RegistryObject<TileEntityType<MatterChangerTileEntity>> MATTER_CHANGER = TILE_ENTITY_TYPES
      .register("matter_changer", () -> TileEntityType.Builder
          .create(MatterChangerTileEntity::new, ModBlocks.MATTER_CHANGER.get()).build(null));

  public static final RegistryObject<TileEntityType<CompressorTileEntity>> COMPRESSOR = TILE_ENTITY_TYPES
      .register("compressor", () -> TileEntityType.Builder
          .create(CompressorTileEntity::new, ModBlocks.MATTER_CHANGER.get()).build(null));
}