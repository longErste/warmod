package longerste.warmod.networking;

import longerste.warmod.WarMod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class WarModNetworkingHandler {
  private static int packetId = 0;

  public static final SimpleNetworkWrapper dispatcher =
      NetworkRegistry.INSTANCE.newSimpleChannel(WarMod.MODID);

  public static final void registerPackets() {
    registerMessage(PacketModifyFoundationHandler.class, PacketModifyFoundation.class, Side.SERVER);
  }

  private static final void registerMessage(Class handlerClass, Class messageClass, Side side) {
    dispatcher.registerMessage(handlerClass, messageClass, packetId++, side);
  }

  public static final void sendToServer(IMessage message) {
    dispatcher.sendToServer(message);
  }


}
