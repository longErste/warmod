package longerste.warmod.block.Foundation;

import java.awt.TextComponent;
import longerste.warmod.WarMod;
import longerste.warmod.tile.FoundationTileEntity;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class FoundationGui extends GuiContainer {
  public static final int WIDTH = 180;
  public static final int HEIGHT = 152;
  private FoundationTileEntity te;

  private static final ResourceLocation background = new ResourceLocation(WarMod.MODID, "textures/gui/foundation.png" );

  public FoundationGui(FoundationTileEntity te, FoundationContainer container) {
    super(container);
    this.te = te;
    xSize = WIDTH;
    ySize = HEIGHT;
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    mc.getTextureManager().bindTexture(background);
    drawTexturedModalRect(guiLeft, guiTop, 0,0, xSize, ySize);
  }

  @Override
  public void initGui() {
    super.initGui();

    this.buttonList.add(new GuiButton(1, 120, 100, 100, 20, "Increase Hardness"));
    this.buttonList.add(new GuiButton(2, 120, 120, 100, 20, "Decrease Hardness"));
  }

  @Override
  protected void actionPerformed(GuiButton button){

    if(button.id == 1){
      te.increaseHardness();
      this.mc.player.sendMessage(new TextComponentString("Hardness " + te.getHardness()));
    }
    if(button.id == 2) {
      te.decreaseHardness();
      this.mc.player.sendMessage(new TextComponentString("Hardness " + te.getHardness()));
    }
  }
}
