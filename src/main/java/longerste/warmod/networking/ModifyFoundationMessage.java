package longerste.warmod.networking;

import longerste.warmod.networking.AbstractMessage.AbstractServerMessage;
import longerste.warmod.tile.FoundationTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class ModifyFoundationMessage extends AbstractServerMessage<ModifyFoundationMessage> {
  private int value;
  private int field;
  private int xPos, yPos, zPos;
  public ModifyFoundationMessage() {}

  public ModifyFoundationMessage(BlockPos pos, int field, int value) {
    this.xPos = pos.getX();
    this.yPos = pos.getY();
    this.zPos = pos.getZ();
    this.field = field;
    this.value = value;
  }


  public int getField() {
    return field;
  }

  public int getValue() {
    return value;
  }

  @Override
  protected void read(PacketBuffer buf){
    xPos = buf.readInt();
    yPos = buf.readInt();
    zPos = buf.readInt();

    field = buf.readInt();
    value = buf.readInt();
  }

  @Override
  protected void write(PacketBuffer buf){
    buf.writeInt(xPos);
    buf.writeInt(yPos);
    buf.writeInt(zPos);

    buf.writeInt(field);
    buf.writeInt(value);
  }

  @Override
  public void process(MessageContext ctx, Side side) {
    EntityPlayer player = ctx.getServerHandler().player;
    World world = player.world;
    TileEntity te = world.getTileEntity(new BlockPos(xPos, yPos, zPos));

    if(te instanceof FoundationTileEntity){
      FoundationTileEntity fte = (FoundationTileEntity) te;
      switch (field) {
        case 1:
          fte.upgrade();
          break;
        case 2:
          fte.setHardness(value);
          break;
      }
    }
  }

}
