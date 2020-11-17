package longerste.warmod.tile;

import longerste.warmod.Config;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class BuildingBlockTileEntity extends TileEntity {
  public final float MIN_HARDNESS = (float) Config.minimumHardness;
  public final float MAX_HARDNESS = (float) Config.maximumHardness;
  private short team;

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);
    if (compound.hasKey("team")) {
      team = (short) compound.getInteger("team");
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    compound =  super.writeToNBT(compound);
    compound.setShort("team", team);
    return compound;
  }

  public short getTeam(){
    return team;
  }
  public void setTeam(short team) {
    this.team = team;
  }

  @Override
  public NBTTagCompound getUpdateTag() {
    // getUpdateTag() is called whenever the chunk's data is sent to the
    // client. In contrast getUpdatePacket() is called when the tile entity
    // itself wants to sync to the client. In many cases you want to send
    // over the same information in getUpdateTag() as in getUpdatePacket().
    return writeToNBT(new NBTTagCompound());
  }

  @Override
  public SPacketUpdateTileEntity getUpdatePacket() {
    // Prepare a packet for syncing our TE to the client. Since we only have to sync the stack
    // and that's all we have we just write our entire NBT here. If you have a complex
    // tile entity that doesn't need to have all information on the client you can write
    // a more optimal NBT here.
    NBTTagCompound nbtTag = new NBTTagCompound();
    this.writeToNBT(nbtTag);
    return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
  }

  @Override
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
    // Here we get the packet from the server and read it into our client side tile entity
    this.readFromNBT(packet.getNbtCompound());
  }
}
