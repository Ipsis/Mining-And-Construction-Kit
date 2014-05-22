package com.ipsis.mackit.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;

public class MKBlocks {

	public static void preInit() {
		
		fixedSand = new BlockFixedEarth(Material.ground, "fixedSand");
		fixedGravel = new BlockFixedEarth(Material.ground, "fixedGravel");
		beaverBlock = new BlockBeaverBlock("beaverBlock");
		portaChant = new BlockPortaChant("portaChant");
		
		testFaced = new BlockTestFaced(Material.iron, "test");
		
		
		GameRegistry.registerBlock(fixedSand, "block.fixedSand");
		GameRegistry.registerBlock(fixedGravel, "block.fixedGravel");
		GameRegistry.registerBlock(beaverBlock, "block.beaverBlock");
		GameRegistry.registerBlock(portaChant, "block.portaChant");
		
		GameRegistry.registerBlock(testFaced, "block.test");
	}
	
	public static void initialise() {
		
	}
	
	public static void postInit() {
		
		
	}
	
	public static Block fixedSand;
	public static Block fixedGravel;
	public static Block beaverBlock;
	public static Block portaChant;
	
	public static Block testFaced;
}
