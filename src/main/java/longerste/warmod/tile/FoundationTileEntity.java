package longerste.warmod.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class FoundationTileEntity extends TileEntity {
  // Constants
  public final float MIN_HARDNESS = 5f;
  public final float MAX_HARDNESS = 1000f;
  public static final int SIZE = 9;
  public static final int[] upgradePoints = {10, 20};

  // Fields
  private float hardness;
  private int tier;
  private int points;

  private ItemStackHandler itemStackHandler =
      new ItemStackHandler(SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
          FoundationTileEntity.this.markDirty();
        }
      };

  public FoundationTileEntity() {
    hardness =5;
    tier = 0;
    points = upgradePoints[0];
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {
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
    super.readFromNBT(compound);
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    compound.setFloat("hardness", hardness);
    compound.setTag("items", itemStackHandler.serializeNBT());
    compound.setInteger("tier", tier);
    compound.setInteger("upPoints", points);
    return super.writeToNBT(compound);
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

  public void decreaseHardness() {
    if (hardness > MIN_HARDNESS) {
      --hardness;
      ++points;
    }
    markDirty();
  }

  public void increaseHardness() {
    if (hardness < MAX_HARDNESS && points > 0) {
      ++hardness;
      --points;
    }
    markDirty();
  }

  public float getHardness() {
    return hardness;
  }

  public int getPointUpper() {
    return upgradePoints[tier];
  }

  public int getPoints() {
    return points;
  }

  public int getTier() {
    return tier;
  }

  public void upgrade() {
    if (tier < upgradePoints.length - 1) {
      points += upgradePoints[tier + 1] - upgradePoints[tier];
      tier += 1;
    }
    markDirty();
  }
}
