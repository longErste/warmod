package longerste.warmod.block.buildblock;

import com.feed_the_beast.ftblib.lib.data.ForgePlayer;
import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.feed_the_beast.ftblib.lib.data.Universe;
import longerste.warmod.block.TeamBlockBase;
import longerste.warmod.capability.TeamHardness.ITeamHardness;
import longerste.warmod.capability.TeamHardness.TeamHardnessProvider;
import longerste.warmod.tile.BuildingBlockTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BuildingBlock extends TeamBlockBase {

  public BuildingBlock() {
    super("buildingblock");
  }

  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new BuildingBlockTileEntity();
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    if (!worldIn.isRemote) {
      if (placer instanceof EntityPlayer) {
        EntityPlayer player = (EntityPlayer) placer;
        Universe universe = Universe.get();
        ForgePlayer fPlayer = universe.getPlayer(player.getGameProfile());
        if (fPlayer.hasTeam()) {
          ForgeTeam team = fPlayer.team;
          TileEntity te = worldIn.getTileEntity(pos);
          if (te instanceof BuildingBlockTileEntity) {
            BuildingBlockTileEntity fte = (BuildingBlockTileEntity) te;
            fte.setTeam(team.getUID());
          }
        }
      }
    }
  }

  @Override
  public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
    BuildingBlockTileEntity bbte = (BuildingBlockTileEntity) worldIn.getTileEntity(pos);
    short team = bbte.getTeam();
    ITeamHardness teamHard = worldIn.getCapability(TeamHardnessProvider.TEAM_HARDNESS_CAP, null);
    return teamHard.getTeamHardness(team);
  }
}
