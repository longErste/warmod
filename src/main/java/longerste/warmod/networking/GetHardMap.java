package longerste.warmod.networking;

import longerste.warmod.capability.TeamHardness.ITeamHardness;
import longerste.warmod.capability.TeamHardness.TeamHardnessProvider;
import longerste.warmod.networking.AbstractMessage.AbstractClientMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class GetHardMap extends AbstractClientMessage<GetHardMap> {

  private short id;
  private int hardness;

  public GetHardMap() {
  }

  public GetHardMap(short id, int hardness) {
    this.id = id;
    this.hardness = hardness;
  }

  public GetHardMap(short id) {
    this.id = id;
    this.hardness = 50;
  }

  @Override
  protected void read(PacketBuffer buffer) {
    id = buffer.readShort();
    hardness = buffer.readInt();
  }

  @Override
  protected void write(PacketBuffer buffer) {
    buffer.writeShort(id);
    buffer.writeInt(hardness);
  }

  @Override
  public void process(MessageContext ctx, Side side) {
    Minecraft minecraft = Minecraft.getMinecraft();
    ITeamHardness TeamHardness = minecraft.world.getCapability(TeamHardnessProvider.TEAM_HARDNESS_CAP, null);
    TeamHardness.setTeamHardness(id, hardness);
  }
}
