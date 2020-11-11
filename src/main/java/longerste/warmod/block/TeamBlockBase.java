package longerste.warmod.block;

import longerste.warmod.WarMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TeamBlockBase extends Block {
  public static String blockName;

  public TeamBlockBase(String name) {
    super(Material.IRON);
    blockName = name;
    setUnlocalizedName(WarMod.MODID + "." + blockName);
    setRegistryName(blockName);
    this.setHardness(5f);
  }

  @Override
  public abstract TileEntity createTileEntity(World world, IBlockState state);

  @Override
  public final boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Override
  public abstract float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos);

}
