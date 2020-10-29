package longerste.warmod.blocks;

import longerste.warmod.WarMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Foundation extends Block {
  String blockname = "foundation";

  public Foundation() {
    super(Material.IRON);
    setUnlocalizedName(WarMod.MODID + "." + blockname);
    setRegistryName(blockname);
  }
}
