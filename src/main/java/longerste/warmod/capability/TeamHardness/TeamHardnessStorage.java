package longerste.warmod.capability.TeamHardness;

import java.util.HashMap;
import java.util.Map;
import longerste.warmod.capability.TeamPos.ITeamPos;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class TeamHardnessStorage implements IStorage<ITeamHardness> {

  @Override
  public NBTBase writeNBT(Capability<ITeamHardness> capability, ITeamHardness instance, EnumFacing side) {
    HashMap<Short, Integer> hardnessMap = instance.getTeamsHardness();
    NBTTagCompound compound = new NBTTagCompound();
    for(Map.Entry<Short, Integer> entry: hardnessMap.entrySet()){
      String id = entry.getKey().toString();
      Integer hardness = entry.getValue();
      compound.setInteger(id, hardness);
    }
    return compound;
  }

  @Override
  public void readNBT(Capability<ITeamHardness> capability, ITeamHardness instance, EnumFacing side, NBTBase nbt) {
    NBTTagCompound compound = (NBTTagCompound) nbt;
    for(String var: compound.getKeySet()){
      short id = Short.parseShort(var);
      Integer hardness = compound.getInteger(var);
      instance.setTeamHardness(id, hardness);
    }
  }
}
