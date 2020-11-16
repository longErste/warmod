package longerste.warmod.capability;

import longerste.warmod.WarMod;
import longerste.warmod.capability.TeamPos.TeamPosProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WMCapabilityHandler {
  public static final ResourceLocation TEAM_POS_CAP = new ResourceLocation(WarMod.MODID, "teamPos" );
  @SubscribeEvent
  public void attachCapability(AttachCapabilitiesEvent<World> event){
    event.addCapability(TEAM_POS_CAP, new TeamPosProvider());
  }

}
