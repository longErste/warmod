package longerste.warmod.capability.TeamHardness;

import java.util.HashMap;
import longerste.warmod.Config;
import longerste.warmod.networking.GetHardMap;
import longerste.warmod.networking.WMNetworkingHandler;
import longerste.warmod.tile.BuildingBlockTileEntity;

public class TeamHardness implements ITeamHardness{
  private static int defaultHardness = Config.defaultHardness;
  private HashMap<Short, Integer> teamHard = new HashMap<Short, Integer>();

  public TeamHardness() {
    teamHard.put((short) 0, 10);
  }

  @Override
  public HashMap<Short, Integer> getTeamsHardness() {
    return teamHard;
  }

  @Override
  public void setTeamHardness(short id, int hardness) {
    teamHard.put(id, hardness);
  }

  @Override
  public int getTeamHardness(short id) {
    return teamHard.get(id);
  }

  @Override
  public void setTeamHardness(short id) {
    setTeamHardness(id, defaultHardness);
  }

}
