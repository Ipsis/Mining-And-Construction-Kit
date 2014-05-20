package com.ipsis.mackit;
import com.ipsis.mackit.block.MKBlocks;
import com.ipsis.mackit.block.TileBeaverBlock;
import com.ipsis.mackit.item.MKItems;
import com.ipsis.mackit.manager.MKManagers;
import com.ipsis.mackit.reference.Reference;
import com.ipsis.mackit.util.network.CommonProxy;
import com.ipsis.mackit.util.network.packet.PacketPipeline;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = MacKit.VERSION)
public class MacKit
{
    public static final String VERSION = "0.1a";
    
    @SidedProxy(clientSide = "com.ipsis.mackit.util.network.ClientProxy", serverSide = "com.ipsis.mackit.util.network.CommonProxy")
    public static CommonProxy proxy;
    
    /* packet handler */
    public static final PacketPipeline pp = new PacketPipeline();
    
    
    @Instance
    public static MacKit instance;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    	MKItems.preInit();
    	MKBlocks.preInit();
    	MKManagers.preInit();
	}
    
    @EventHandler
    public void initialize(FMLInitializationEvent event) {

    	MKItems.initialise();
    	MKBlocks.initialise();
    	MKManagers.initialise();
    	
    	GameRegistry.registerTileEntity(TileBeaverBlock.class, "tile.beaverBlock");
    	
    	pp.initalise();
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	
    	MKItems.postInit();
    	MKBlocks.postInit();
    	MKManagers.postInit();
    	
    	pp.postInitialise();
    }
    
    
}