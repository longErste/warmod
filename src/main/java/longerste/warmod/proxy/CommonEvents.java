package longerste.warmod.proxy;

import longerste.warmod.block.WMBlocks;
import longerste.warmod.WarMod;
import longerste.warmod.block.buildblock.BuildingBlock;
import longerste.warmod.block.foundation.Foundation;
import longerste.warmod.item.FoundationItemBlock;
import longerste.warmod.tile.BuildingBlockTileEntity;
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
    event.getRegistry().register(new BuildingBlock());
    GameRegistry.registerTileEntity(BuildingBlockTileEntity.class, WarMod.MODID+"_building_block");
    GameRegistry.registerTileEntity(FoundationTileEntity.class, WarMod.MODID + "_foundation");
  }

  @SubscribeEvent
  public static void registerItems(Register<Item> event) {
    ItemBlock foundationItemBlock = new FoundationItemBlock(WMBlocks.foundation);
    Item buildingItemBlock = new ItemBlock(WMBlocks.buildingBlock).setRegistryName(WMBlocks.buildingBlock.getRegistryName());
    event.getRegistry().register(foundationItemBlock);
    event.getRegistry().register(buildingItemBlock);
  }
}
