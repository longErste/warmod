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

  public PacketModifyFoundation(BlockPos pos, int field, int value) {
    this.xPos = pos.getX();
    this.yPos = pos.getY();
    this.zPos = pos.getZ();
    this.field = field;
    this.value = value;
  }

  public BlockPos getBlockPos(){
    return new BlockPos(xPos, yPos, zPos);
  }

  public int getField() {
    return field;
  }

  public int getValue() {
    return value;
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

}
