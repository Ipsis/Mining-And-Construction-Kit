package com.ipsis.mackit.block;

import net.minecraft.block.Block;

import com.ipsis.mackit.fluid.ModFluids;
import com.ipsis.mackit.item.ItemBlockFixedEarth;
import com.ipsis.mackit.lib.BlockIds;
import com.ipsis.mackit.lib.Strings;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	public static BlockEnchanter enchanter;
	public static Block fixedEarth;
	public static BlockBeaverBlock beaverBlock;
	
	public static Block redDye;
	public static Block yellowDye;
	public static Block blueDye;
	public static Block whiteDye;
	public static Block pureDye;
	
	public static void init()
	{
		enchanter = new BlockEnchanter(BlockIds.ENCHANTER);
		fixedEarth = new BlockFixedEarth(BlockIds.FIXED_EARTH);
		beaverBlock = new BlockBeaverBlock(BlockIds.BEAVER_BLOCK);
		
		/* Register with game */
		GameRegistry.registerBlock(enchanter, "block." + Strings.ENCHANTER_NAME);
		GameRegistry.registerBlock(fixedEarth, ItemBlockFixedEarth.class, "block." + Strings.FIXED_EARTH_NAME);
		GameRegistry.registerBlock(beaverBlock,  "block." + Strings.BEAVER_BLOCK_NAME);
		
		/* Fluid Blocks */		
		redDye = new BlockFluidDye(BlockIds.FLUID_RED_DYE, ModFluids.redDye, Strings.RED_DYE_BLOCK_NAME);
		yellowDye = new BlockFluidDye(BlockIds.FLUID_YELLOW_DYE, ModFluids.yellowDye, Strings.YELLOW_DYE_BLOCK_NAME);
		blueDye = new BlockFluidDye(BlockIds.FLUID_BLUE_DYE, ModFluids.blueDye, Strings.BLUE_DYE_BLOCK_NAME);
		whiteDye = new BlockFluidDye(BlockIds.FLUID_WHITE_DYE, ModFluids.whiteDye, Strings.WHITE_DYE_BLOCK_NAME);
		pureDye = new BlockFluidDye(BlockIds.FLUID_PURE_DYE, ModFluids.pureDye, Strings.PURE_DYE_BLOCK_NAME);
		
		GameRegistry.registerBlock(redDye, "block." + Strings.RED_DYE_BLOCK_NAME);
		GameRegistry.registerBlock(yellowDye, "block." + Strings.YELLOW_DYE_BLOCK_NAME);
		GameRegistry.registerBlock(blueDye, "block." + Strings.BLUE_DYE_BLOCK_NAME);
		GameRegistry.registerBlock(whiteDye, "block." + Strings.WHITE_DYE_BLOCK_NAME);
		GameRegistry.registerBlock(pureDye, "block." + Strings.PURE_DYE_BLOCK_NAME);
		
	}
}
