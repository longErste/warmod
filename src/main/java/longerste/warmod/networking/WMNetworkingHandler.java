package longerste.warmod.networking;

import longerste.warmod.WarMod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class WMNetworkingHandler {
  public static final SimpleNetworkWrapper dispatcher =
      NetworkRegistry.INSTANCE.newSimpleChannel(WarMod.MODID);
  private static int packetId = 0;

  public static final void registerPackets() {
    registerMessage(ModifyFoundationMessage.class);
    registerMessage(GetTeamNameMessage.class);
    registerMessage(GetHardMap.class);
  }

  private static final <T extends AbstractMessage<T> & IMessageHandler<T, IMessage>> void registerMessage(Class <T> msgClass) {
    if(AbstractMessage.AbstractClientMessage.class.isAssignableFrom(msgClass)) {
      dispatcher.registerMessage(msgClass, msgClass, packetId++, Side.CLIENT);
    } else if (AbstractMessage.AbstractServerMessage.class.isAssignableFrom(msgClass)) {
      dispatcher.registerMessage(msgClass, msgClass, packetId++, Side.SERVER);
    } else {
      dispatcher.registerMessage(msgClass, msgClass, packetId++, Side.CLIENT);
      dispatcher.registerMessage(msgClass, msgClass, packetId++, Side.SERVER);
    }
  }
}
