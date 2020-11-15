package longerste.warmod.block.Foundation;

import com.feed_the_beast.ftblib.lib.data.ForgePlayer;
import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.feed_the_beast.ftblib.lib.data.Universe;
import longerste.warmod.WarMod;
import longerste.warmod.block.TeamBlockBase;
import longerste.warmod.networking.GetTeamNameMessage;
import longerste.warmod.networking.WarModNetworkingHandler;
import longerste.warmod.tile.FoundationTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Foundation extends TeamBlockBase {

  public static final int GUI_ID = 1;

  public Foundation() {
    super("foundation");
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
  public void onBlockPlacedBy(
      World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    if (!worldIn.isRemote) {
      if (placer instanceof EntityPlayer) {
        EntityPlayer player = (EntityPlayer) placer;
        Universe universe = Universe.get();
        ForgePlayer fPlayer = universe.getPlayer(player.getGameProfile());
        if (fPlayer.hasTeam()) {
          ForgeTeam team = fPlayer.team;
          TileEntity te = worldIn.getTileEntity(pos);
          if (te instanceof FoundationTileEntity) {
            FoundationTileEntity fte = (FoundationTileEntity) te;
            fte.setTeam(team);
          }
        }
      }
    }
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (worldIn.isRemote) {
      return true;
    }
    TileEntity te = worldIn.getTileEntity(pos);
    if (!(te instanceof FoundationTileEntity)) {
      return false;
    } else {
      Universe universe = Universe.get();
      ForgePlayer forgePlayer = universe.getPlayer(playerIn);
      ForgeTeam team = forgePlayer.team;
      FoundationTileEntity fte = (FoundationTileEntity) te;
      if (!fte.getTeam().equalsTeam(universe.fakePlayerTeam)
          && fte.getTeam().isValid()
          && fte.getTeam().equalsTeam(team)) {
        playerIn.openGui(WarMod.instance, GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        WarModNetworkingHandler.dispatcher.sendTo(new GetTeamNameMessage(pos, team.getTitle()), (EntityPlayerMP) playerIn);
      }
    }
    return true;
  }

  @Override
  public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
    FoundationTileEntity te = (FoundationTileEntity) worldIn.getTileEntity(pos);
    return te.getHardness();
  }
}
