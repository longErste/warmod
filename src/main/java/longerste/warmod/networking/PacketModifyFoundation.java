package longerste.warmod.networking;

import io.netty.buffer.ByteBuf;
import longerste.warmod.tile.FoundationTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketModifyFoundation implements IMessage {
  public PacketModifyFoundation() {}

  private int value;
  private int field;
  private int xPos, yPos, zPos;

  public PacketModifyFoundation(int xPos, int yPos, int zPos, int field, int value) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.zPos = zPos;
    this.field = field;
    this.value = value;
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeInt(xPos);
    buf.writeInt(yPos);
    buf.writeInt(zPos);

    buf.writeInt(field);
    buf.writeInt(value);
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    xPos = buf.readInt();
    yPos = buf.readInt();
    zPos = buf.readInt();

    field = buf.readInt();
    value = buf.readInt();
  }

  public static class PacketModifyFoundationHandler
      implements IMessageHandler<PacketModifyFoundation, IMessage> {

    @Override
    public IMessage onMessage(PacketModifyFoundation message, MessageContext ctx) {
      World world = ctx.getServerHandler().player.world;
      TileEntity te = world.getTileEntity(new BlockPos(message.xPos, message.yPos, message.zPos));

      int field = message.field;
      int amount = message.value;

      if (te instanceof FoundationTileEntity) {
        FoundationTileEntity fte = (FoundationTileEntity) te;
        handleMessage(fte, field, amount);
      }
      return null;
    }

    private void handleMessage(FoundationTileEntity fte, int field, int amount){
      switch(field){
        case 1:
          fte.upgrade();
          break;
        case 2:
          fte.setHardness(amount);
          break;
      }

    }
  }
}
