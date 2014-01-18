package com.ipsis.mackit.block;

import net.minecraft.block.Block;

import com.ipsis.mackit.item.ItemBlockFixedEarth;
import com.ipsis.mackit.lib.BlockIds;
import com.ipsis.mackit.lib.Strings;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	public static BlockEnchanter enchanter;
	public static Block fixedEarth;
	public static BlockBeaverBlock beaverBlock;

	
	public static void init()
	{
		enchanter = new BlockEnchanter(BlockIds.ENCHANTER);
		fixedEarth = new BlockFixedEarth(BlockIds.FIXED_EARTH);
		beaverBlock = new BlockBeaverBlock(BlockIds.BEAVER_BLOCK);
		
		/* Register with game */
		GameRegistry.registerBlock(enchanter, "block." + Strings.ENCHANTER_NAME);
		GameRegistry.registerBlock(fixedEarth, ItemBlockFixedEarth.class, "block." + Strings.FIXED_EARTH_NAME);
		GameRegistry.registerBlock(beaverBlock,  "block." + Strings.BEAVER_BLOCK_NAME);
	}
}
