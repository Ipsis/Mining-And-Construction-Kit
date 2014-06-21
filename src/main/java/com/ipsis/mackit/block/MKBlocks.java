package com.ipsis.mackit.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.ipsis.mackit.fluid.BlockFluidDye;
import com.ipsis.mackit.fluid.MKFluids;
import com.ipsis.mackit.item.ItemBeaverBlock;

import cpw.mods.fml.common.registry.GameRegistry;

public class MKBlocks {

	public static void preInit() {
		
		fixedSand = new BlockFixedEarth(Material.clay, "fixedSand");
		fixedGravel = new BlockFixedEarth(Material.clay, "fixedGravel");
		beaverBlock = new BlockBeaverBlock("beaverBlock");
		portaChant = new BlockPortaChant("portaChant");
		machineSqueezer = new BlockMachineSqueezer("machineSqueezer");
		machineStamper = new BlockMachineStamper("machineStamper");
		machinePainter = new BlockMachinePainter("machinePainter");
		machineDyeFiller = new BlockMachineDyeFiller("machineDyeFiller");
		dyeLeech = new BlockDyeLeech("dyeLeech");
		vegasBlock = new BlockVegas("vegasBlock");
		
		powerBlock = new BlockPowerBlock("powerBlock");
		
		blockFluidDyeRed = new BlockFluidDye(MKFluids.fluidDyeRed, "redDye");
		blockFluidDyeYellow = new BlockFluidDye(MKFluids.fluidDyeYellow, "yellowDye");
		blockFluidDyeBlue = new BlockFluidDye(MKFluids.fluidDyeBlue, "blueDye");
		blockFluidDyeWhite = new BlockFluidDye(MKFluids.fluidDyeWhite, "whiteDye");
		blockFluidDyePure = new BlockFluidDye(MKFluids.fluidDyePure, "pureDye");
		
		testFaced = new BlockTestFaced(Material.iron, "test");
		
		
		GameRegistry.registerBlock(fixedSand, "block.fixedSand");
		GameRegistry.registerBlock(fixedGravel, "block.fixedGravel");
		GameRegistry.registerBlock(beaverBlock, ItemBeaverBlock.class, "block.beaverBlock");
		GameRegistry.registerBlock(portaChant, "block.portaChant");
		GameRegistry.registerBlock(machineSqueezer, "block.machineSqueezer");
		GameRegistry.registerBlock(machineStamper, "block.machineStamper");
		GameRegistry.registerBlock(machinePainter, "block.machinePainter");
		GameRegistry.registerBlock(machineDyeFiller, "block.machineDyeFiller");
		GameRegistry.registerBlock(dyeLeech, "block.dyeLeech");
		GameRegistry.registerBlock(vegasBlock, "block.vegasBlock");
		
		GameRegistry.registerBlock(blockFluidDyeRed, "block.redDye");
		GameRegistry.registerBlock(blockFluidDyeYellow, "block.yellowDye");
		GameRegistry.registerBlock(blockFluidDyeBlue, "block.blueDye");
		GameRegistry.registerBlock(blockFluidDyeWhite, "block.whiteDye");
		GameRegistry.registerBlock(blockFluidDyePure, "block.pureDye");
		
		GameRegistry.registerBlock(powerBlock,  "block.powerBlock");
		
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
	public static Block machineSqueezer;
	public static Block machineStamper;
	public static Block machinePainter;
	public static Block machineDyeFiller;
	public static Block dyeLeech;
	public static Block powerBlock;
	public static Block vegasBlock;
	
	public static BlockFluidDye blockFluidDyeRed;
	public static BlockFluidDye blockFluidDyeYellow;
	public static BlockFluidDye blockFluidDyeBlue;
	public static BlockFluidDye blockFluidDyeWhite;
	public static BlockFluidDye blockFluidDyePure;
	
	public static Block testFaced;
}
