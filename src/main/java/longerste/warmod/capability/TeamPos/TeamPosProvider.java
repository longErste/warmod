package longerste.warmod.capability.TeamPos;


import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class TeamPosProvider implements ICapabilitySerializable<NBTBase> {

  @CapabilityInject(ITeamPos.class)
  public static final Capability<ITeamPos> TEAM_POS_CAP = null;

  private ITeamPos instance = TEAM_POS_CAP.getDefaultInstance();

  @Override
  public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
    return capability == TEAM_POS_CAP;
  }

  @Override
  public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
    if (capability == TEAM_POS_CAP){
      return TEAM_POS_CAP.<T> cast(this.instance);
    } else {
      return null;
    }
  }

  @Override
  public NBTBase serializeNBT() {
    return TEAM_POS_CAP.getStorage().writeNBT(TEAM_POS_CAP, this.instance, null);
  }

  @Override
  public void deserializeNBT(NBTBase nbt) {
    TEAM_POS_CAP.getStorage().readNBT(TEAM_POS_CAP, this.instance, null, nbt);
  }
}
