package longerste.warmod.client;

import longerste.warmod.TeamBlocks;
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
    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(TeamBlocks.foundation),0, new ModelResourceLocation(TeamBlocks.foundation.getRegistryName(), "inventory"));
  }

}
