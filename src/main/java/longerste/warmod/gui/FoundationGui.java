package longerste.warmod.gui;

import longerste.warmod.WarMod;
import longerste.warmod.block.Foundation.FoundationContainer;
import longerste.warmod.networking.ModifyFoundationMessage;
import longerste.warmod.networking.WarModNetworkingHandler;
import longerste.warmod.tile.FoundationTileEntity;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class FoundationGui extends GuiContainer {
  public static final int WIDTH = 180;
  public static final int HEIGHT = 152;
  private final FoundationTileEntity te;

  private static final ResourceLocation background =
      new ResourceLocation(WarMod.MODID, "textures/gui/foundation.png");


  public FoundationGui(FoundationTileEntity te, FoundationContainer container) {
    super(container);
    this.te = te;
    xSize = WIDTH;
    ySize = HEIGHT;
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    mc.getTextureManager().bindTexture(background);
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    String points = "Upgrade Points: " + te.getPoints() + " pts / " + te.getMaxPoints() + "pts";
    String level = "Lv: " + (te.getTier() + 1) + " / " + FoundationTileEntity.upgradePoints.length;
    String hardness = "Hardness: " + te.getHardness();
    String team = "Team: " + te.getTeamId();

    this.fontRenderer.drawString(team, 8, 25, 4210752);
    this.fontRenderer.drawString(
        level, this.xSize / 2 - this.fontRenderer.getStringWidth(level) / 2, 35, 4210752);
    this.fontRenderer.drawString(hardness, 8, 45, 4210752);
    this.fontRenderer.drawString(points, 8, 55, 4210752);
  }

  @Override
  public void initGui() {
    super.initGui();

    this.buttonList.add(new GuiButton(1, 120, 100, 100, 20, "Increase Hardness"));
    this.buttonList.add(new GuiButton(2, 120, 120, 100, 20, "Decrease Hardness"));
    this.buttonList.add(new GuiButton(3, 120, 140, 100, 20, "Upgrade"));
  }

  @Override
  protected void actionPerformed(GuiButton button) {
    SimpleNetworkWrapper instance = WarModNetworkingHandler.dispatcher;
    BlockPos tePos = te.getPos();
    if (button.id == 1) {
      te.setHardness(1);
      this.mc.player.sendMessage(new TextComponentString("Hardness " + te.getHardness()));
      instance.sendToServer(new ModifyFoundationMessage(tePos, 2, 1));
    }
    if (button.id == 2) {
      te.setHardness(-1);
      this.mc.player.sendMessage(new TextComponentString("Hardness " + te.getHardness()));
      instance.sendToServer(new ModifyFoundationMessage(tePos, 2, -1));
    }
    if (button.id == 3) {
      te.upgrade();
      instance.sendToServer(new ModifyFoundationMessage(tePos, 1, 0));
    }
  }
}
