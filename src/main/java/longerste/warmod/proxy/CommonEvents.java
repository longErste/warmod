package longerste.warmod.proxy;

import longerste.warmod.TeamBlocks;
import longerste.warmod.WarMod;
import longerste.warmod.block.Foundation.Foundation;
import longerste.warmod.tile.FoundationTileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class CommonEvents {

  @SubscribeEvent
  public static void registerBlocks(RegistryEvent.Register<Block> event) {
    event.getRegistry().register(new Foundation());
    GameRegistry.registerTileEntity(FoundationTileEntity.class, WarMod.MODID + "_foundation");
  }

  @SubscribeEvent
  public static void registerItems(Register<Item> event) {
    event
        .getRegistry()
        .register(
            new ItemBlock(TeamBlocks.foundation)
                .setRegistryName(TeamBlocks.foundation.getRegistryName()));
  }
}
