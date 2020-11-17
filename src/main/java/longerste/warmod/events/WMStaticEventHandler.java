package longerste.warmod.events;

import com.feed_the_beast.ftblib.events.team.ForgeTeamCreatedEvent;
import com.feed_the_beast.ftblib.events.team.ForgeTeamDeletedEvent;
import com.feed_the_beast.ftblib.lib.data.ForgePlayer;
import com.feed_the_beast.ftblib.lib.data.Universe;
import longerste.warmod.block.WMBlocks;
import longerste.warmod.capability.TeamHardness.ITeamHardness;
import longerste.warmod.capability.TeamHardness.TeamHardnessProvider;
import longerste.warmod.capability.TeamPos.ITeamPos;
import longerste.warmod.capability.TeamPos.TeamPosProvider;
import longerste.warmod.networking.GetHardMap;
import longerste.warmod.networking.WMNetworkingHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
  public static void GiveFoundationAndSetHardness(ForgeTeamCreatedEvent event) {
    short id = event.getTeam().getUID();
    ITeamHardness hardness = event.getUniverse().world.getCapability(TeamHardnessProvider.TEAM_HARDNESS_CAP, null);
    hardness.setTeamHardness(id);
    WMNetworkingHandler.dispatcher.sendToAll(new GetHardMap(id));
    if (event.getTeam().isValid() && event.getTeam().owner != null) {
      EntityPlayerMP owner = event.getTeam().owner.getPlayer();
      ItemStack foundation = new ItemStack(WMBlocks.foundation, 1);
      String message = "The team: " + event.getTeam().getTitle().getFormattedText() + "is created";
      event.getTeam().universe.server.getPlayerList().sendMessage(new TextComponentString(message));
      if (!owner.inventory.hasItemStack(foundation)) {
        owner.inventory.addItemStackToInventory(new ItemStack(WMBlocks.foundation, 1));
      } else {
        owner.sendMessage(new TextComponentString("Why do you have a foundation block already huh ?"));
      }
    }
  }

  @SubscribeEvent
  public static void LoseWhenNoMemberInTeam(ForgeTeamDeletedEvent event) {
    event.getUniverse().server.getPlayerList().sendMessage(new TextComponentString("Team Got YEETED: GG " + event.getTeam().getTitle().getFormattedText()));
  }
}
