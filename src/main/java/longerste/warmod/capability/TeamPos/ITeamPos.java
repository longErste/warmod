package longerste.warmod.capability.TeamPos;


import java.util.HashMap;
import net.minecraft.util.math.BlockPos;

public interface ITeamPos {

  BlockPos getTeamBlockPos(short id);

  void setTeamBlockPos(short id, BlockPos pos);

  boolean hasTeam(short id);

  HashMap<Short, BlockPos> getTeamsPos();
}
