package longerste.warmod.client.gui;

import longerste.warmod.block.Foundation.FoundationContainer;
import longerste.warmod.tile.FoundationTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class WMGUIHandler implements IGuiHandler {

  @Override
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    BlockPos pos = new BlockPos(x, y, z);
    TileEntity te = world.getTileEntity(pos);
    if (te instanceof FoundationTileEntity) {
      return new FoundationContainer(player.inventory, (FoundationTileEntity) te);
    }
    return null;
  }

  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    BlockPos pos = new BlockPos(x, y, z);
    TileEntity te = world.getTileEntity(pos);
    if (te instanceof FoundationTileEntity) {
      FoundationTileEntity fte = (FoundationTileEntity) te;
      return new FoundationGui(fte, new FoundationContainer(player.inventory, fte));
    }
    return null;
  }
}
