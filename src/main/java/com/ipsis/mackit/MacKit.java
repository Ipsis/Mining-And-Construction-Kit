package com.ipsis.mackit;
import net.minecraftforge.common.MinecraftForge;

import com.ipsis.mackit.block.MKBlocks;
import com.ipsis.mackit.block.TileBeaverBlock;
import com.ipsis.mackit.block.TileMachineDyeFiller;
import com.ipsis.mackit.block.TileMachinePainter;
import com.ipsis.mackit.block.TileMachineSqueezer;
import com.ipsis.mackit.block.TileMachineStamper;
import com.ipsis.mackit.block.TilePortaChant;
import com.ipsis.mackit.block.TilePowerBlock;
import com.ipsis.mackit.block.TileTestFaced;
import com.ipsis.mackit.fluid.MKFluids;
import com.ipsis.mackit.helper.DyeHelper;
import com.ipsis.mackit.helper.DyedOriginHelper;
import com.ipsis.mackit.item.MKItems;
import com.ipsis.mackit.manager.MKManagers;
import com.ipsis.mackit.network.PacketHandler;
import com.ipsis.mackit.reference.Reference;
import com.ipsis.mackit.util.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = MacKit.VERSION)
public class MacKit
{
    public static final String VERSION = "0.1a";
    
    @SidedProxy(clientSide = "com.ipsis.mackit.util.proxy.ClientProxy", serverSide = "com.ipsis.mackit.util.proxy.CommonProxy")
    public static CommonProxy proxy;   
    
    @Instance
    public static MacKit instance;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    	PacketHandler.init();
    	
    	MKFluids.preInit();
    	MKItems.preInit();
    	MKBlocks.preInit();
    	MKManagers.preInit();    
    	
    	FMLInterModComms.sendMessage("Waila", "register", "com.ipsis.mackit.waila.MKWailaProvider.callbackRegister");
	}
    
    @EventHandler
    public void initialize(FMLInitializationEvent event) {

    	DyeHelper.initialise(); 
    	
    	MKFluids.initialise();
    	MKItems.initialise();
    	MKBlocks.initialise();    	   	
    	MKManagers.initialise();
    	
    	GameRegistry.registerTileEntity(TileBeaverBlock.class, "tile.beaverBlock");
    	GameRegistry.registerTileEntity(TilePortaChant.class, "tile.portaChant");
    	GameRegistry.registerTileEntity(TileMachineSqueezer.class, "tile.machineSqueezer");
    	GameRegistry.registerTileEntity(TileMachineStamper.class, "tile.machineStamper");
    	GameRegistry.registerTileEntity(TileMachinePainter.class, "tile.machinePainter");
    	GameRegistry.registerTileEntity(TileMachineDyeFiller.class, "tile.machineDyeFiller");
    	    	
    	GameRegistry.registerTileEntity(TilePowerBlock.class, "tile.powerBlock");
    	
    	GameRegistry.registerTileEntity(TileTestFaced.class, "tile.testFaced");
    	
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
    	MinecraftForge.EVENT_BUS.register(proxy);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	
    	DyeHelper.loadItemRecipes(); 
    	DyedOriginHelper.load();
    	
    	MKFluids.postInit();
    	MKItems.postInit();
    	MKBlocks.postInit();
    	   	
    	MKManagers.postInit();
    	
    	DyeHelper.debugDumpMap();
    	DyedOriginHelper.debugDumpMap();
    }
    
    
}