package mod.puglove.retco.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mod.puglove.retco.ReTCo;
import mod.puglove.retco.client.ScreenUtils;
import mod.puglove.retco.container.DimensionalEnergySiphonerMK4Container;
import mod.puglove.retco.tile.DimensionalEnergySiphonerMK4TileEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.EnergyStorage;

public class DimensionalEnergySiphonerMK4Screen extends ContainerScreen<DimensionalEnergySiphonerMK4Container> {
  private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(ReTCo.MODID,
      "textures/gui/container/dimensional_energy_siphoner_mk4.png");

  public DimensionalEnergySiphonerMK4Screen(final DimensionalEnergySiphonerMK4Container container,
      final PlayerInventory inventory, final ITextComponent title) {
    super(container, inventory, title);
  }

  @Override
  public void render(MatrixStack stack, final int mouseX, final int mouseY, final float partialTicks) {
    this.renderBackground(stack);
    super.render(stack, mouseX, mouseY, partialTicks);
    this.renderHoveredTooltip(stack, mouseX, mouseY);
  }

  @Override
  protected void drawGuiContainerForegroundLayer(MatrixStack stack, final int mouseX, final int mouseY) {
    String s = this.title.getString();
    this.font.drawString(stack, s, (float) (this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 0x404040);
    this.font.drawString(stack, this.playerInventory.getDisplayName().getString(), 8.0F, (float) (this.ySize - 96 + 2),
        0x404040);

    String energyStored = ScreenUtils.getReadableNum(this.container.tileEntity.energy.getEnergyStored());
    String energyGenerated = ScreenUtils.getReadableNum(this.container.tileEntity.energyGenerated);
    this.font.drawString(stack,
        new TranslationTextComponent("gui." + ReTCo.MODID + ".energy", energyStored).getString(), 8.0F, 30.0F,
        0x404040);
    this.font.drawString(stack,
        new TranslationTextComponent("gui." + ReTCo.MODID + ".energy_generated", energyGenerated).getString(), 8.0F,
        40.0F, 0x404040);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(MatrixStack stack, final float partialTicks, final int mouseX,
      final int mouseY) {
    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
    int startX = this.guiLeft;
    int startY = this.guiTop;

    // Screen#blit draws a part of the current texture (assumed to be 256x256) to
    // the screen
    // The parameters are (x, y, u, v, width, height)

    this.blit(stack, startX, startY, 0, 0, this.xSize, this.ySize);

    final DimensionalEnergySiphonerMK4TileEntity tileEntity = container.tileEntity;

    final EnergyStorage energy = tileEntity.energy;
    final int energyStored = energy.getEnergyStored();
    if (energyStored > 0) { // Draw energy bar
      final int energyProgress = Math.round((float) energyStored / energy.getMaxEnergyStored() * 52);
      this.blit(stack, startX + 121, startY + 19 + 52 - energyProgress, 176, 0, 14, energyProgress);
    }
  }

}
