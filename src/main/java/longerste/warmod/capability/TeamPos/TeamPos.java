package longerste.warmod.capability.TeamPos;

import java.util.HashMap;
import net.minecraft.util.math.BlockPos;

public class TeamPos implements ITeamPos{
  private HashMap<Short, BlockPos> teamPos = new HashMap<Short, BlockPos>();

  public BlockPos getTeamBlockPos(short id) {
    return teamPos.get(id);
  }

  public HashMap<Short, BlockPos> getTeamsPos() {
    return this.teamPos;
  }

  public void setTeamBlockPos(short id, BlockPos pos) {
    teamPos.put(id, pos);
  }

  public boolean hasTeam(short id) {
    return teamPos.containsKey(id);
  }
}
