package com.ipsis.mackit;
import com.ipsis.mackit.block.MKBlocks;
import com.ipsis.mackit.item.MKItems;
import com.ipsis.mackit.reference.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = MacKit.VERSION)
public class MacKit
{
    public static final String VERSION = "0.1a";
    
    @Instance
    public static MacKit instance;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    	MKItems.preInit();
    	MKBlocks.preInit();
	}
    
    @EventHandler
    public void initialize(FMLInitializationEvent event) {

    	MKItems.initialize();
    	MKBlocks.initialize();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	
    	MKItems.postInit();
    	MKBlocks.postInit();
    }
    
    
}