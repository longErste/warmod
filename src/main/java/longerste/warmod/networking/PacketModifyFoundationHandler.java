package longerste.warmod.networking;

import longerste.warmod.tile.FoundationTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketModifyFoundationHandler
    implements IMessageHandler<PacketModifyFoundation, IMessage> {

  @Override
  public IMessage onMessage(PacketModifyFoundation message, MessageContext ctx) {
    World world = ctx.getServerHandler().player.world;
    TileEntity te = world.getTileEntity(message.getBlockPos());

    int field = message.getField();
    int amount = message.getValue();

    if (te instanceof FoundationTileEntity) {
      FoundationTileEntity fte = (FoundationTileEntity) te;
      handleMessage(fte, field, amount);
    }
    return null;
  }

  private void handleMessage(FoundationTileEntity fte, int field, int amount) {
    switch (field) {
      case 1:
        fte.upgrade();
        break;
      case 2:
        fte.setHardness(amount);
        break;
    }
  }
}
