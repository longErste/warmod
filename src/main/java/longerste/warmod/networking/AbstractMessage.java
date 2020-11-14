package longerste.warmod.networking;

import com.google.common.base.Throwables;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class AbstractMessage<T extends AbstractMessage<T>> implements IMessage, IMessageHandler<T, IMessage> {

  public static EntityPlayer getPlayerEntity(MessageContext ctx) {
    return ctx.getServerHandler().player;
  }

  protected abstract void read(PacketBuffer buffer) throws IOException;

  protected abstract void write(PacketBuffer buffer) throws IOException;

  public abstract void process(T msg, EntityPlayer player, Side side);

  protected boolean isValidOnSide(Side side) {
    return true;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    try {
      read(new PacketBuffer(buf));
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public void toBytes(ByteBuf buf) {
    try {
      write(new PacketBuffer(buf));
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public IMessage onMessage(T msg, MessageContext ctx) {
    if (!msg.isValidOnSide(ctx.side)) {
      throw new RuntimeException("Invalid side " + ctx.side.name() + "for" + msg.getClass().getSimpleName());
    } else {
      msg.process(msg, getPlayerEntity(ctx), ctx.side);
    }
    return null;
  }

  protected boolean requiresMainThread() {
    return true;
  }

  public abstract static class AbstractClientMessage<T extends AbstractMessage<T>> extends AbstractMessage<T> {
    @Override
    protected boolean isValidOnSide(Side side) {
      return side.isClient();
    }

  }
  public abstract static class AbstractServerMessage<T extends AbstractMessage<T>> extends AbstractMessage<T> {
    @Override
    protected boolean isValidOnSide(Side side) {
      return side.isServer();
    }

  }
}
