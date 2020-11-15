package longerste.warmod.networking;

import com.feed_the_beast.ftblib.lib.data.Universe;
import java.io.IOException;
import longerste.warmod.gui.FoundationGui;
import longerste.warmod.networking.AbstractMessage.AbstractClientMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class GetTeamNameMessage extends AbstractClientMessage<GetTeamNameMessage> {

  private ITextComponent teamName;
  private int xPos, yPos, zPos;

  public GetTeamNameMessage() {
  }

  public GetTeamNameMessage(BlockPos pos, short id) {
    xPos = pos.getX();
    yPos = pos.getY();
    zPos = pos.getZ();
    teamName = Universe.get().getTeam(id).getTitle();
  }

  public GetTeamNameMessage(BlockPos pos, ITextComponent name) {
    xPos = pos.getX();
    yPos = pos.getY();
    zPos = pos.getZ();
    teamName = name;
  }

  @Override
  protected void read(PacketBuffer buffer) throws IOException {
    xPos = buffer.readInt();
    yPos = buffer.readInt();
    zPos = buffer.readInt();
    teamName = buffer.readTextComponent();
  }

  @Override
  protected void write(PacketBuffer buffer) {
    buffer.writeInt(xPos);
    buffer.writeInt(yPos);
    buffer.writeInt(zPos);
    buffer.writeTextComponent(teamName);
  }

  @Override
  public void process(MessageContext ctx, Side side) {
    Minecraft minecraft = Minecraft.getMinecraft();
    if(minecraft.currentScreen instanceof FoundationGui) {
      FoundationGui gui = (FoundationGui) minecraft.currentScreen;
      gui.setTeamName(teamName.getFormattedText());
    }

  }

}
