package longerste.warmod.client;

import longerste.warmod.block.WMBlocks;
import longerste.warmod.WarMod;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = WarMod.MODID)
public class ClientEvent {
  @SubscribeEvent
  public static void registerModels(ModelRegistryEvent event){
    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(WMBlocks.foundation),0, new ModelResourceLocation(WMBlocks.foundation.getRegistryName(), "inventory"));
    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(WMBlocks.buildingBlock), 0, new ModelResourceLocation(WMBlocks.buildingBlock.getRegistryName(), "inventory"));
  }

}
