package ipsis.mackit;

import ipsis.mackit.block.ModBlocks;
import ipsis.mackit.configuration.ConfigurationHandler;
import ipsis.mackit.core.util.LogHelper;
import ipsis.mackit.item.ModItems;
import ipsis.mackit.lib.Reference;
import ipsis.mackit.proxy.CommonProxy;

import java.io.File;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER)
public class MacKit {

	@Instance(Reference.MOD_ID)
	public static MacKit instace;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		
		LogHelper.init();
		
		ConfigurationHandler.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.CHANNE_NAME + File.separator + Reference.MOD_ID + ".cfg"));
		ModItems.init();
		ModBlocks.init();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		proxy.registerTileEntities();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
