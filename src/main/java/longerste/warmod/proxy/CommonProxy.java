package longerste.warmod.proxy;

import java.io.File;
import longerste.warmod.Config;
import longerste.warmod.TeamBlocks;
import longerste.warmod.blocks.Foundation;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CommonProxy {

  public static Configuration config;

  public void preInit(FMLPreInitializationEvent e) {
    File directory = e.getModConfigurationDirectory();
    config = new Configuration(new File(directory.getPath(), "WarOfMinecraft.cfg"));
    Config.readConfig();
  }

  public void init(FMLInitializationEvent e) {

  }
  public void postInit(FMLPostInitializationEvent e) {
    if (config.hasChanged()) {
      config.save();
    }
  }

  @SubscribeEvent
  public static void registerBlocks(RegistryEvent.Register<Block> event) {
    event.getRegistry().register(new Foundation());
  }

  @SubscribeEvent
  public static void registerItems(Register<Item> event) {
    event.getRegistry().register(new ItemBlock(TeamBlocks.foundation).setRegistryName(TeamBlocks.foundation.getRegistryName()));
  }

}
