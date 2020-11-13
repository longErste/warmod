package longerste.warmod;

import com.feed_the_beast.ftblib.FTBLib;
import java.io.File;
import longerste.warmod.gui.WarModGUIHandler;
import longerste.warmod.networking.WarModNetworkingHandler;
import longerste.warmod.proxy.IModProxy;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

@Mod(
    modid = WarMod.MODID,
    name = WarMod.MODNAME,
    version = WarMod.MODVERSION,
    dependencies = "required-after:forge@[14.23.5.2847,);required-after:" + FTBLib.MOD_ID,
    useMetadata = true)
public class WarMod {
  public static final String MODID = "warmod";
  public static final String MODNAME = "Epics of Raids and Destruction";
  public static final String MODVERSION = "alpha-dev";

  @SidedProxy(
      clientSide = "longerste.warmod.proxy.ClientProxy",
      serverSide = "longerste.warmod.proxy.ServerProxy")

  public static IModProxy proxy;
  @Mod.Instance public static WarMod instance;

  public static Logger logger;

  public static Configuration config;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent e) {
    File directory = e.getModConfigurationDirectory();
    config = new Configuration(new File(directory.getPath(), "WarOfMinecraft.cfg"));
    Config.readConfig();
    proxy.preInit(e);
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent e) {
    NetworkRegistry.INSTANCE.registerGuiHandler(WarMod.instance, new WarModGUIHandler());
    WarModNetworkingHandler.registerPackets();
    proxy.init(e);
  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent e) {
    if (config.hasChanged()) {
      config.save();
    }
    proxy.postInit(e);
  }

}
