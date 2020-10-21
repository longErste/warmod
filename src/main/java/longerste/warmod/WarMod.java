package longerste.warmod;

import longerste.warmod.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
    modid = WarMod.MODID,
    name = WarMod.MODNAME,
    version = WarMod.MODVERSION,
    dependencies = "required-after:forge@[14.23.5.2847,)",
    useMetadata = true)
public class WarMod {
  public static final String MODID = "warmod";
  public static final String MODNAME = "War of Minecraft";
  public static final String MODVERSION = "alpha-dev";

  @SidedProxy(
      clientSide = "longerste.warmod.proxy.ClientProxy",
      serverSide = "longerste.warmod.proxy.ServerProxy")
  public static CommonProxy proxy;

  @Mod.Instance public static WarMod instance;

  public static Logger logger;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
    proxy.preInit(event);
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent e) {
    proxy.init(e);
  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent e) {
    proxy.postInit(e);
  }
}
