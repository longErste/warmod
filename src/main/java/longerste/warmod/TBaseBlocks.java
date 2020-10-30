package longerste.warmod;

import longerste.warmod.blocks.Foundation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TBaseBlocks {

  @GameRegistry.ObjectHolder("warmod:foundation")
  public static Foundation foundation;

  @SideOnly(Side.CLIENT)
  public static void initModels() {
    foundation.initModel();
  }

}
