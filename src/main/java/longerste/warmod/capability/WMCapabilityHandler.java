package longerste.warmod.capability;

import longerste.warmod.WarMod;
import longerste.warmod.capability.TeamHardness.ITeamHardness;
import longerste.warmod.capability.TeamHardness.TeamHardness;
import longerste.warmod.capability.TeamHardness.TeamHardnessProvider;
import longerste.warmod.capability.TeamHardness.TeamHardnessStorage;
import longerste.warmod.capability.TeamPos.ITeamPos;
import longerste.warmod.capability.TeamPos.TeamPos;
import longerste.warmod.capability.TeamPos.TeamPosProvider;
import longerste.warmod.capability.TeamPos.TeamPosStorage;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WMCapabilityHandler {
  public static final ResourceLocation TEAM_POS_CAP = new ResourceLocation(WarMod.MODID, "teamPos" );
  public static final ResourceLocation TEAM_HARDNESS_CAP = new ResourceLocation(WarMod.MODID, "teamHardness" );

  @SubscribeEvent
  public void attachCapability(AttachCapabilitiesEvent<World> event){
    event.addCapability(TEAM_POS_CAP, new TeamPosProvider());
    event.addCapability(TEAM_HARDNESS_CAP, new TeamHardnessProvider());
  }
  public static void RegisterCapability(){
    CapabilityManager.INSTANCE.register(ITeamPos.class, new TeamPosStorage(), TeamPos.class);
    CapabilityManager.INSTANCE.register(ITeamHardness.class, new TeamHardnessStorage(), TeamHardness.class);
  }

}
