package longerste.warmod.capability.TeamPos;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class TeamPosStorage implements IStorage<ITeamPos> {

  @Override
  public NBTBase writeNBT(Capability<ITeamPos> capability, ITeamPos instance, EnumFacing side) {
    HashMap<Short, BlockPos> positions = instance.getTeamsPos();
    NBTTagCompound compound = new NBTTagCompound();
    for(Map.Entry<Short, BlockPos> entry: positions.entrySet()){
      String id = entry.getKey().toString();
      BlockPos pos = entry.getValue();
      compound.setTag(id, NBTUtil.createPosTag(pos));
    }
    return compound;
  }

  @Override
  public void readNBT(Capability<ITeamPos> capability, ITeamPos instance, EnumFacing side, NBTBase nbt) {
    NBTTagCompound compound = (NBTTagCompound) nbt;
    for(String var: compound.getKeySet()){
      short id = Short.parseShort(var);
      BlockPos pos = NBTUtil.getPosFromTag((NBTTagCompound) compound.getTag(var));
      instance.setTeamBlockPos(id, pos);
    }
  }
}
