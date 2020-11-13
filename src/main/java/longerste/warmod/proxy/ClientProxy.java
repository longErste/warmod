package longerste.warmod.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)

public class ClientProxy implements IModProxy{
  public void preInit() {

  }

  @Override
  public void preInit(FMLPreInitializationEvent event) {

  }

  @Override
  public void init(FMLInitializationEvent event) {

  }

  @Override
  public void postInit(FMLPostInitializationEvent event) {

  }

  @Override
  public EntityPlayer getPlayerEntityFromContext(MessageContext parContext) {
    return null;
  }
}
