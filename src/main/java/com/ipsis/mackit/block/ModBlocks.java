package com.ipsis.mackit.block;

import com.ipsis.mackit.lib.BlockIds;
import com.ipsis.mackit.lib.Strings;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	public static BlockEnchanter enchanter;

	
	public static void init()
	{
		enchanter = new BlockEnchanter(BlockIds.ENCHANTER);
		
		/* Register with game */
		GameRegistry.registerBlock(enchanter, "block." + Strings.ENCHANTER_NAME);
	}
}
