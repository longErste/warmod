package longerste.warmod.tile;

import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.feed_the_beast.ftblib.lib.data.Universe;
import longerste.warmod.Config;
import longerste.warmod.WarMod;
import longerste.warmod.capability.TeamPos.ITeamPos;
import longerste.warmod.capability.TeamPos.TeamPosProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class FoundationTileEntity extends TileEntity {

  public static final int SIZE = 9;
  public static final int SumOfStartingPoints = Config.startingHardness;
  public static final int[] upgradePoints = {10, 20};
  // Constants
  public final float MIN_HARDNESS = (float) Config.minimumHardness;
  public final float MAX_HARDNESS = (float) Config.maximumHardness;
  // Fields
  private float hardness = (float) Config.startingHardness;
  private int tier = 0;
  private int points = upgradePoints[tier];

  private ItemStackHandler itemStackHandler =
      new ItemStackHandler(SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
          FoundationTileEntity.this.markDirty();
        }
      };
  private short team;
  private String teamName;

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);
    if (compound.hasKey("items")) {
      itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
    }
    if (compound.hasKey("hardness")) {
      hardness = compound.getFloat("hardness");
    }
    if (compound.hasKey("tier")) {
      tier = compound.getInteger("tier");
    }
    if (compound.hasKey("upPoints")) {
      points = compound.getInteger("upPoints");
    }
    if (compound.hasKey("team")) {
      team = compound.getShort("team");
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    compound = super.writeToNBT(compound);
    compound.setFloat("hardness", hardness);
    compound.setTag("items", itemStackHandler.serializeNBT());
    compound.setInteger("tier", tier);
    compound.setInteger("upPoints", points);
    compound.setShort("team", team);
    return compound;
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

  public boolean canInteractWith(EntityPlayer playerIn) {
    return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
  }

  @Override
  public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return true;
    }
    return super.hasCapability(capability, facing);
  }

  @Override
  public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
    }
    return super.getCapability(capability, facing);
  }

  public float getHardness() {
    return hardness;
  }

  public void setHardness(int amount) {
    float newHardness = hardness + amount;
    if (MIN_HARDNESS <= newHardness && newHardness <= MAX_HARDNESS && points >= amount) {
      points -= amount;
      hardness += amount;
      markDirty();
    }
  }

  public int getPoints() {
    return points;
  }

  public int getTier() {
    return tier;
  }

  public short getTeamId() {
    return this.team;
  }

  public ForgeTeam getTeam() {
    return Universe.get().getTeam(team);
  }

  public void setTeam(ForgeTeam team) {
    this.setTeam(team.getUID());
  }

  public void setTeam(short uid) {
    if (!this.getWorld().isRemote) {
      ITeamPos teamPos = this.getWorld().getCapability(TeamPosProvider.TEAM_POS_CAP, null);
      if (!hasTeam()) {
        if (teamPos.hasTeam(uid)) {
          this.team = 0;
          WarMod.logger.warn("WTF this is impossible");
        } else {
          this.team = uid;
          teamPos.setTeamBlockPos(uid, this.getPos());
        }
      }
    } else if (!hasTeam()) {
      this.team = uid;
    }

    markDirty();
  }

  public boolean hasTeam() {
    return Universe.get().getTeam(team).isValid();
  }

  public void upgrade() {
    if (tier < upgradePoints.length - 1) {
      points += upgradePoints[tier + 1] - upgradePoints[tier];
      tier += 1;
      markDirty();
    }
  }

  public int getMaxPoints() {
    return upgradePoints[tier] + SumOfStartingPoints;
  }
}
