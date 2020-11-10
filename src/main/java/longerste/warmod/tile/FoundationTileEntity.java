package longerste.warmod.tile;

import longerste.warmod.WarMod;
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
  private float hardness = 5f;
  private int tier = 0;
  private int points = upgradePoints[tier];

  private ItemStackHandler itemStackHandler =
      new ItemStackHandler(SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
          FoundationTileEntity.this.markDirty();
        }
      };

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
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    compound = super.writeToNBT(compound);
    compound.setFloat("hardness", hardness);
    compound.setTag("items", itemStackHandler.serializeNBT());
    compound.setInteger("tier", tier);
    System.out.println(points);
    compound.setInteger("upPoints", points);
    return compound;
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
