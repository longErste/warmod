package longerste.warmod.block;

import longerste.warmod.block.buildblock.BuildingBlock;
import longerste.warmod.block.foundation.Foundation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

public class WMBlocks {

  @GameRegistry.ObjectHolder("warmod:foundation")
  public static Foundation foundation;
  @GameRegistry.ObjectHolder("warmod:buildingblock")
  public static BuildingBlock buildingBlock;
}
