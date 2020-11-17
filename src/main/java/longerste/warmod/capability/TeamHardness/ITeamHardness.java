package longerste.warmod.capability.TeamHardness;

import java.util.HashMap;

public interface ITeamHardness {
  HashMap<Short, Integer> getTeamsHardness();
  void setTeamHardness(short id, int hardness);
  int getTeamHardness(short id);
  void setTeamHardness(short id);
}
