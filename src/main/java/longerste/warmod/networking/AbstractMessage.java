package longerste.warmod.networking;

import com.google.common.base.Throwables;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import longerste.warmod.WarMod;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class AbstractMessage<T extends AbstractMessage<T>> implements IMessage, IMessageHandler<T, IMessage> {

  protected abstract void read(PacketBuffer buffer) throws IOException;

  protected abstract void write(PacketBuffer buffer) throws IOException;

  public abstract void process(MessageContext ctx, Side side);

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
    } else if(msg.requiresMainThread()){
      AbstractMessage.checkThreadAndEnqueue(msg, ctx);
    } else {
      msg.process(ctx, ctx.side);
    }
    return null;
  }

  private static final <T extends AbstractMessage<T>> void checkThreadAndEnqueue(final AbstractMessage<T> msg, final MessageContext ctx) {
    IThreadListener thread;
    try {
      thread = ctx.getServerHandler().player.getServer();
    } catch (ClassCastException e){
      thread = Minecraft.getMinecraft();
    }
    thread.addScheduledTask(new Runnable() {
      public void run() {
        msg.process(ctx, ctx.side);
      }
    });
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
