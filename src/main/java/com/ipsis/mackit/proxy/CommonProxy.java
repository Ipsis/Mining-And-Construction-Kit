package com.ipsis.mackit.proxy;

import com.ipsis.mackit.lib.Strings;
import com.ipsis.mackit.tileentity.TileBeaverBlock;
import com.ipsis.mackit.tileentity.TileEnchanter;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void registerTileEntities() {
		
		GameRegistry.registerTileEntity(TileEnchanter.class, "tile." + Strings.TE_ENCHANTER_NAME);
		GameRegistry.registerTileEntity(TileBeaverBlock.class, "tile." + Strings.TE_BEAVER_BLOCK_NAME);
	}
}
