package longerste.warmod.capability.TeamHardness;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class TeamHardnessProvider implements ICapabilitySerializable<NBTBase> {

  @CapabilityInject(ITeamHardness.class)
  public static final Capability<ITeamHardness> TEAM_HARDNESS_CAP = null;

  private ITeamHardness instance = TEAM_HARDNESS_CAP.getDefaultInstance();

  @Override
  public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
    return capability == TEAM_HARDNESS_CAP;
  }

  @Override
  public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
    T result;
    if (capability == TEAM_HARDNESS_CAP) {
      result = TEAM_HARDNESS_CAP.<T>cast(this.instance);
    } else {
      result = null;
    }
    return result;
  }

  @Override
  public NBTBase serializeNBT() {
    return TEAM_HARDNESS_CAP.getStorage().writeNBT(TEAM_HARDNESS_CAP, this.instance, null);
  }

  @Override
  public void deserializeNBT(NBTBase nbt) {
  TEAM_HARDNESS_CAP.getStorage().readNBT(TEAM_HARDNESS_CAP, this.instance, null, nbt);
  }
}
