package longerste.warmod.events;

import com.feed_the_beast.ftblib.lib.data.ForgePlayer;
import com.feed_the_beast.ftblib.lib.data.Universe;
import longerste.warmod.capability.TeamPos.ITeamPos;
import longerste.warmod.capability.TeamPos.TeamPosProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class WMStaticEventHandler {

  @SubscribeEvent
  public static void noSpawnOutside(PlayerSetSpawnEvent event) {
    EntityPlayer player = event.getEntityPlayer();
    ITeamPos teamPos = player.world.getCapability(TeamPosProvider.TEAM_POS_CAP, null);
    if (!player.world.isRemote) {
      ForgePlayer fplayer = Universe.get().getPlayer(player.getUniqueID());
      if (fplayer.hasTeam()) {
        short id = fplayer.team.getUID();
        if (teamPos.hasTeam(id)) {
          BlockPos pos = teamPos.getTeamBlockPos(id);
          if (event.getNewSpawn().distanceSq(pos) > 20D) {
            player.sendMessage(new TextComponentString("Fuck u"));
            event.setCanceled(true);
          }
        }
      }
    }

  }
}
