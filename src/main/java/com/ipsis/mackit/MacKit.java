package com.ipsis.mackit;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;

import com.ipsis.mackit.block.ModBlocks;
import com.ipsis.mackit.configuration.ConfigurationHandler;
import com.ipsis.mackit.creativetab.CreativeTabMK;
import com.ipsis.mackit.handler.GuiHandler;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.item.ModItems;
import com.ipsis.mackit.lib.Reference;
import com.ipsis.mackit.network.PacketHandler;
import com.ipsis.mackit.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, dependencies = Reference.DEPENDENCIES)
@NetworkMod(channels = {Reference.CHANNEL_NAME}, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class MacKit {
	
	@Instance(Reference.MOD_ID)
	public static MacKit instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static CreativeTabs tabsMacKit = new CreativeTabMK(CreativeTabs.getNextID(), Reference.MOD_ID);
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
	
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		/* set version number */
        event.getModMetadata().version = Reference.VERSION_NUMBER;
        
        /* Initialize the log helper */
        LogHelper.init();
        
        /* init the configuration */
        ConfigurationHandler.init(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.CHANNEL_NAME.toLowerCase() + File.separator);
		
		/* init the blocks */
        ModBlocks.init();
        
        /* init the items */
        ModItems.init();
        
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		/* Gui */
		NetworkRegistry.instance().registerGuiHandler(instance, new GuiHandler());
		
	
		/* Tile entities */
		proxy.registerTileEntities();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
	

}
