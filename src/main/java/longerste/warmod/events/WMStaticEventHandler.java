package longerste.warmod.events;

import com.feed_the_beast.ftblib.events.team.ForgeTeamCreatedEvent;
import com.feed_the_beast.ftblib.lib.data.ForgePlayer;
import com.feed_the_beast.ftblib.lib.data.Universe;
import longerste.warmod.block.WMBlocks;
import longerste.warmod.capability.TeamPos.ITeamPos;
import longerste.warmod.capability.TeamPos.TeamPosProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
      ForgePlayer fPlayer = Universe.get().getPlayer(player.getUniqueID());
      if (fPlayer.hasTeam()) {
        short id = fPlayer.team.getUID();
        if (teamPos.hasTeam(id)) {
          BlockPos pos = teamPos.getTeamBlockPos(id);
          if (event.getNewSpawn().distanceSq(pos) > 20D) {
            player.sendMessage(new TextComponentString("Don't set spawn outside >:("));
            event.setCanceled(true);
          }
        } else {
          player.sendMessage(new TextComponentString("No Base No spawn so sad..."));
          event.setCanceled(true);
        }
      }
    }
  }

  @SubscribeEvent
  public static void GiveFoundation(ForgeTeamCreatedEvent event) {
    if(event.getTeam() != null && event.getTeam().isValid() && event.getTeam().owner != null){
      event.getTeam().owner.getPlayer().inventory.addItemStackToInventory(new ItemStack(WMBlocks.foundation, 1));
    }
  }
}
