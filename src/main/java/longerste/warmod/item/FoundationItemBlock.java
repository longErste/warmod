package longerste.warmod.item;

import com.feed_the_beast.ftblib.lib.data.ForgePlayer;
import com.feed_the_beast.ftblib.lib.data.Universe;
import longerste.warmod.block.WMBlocks;
import longerste.warmod.capability.TeamPos.ITeamPos;
import longerste.warmod.capability.TeamPos.TeamPosProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FoundationItemBlock extends ItemBlock {

  public FoundationItemBlock(Block block) {
    super(block);
    setRegistryName(WMBlocks.foundation.getRegistryName());
    setMaxStackSize(1);
  }

  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (worldIn.isRemote) {
      return EnumActionResult.FAIL;
    }
    ForgePlayer fPlayer = Universe.get().getPlayer(player.getUniqueID());
    if (fPlayer.hasTeam()) {
      short uid = Universe.get().getPlayer(player.getUniqueID()).team.getUID();
      ITeamPos teamPos = worldIn.getCapability(TeamPosProvider.TEAM_POS_CAP, null);
      if (teamPos.hasTeam(uid)) {
        return EnumActionResult.FAIL;
      }
      return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
    return EnumActionResult.FAIL;
  }
}
