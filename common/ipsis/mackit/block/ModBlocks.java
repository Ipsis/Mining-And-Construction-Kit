package ipsis.mackit.block;

import ipsis.mackit.core.util.LogHelper;
import ipsis.mackit.fluid.ModFluids;
import ipsis.mackit.item.ItemBlockFixedEarth;
import ipsis.mackit.lib.BlockIds;
import ipsis.mackit.lib.Strings;
import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

	/* Mod block instantiations */
	public static Block fixedEarth;
	public static Block dyeTransposer;
	public static Block waterFiller;
	public static Block waterFillerMachine;
	public static Block redDye;
	public static Block yellowDye;
	public static Block greenDye;
	public static Block blueDye;
	public static Block whiteDye;
	public static Block blackDye;
	public static Block brownDye;
	
	
	public static void init() {
		
		LogHelper.debug("Initialising blocks");
		fixedEarth = new BlockFixedEarth(BlockIds.FIXED_EARTH);
		dyeTransposer = new BlockDyeTransposer(BlockIds.DYE_TRANSPOSER);
		waterFiller = new BlockWaterFiller(BlockIds.WATER_FILLER);
		waterFillerMachine = new BlockWaterFillerMachine(BlockIds.WATER_FILLER_MACHINE);
		
		GameRegistry.registerBlock(fixedEarth, ItemBlockFixedEarth.class ,Strings.BLOCK_FIXED_EARTH);
		GameRegistry.registerBlock(dyeTransposer, Strings.BLOCK_DYE_TRANSPOSER);
		GameRegistry.registerBlock(waterFiller, Strings.BLOCK_WATER_FILLER);
		GameRegistry.registerBlock(waterFillerMachine, Strings.BLOCK_WATER_FILLER_MACHINE);
		
		/**
		 * Fluids blocks and containers
		 */
		
		redDye = new BlockLiquidDye(BlockIds.LIQUID_DYE_RED, ModFluids.fluidRedDye, Strings.BLOCK_LIQUID_RED_DYE, Strings.LIQUID_RED_DYE);
		yellowDye = new BlockLiquidDye(BlockIds.LIQUID_DYE_YELLOW, ModFluids.fluidYellowDye, Strings.BLOCK_LIQUID_YELLOW_DYE, Strings.LIQUID_YELLOW_DYE);
		greenDye = new BlockLiquidDye(BlockIds.LIQUID_DYE_GREEN, ModFluids.fluidGreenDye, Strings.BLOCK_LIQUID_GREEN_DYE, Strings.LIQUID_GREEN_DYE);		
		blueDye = new BlockLiquidDye(BlockIds.LIQUID_DYE_BLUE, ModFluids.fluidBlueDye, Strings.BLOCK_LIQUID_BLUE_DYE, Strings.LIQUID_BLUE_DYE);
		whiteDye = new BlockLiquidDye(BlockIds.LIQUID_DYE_WHITE, ModFluids.fluidWhiteDye, Strings.BLOCK_LIQUID_WHITE_DYE, Strings.LIQUID_WHITE_DYE);
		blackDye = new BlockLiquidDye(BlockIds.LIQUID_DYE_BLACK, ModFluids.fluidBlackDye, Strings.BLOCK_LIQUID_BLACK_DYE, Strings.LIQUID_BLACK_DYE);
		brownDye = new BlockLiquidDye(BlockIds.LIQUID_DYE_BROWN, ModFluids.fluidBrownDye, Strings.BLOCK_LIQUID_BROWN_DYE, Strings.LIQUID_BROWN_DYE);
		
		GameRegistry.registerBlock(redDye, Strings.BLOCK_LIQUID_RED_DYE);
		GameRegistry.registerBlock(yellowDye, Strings.BLOCK_LIQUID_YELLOW_DYE);		
		GameRegistry.registerBlock(greenDye, Strings.BLOCK_LIQUID_GREEN_DYE);
		GameRegistry.registerBlock(blueDye, Strings.BLOCK_LIQUID_BLUE_DYE);
		GameRegistry.registerBlock(whiteDye, Strings.BLOCK_LIQUID_WHITE_DYE);
		GameRegistry.registerBlock(blackDye, Strings.BLOCK_LIQUID_BLACK_DYE);
		GameRegistry.registerBlock(brownDye, Strings.BLOCK_LIQUID_BROWN_DYE);
		
	}
}
