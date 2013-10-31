package ipsis.mackit;

import ipsis.mackit.block.ModBlocks;
import ipsis.mackit.configuration.ConfigurationHandler;
import ipsis.mackit.core.util.LogHelper;
import ipsis.mackit.creativetab.CreativeTabMacKit;
import ipsis.mackit.fluid.ModFluids;
import ipsis.mackit.item.ModItems;
import ipsis.mackit.lib.Reference;
import ipsis.mackit.network.PacketHandler;
import ipsis.mackit.proxy.CommonProxy;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER)
@NetworkMod(channels = { Reference.CHANNEL_NAME }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class MacKit {

	@Instance(Reference.MOD_ID)
	public static MacKit instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static CreativeTabs tabsMacKit = new CreativeTabMacKit(CreativeTabs.getNextID(), Reference.MOD_ID);
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		
		LogHelper.init();
		
		ConfigurationHandler.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.CHANNEL_NAME + File.separator + Reference.MOD_ID + ".cfg"));
		ModItems.init();
		ModFluids.init();
		ModBlocks.init();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		proxy.registerTileEntities();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
