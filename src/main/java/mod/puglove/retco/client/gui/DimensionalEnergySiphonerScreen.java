package mod.puglove.retco.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import mod.puglove.retco.ReTCo;
import mod.puglove.retco.container.DimensionalEnergySiphonerContainer;
import mod.puglove.retco.energy.SettableEnergyStorage;
import mod.puglove.retco.tileentity.DimensionalEnergySiphonerTileEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class DimensionalEnergySiphonerScreen extends ContainerScreen<DimensionalEnergySiphonerContainer> {
  private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(ReTCo.MODID,
      "textures/gui/container/dimensional_energy_siphoner.png");

  public DimensionalEnergySiphonerScreen(final DimensionalEnergySiphonerContainer container,
      final PlayerInventory inventory, final ITextComponent title) {
    super(container, inventory, title);
  }

  @Override
  public void render(final int mouseX, final int mouseY, final float partialTicks) {
    this.renderBackground();
    super.render(mouseX, mouseY, partialTicks);
    this.renderHoveredToolTip(mouseX, mouseY);

    int relMouseX = mouseX - this.guiLeft;
    int relMouseY = mouseY - this.guiTop;
    boolean energyBarHovered = relMouseX > 81 && relMouseX < 94 && relMouseY > 18 && relMouseY < 70;
    if (energyBarHovered) {
      String tooltip = new TranslationTextComponent("gui." + ReTCo.MODID + ".energy",
          this.container.tileEntity.energy.getEnergyStored()).getFormattedText();
      this.renderTooltip(tooltip, mouseX, mouseY);
    }
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
    int startX = this.guiLeft;
    int startY = this.guiTop;

    // Screen#blit draws a part of the current texture (assumed to be 256x256) to
    // the screen
    // The parameters are (x, y, u, v, width, height)

    this.blit(startX, startY, 0, 0, this.xSize, this.ySize);

    final DimensionalEnergySiphonerTileEntity tileEntity = container.tileEntity;

    final SettableEnergyStorage energy = tileEntity.energy;
    final int energyStored = energy.getEnergyStored();
    if (energyStored > 0) { // Draw energy bar
      final int energyProgress = Math.round((float) energyStored / energy.getMaxEnergyStored() * 65);
      this.blit(startX + 81, startY + 18 + 52 - energyProgress, 176, 14, 14, energyProgress);
    }
  }
}
