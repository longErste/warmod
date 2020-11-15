package longerste.warmod.events;

import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class WMStaticEventHandler {

  @SubscribeEvent
  public static void noSpawnOutside(PlayerSetSpawnEvent event) {
    event.setCanceled(true);
  }
}
