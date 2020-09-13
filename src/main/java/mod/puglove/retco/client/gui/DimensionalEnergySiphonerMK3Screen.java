package mod.puglove.retco.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import mod.puglove.retco.ReTCo;
import mod.puglove.retco.container.DimensionalEnergySiphonerMK3Container;
import mod.puglove.retco.energy.SettableEnergyStorage;
import mod.puglove.retco.tileentity.DimensionalEnergySiphonerMK3TileEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class DimensionalEnergySiphonerMK3Screen extends ContainerScreen<DimensionalEnergySiphonerMK3Container> {
  private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(ReTCo.MODID,
      "textures/gui/container/dimensional_energy_siphoner_mk3.png");

  public DimensionalEnergySiphonerMK3Screen(final DimensionalEnergySiphonerMK3Container container,
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
  protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
    super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    // Copied from AbstractFurnaceScreen#drawGuiContainerForegroundLayer
    String s = this.title.getFormattedText();
    this.font.drawString(s, (float) (this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 0x404040);
    this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2),
        0x404040);
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

    final DimensionalEnergySiphonerMK3TileEntity tileEntity = container.tileEntity;

    final SettableEnergyStorage energy = tileEntity.energy;
    final int energyStored = energy.getEnergyStored();
    if (energyStored > 0) { // Draw energy bar
      final int energyProgress = Math.round((float) energyStored / energy.getMaxEnergyStored() * 52);
      this.blit(startX + 81, startY + 19 + 52 - energyProgress, 176, 0, 14, energyProgress);
    }
  }
}
