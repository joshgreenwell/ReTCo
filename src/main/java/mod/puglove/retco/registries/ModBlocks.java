package mod.puglove.retco.registries;

import mod.puglove.retco.ReTCo;
import mod.puglove.retco.block.DimensionalEnergySiphonerBlock;
import mod.puglove.retco.block.DimensionalEnergySiphonerMK2Block;
import mod.puglove.retco.block.DimensionalEnergySiphonerMK3Block;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

  public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, ReTCo.MODID);

  public static final RegistryObject<Block> DIMENSIONAL_RESIDUE_ORE = BLOCKS.register("dimensional_residue_ore",
      () -> new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0F, 3.0F)
          .harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)));

  public static final RegistryObject<Block> DIMENSIONAL_BLOCK = BLOCKS.register("dimensional_block",
      () -> new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0F, 6.0F)
          .harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)));

  public static final RegistryObject<Block> DIMENSIONAL_ENERGY_SIPHONER = BLOCKS.register("dimensional_energy_siphoner",
      () -> new DimensionalEnergySiphonerBlock(Block.Properties.create(Material.IRON)
          .hardnessAndResistance(5.0F, 1200.0F).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)));

  public static final RegistryObject<Block> DIMENSIONAL_ENERGY_SIPHONER_MK2 = BLOCKS.register(
      "dimensional_energy_siphoner_mk2",
      () -> new DimensionalEnergySiphonerMK2Block(Block.Properties.create(Material.IRON)
          .hardnessAndResistance(5.0F, 1200.0F).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)));

  public static final RegistryObject<Block> DIMENSIONAL_ENERGY_SIPHONER_MK3 = BLOCKS.register(
      "dimensional_energy_siphoner_mk3",
      () -> new DimensionalEnergySiphonerMK3Block(Block.Properties.create(Material.IRON)
          .hardnessAndResistance(5.0F, 1200.0F).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)));
}
