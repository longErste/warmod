package longerste.warmod.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class FoundationTileEntity extends TileEntity {
  private final float MIN_HARDNESS = 5f;
  private final float MAX_HARDNESS = 1000f;
  private float hardness = MIN_HARDNESS;
  public static final int SIZE = 9;

  private ItemStackHandler itemStackHandler =
      new ItemStackHandler(SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
          FoundationTileEntity.this.markDirty();
        }
      };

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    compound.setFloat("hardness", hardness);
    super.readFromNBT(compound);
    if (compound.hasKey("items")) {
      itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    hardness = compound.getFloat("hardness");
    compound.setTag("items", itemStackHandler.serializeNBT());
    super.writeToNBT(compound);
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
      hardness--;
    }
    markDirty();
  }

  public void increaseHardness() {
    if (hardness < MAX_HARDNESS) {
      hardness++;
    }
    markDirty();
  }

  public float getHardness() {
    return hardness;
  }
}
