package com.ipsis.mackit.proxy;

import com.ipsis.mackit.lib.Strings;
import com.ipsis.mackit.tileentity.TileBeaverBlock;
import com.ipsis.mackit.tileentity.TileEnchanter;
import com.ipsis.mackit.tileentity.TileMachineBBBuilder;
import com.ipsis.mackit.tileentity.TileMachineSqueezer;

import cpw.mods.fml.common.registry.GameRegistry;

public abstract class CommonProxy implements IProxy {

	public void registerTileEntities() {
		
		GameRegistry.registerTileEntity(TileEnchanter.class, "tile." + Strings.TE_ENCHANTER_NAME);
		GameRegistry.registerTileEntity(TileBeaverBlock.class, "tile." + Strings.TE_BEAVER_BLOCK_NAME);
		
		GameRegistry.registerTileEntity(TileMachineBBBuilder.class,  "tile." + Strings.TE_MACHINE_BBBUILDER_NAME);
		GameRegistry.registerTileEntity(TileMachineSqueezer.class,  "tile." + Strings.TE_MACHINE_SQUEEZER_NAME);
	}
}
