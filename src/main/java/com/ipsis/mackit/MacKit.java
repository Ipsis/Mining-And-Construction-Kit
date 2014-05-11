package com.ipsis.mackit;
import net.minecraft.init.Blocks;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = MacKit.MODID, version = MacKit.VERSION)
public class MacKit
{
    public static final String MODID = "mackit";
    public static final String VERSION = "0.1a";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		// some example code
        System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
    }
}