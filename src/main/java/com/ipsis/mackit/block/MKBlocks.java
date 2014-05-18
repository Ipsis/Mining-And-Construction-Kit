package com.ipsis.mackit.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class MKBlocks {

	public static void preInit() {
		
		fixedSand = new BlockFixedEarth(Material.ground, "fixedSand");
		fixedGravel = new BlockFixedEarth(Material.ground, "fixedGravel");
		beaverBlock = new BlockBeaverBlock("beaverBlock");
		
		GameRegistry.registerBlock(fixedSand, "block.fixedSand");
		GameRegistry.registerBlock(fixedGravel, "block.fixedGravel");
		GameRegistry.registerBlock(beaverBlock, "beaverBlock");
	}
	
	public static void initialise() {
		
	}
	
	public static void postInit() {
		
		
	}
	
	public static Block fixedSand;
	public static Block fixedGravel;
	public static Block beaverBlock;
}
