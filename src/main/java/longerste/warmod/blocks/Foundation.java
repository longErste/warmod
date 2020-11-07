package longerste.warmod.blocks;

import longerste.warmod.WarMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Foundation extends Block {
  String blockname = "foundation";

  public Foundation() {
    super(Material.IRON);
    setUnlocalizedName(WarMod.MODID + "." + blockname);
    setRegistryName(blockname);
  }

  @SideOnly(Side.CLIENT)
  public void initModel() {
    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
  }
}
