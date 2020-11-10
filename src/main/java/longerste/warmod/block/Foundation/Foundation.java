package longerste.warmod.block.Foundation;

import longerste.warmod.WarMod;
import longerste.warmod.tile.FoundationTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Foundation extends Block {
  public static String blockName = "foundation";
  public static final int GUI_ID = 1;

  public Foundation() {
    super(Material.IRON);
    setUnlocalizedName(WarMod.MODID + "." + blockName);
    setRegistryName("foundation");
    this.setHardness(5f);
  }

  @SideOnly(Side.CLIENT)
  public void initModel() {
    ModelLoader.setCustomModelResourceLocation(
        Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getBlockLayer() {
    return BlockRenderLayer.CUTOUT_MIPPED;
  }

  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new FoundationTileEntity();
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Override
  public boolean onBlockActivated(
      World worldIn,
      BlockPos pos,
      IBlockState state,
      EntityPlayer playerIn,
      EnumHand hand,
      EnumFacing facing,
      float hitX,
      float hitY,
      float hitZ) {
    if (worldIn.isRemote) {
      return true;
    }
    TileEntity te = worldIn.getTileEntity(pos);
    if (!(te instanceof FoundationTileEntity)) {
      return false;
    }
    playerIn.openGui(WarMod.instance, GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
    return true;
  }

  @Override
  public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
    FoundationTileEntity te = (FoundationTileEntity) worldIn.getTileEntity(pos);
    return te.getHardness();
  }
}
