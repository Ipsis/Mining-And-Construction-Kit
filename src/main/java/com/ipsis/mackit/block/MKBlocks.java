package com.ipsis.mackit.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class MKBlocks {

	public static void preInit() {
		
		fixedSand = new BlockFixedEarth(Material.ground, "fixedSand");
		fixedGravel = new BlockFixedEarth(Material.ground, "fixedGravel");
		
		GameRegistry.registerBlock(fixedSand, "block.fixedSand");
		GameRegistry.registerBlock(fixedGravel, "block.fixedGravel");
	}
	
	public static void initialize() {
		
	}
	
	public static void postInit() {
		
		
	}
	
	public static Block fixedSand;
	public static Block fixedGravel;
}
