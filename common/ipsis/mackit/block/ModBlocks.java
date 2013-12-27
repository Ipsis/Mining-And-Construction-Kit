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
	public static Block beaverBlock;
	
	public static Block machineDyeExtractor;
	public static Block machineDyeMixer;
	public static Block machineDyeStamper;
	public static Block machineDyeApplicator;
	public static Block machineBBBuilder;
	
	public static Block redDye;
	public static Block yellowDye;
	public static Block blueDye;
	public static Block whiteDye;
	public static Block pureDye;
	
	
	public static void init() {
		
		LogHelper.debug("Initialising blocks");
		
		/* blocks */
		fixedEarth = new BlockFixedEarth(BlockIds.FIXED_EARTH);		
		beaverBlock = new BlockBeaverBlock(BlockIds.BEAVER_BLOCK);
		
		/* machine blocks */
		machineDyeExtractor = new BlockMachineExtractor(BlockIds.MACHINE_EXTRACTOR);
		machineDyeMixer = new BlockMachineMixer(BlockIds.MACHINE_MIXER);
		machineDyeStamper = new BlockMachineStamper(BlockIds.MACHINE_STAMPER);
		machineDyeApplicator = new BlockMachineApplicator(BlockIds.MACHINE_APPLICATOR);
		machineBBBuilder = new BlockMachineBBBuilder(BlockIds.MACHINE_BBBUILDER);
		
		/* blocks */
		GameRegistry.registerBlock(fixedEarth, ItemBlockFixedEarth.class ,Strings.BLOCK_FIXED_EARTH);
		GameRegistry.registerBlock(beaverBlock, Strings.BLOCK_BEAVER_BLOCK);
		
		/* machine blocks */
		GameRegistry.registerBlock(machineDyeExtractor, Strings.BLOCK_MACHINE_EXTRACTOR);
		GameRegistry.registerBlock(machineDyeMixer, Strings.BLOCK_MACHINE_MIXER);
		GameRegistry.registerBlock(machineDyeStamper, Strings.BLOCK_MACHINE_STAMPER);
		GameRegistry.registerBlock(machineDyeApplicator, Strings.BLOCK_MACHINE_APPLICATOR);
		GameRegistry.registerBlock(machineBBBuilder, Strings.BLOCK_MACHINE_BBBUILDER);
		
		/**
		 * Fluids blocks and containers
		 */
		
		redDye = new BlockLiquidDye(BlockIds.LIQUID_DYE_RED, ModFluids.fluidRedDye, Strings.BLOCK_LIQUID_RED_DYE, Strings.LIQUID_RED_DYE);
		yellowDye = new BlockLiquidDye(BlockIds.LIQUID_DYE_YELLOW, ModFluids.fluidYellowDye, Strings.BLOCK_LIQUID_YELLOW_DYE, Strings.LIQUID_YELLOW_DYE);		
		blueDye = new BlockLiquidDye(BlockIds.LIQUID_DYE_BLUE, ModFluids.fluidBlueDye, Strings.BLOCK_LIQUID_BLUE_DYE, Strings.LIQUID_BLUE_DYE);
		whiteDye = new BlockLiquidDye(BlockIds.LIQUID_DYE_WHITE, ModFluids.fluidWhiteDye, Strings.BLOCK_LIQUID_WHITE_DYE, Strings.LIQUID_WHITE_DYE);
		pureDye = new BlockLiquidDye(BlockIds.LIQUID_DYE_PURE, ModFluids.fluidPureDye, Strings.BLOCK_LIQUID_PURE_DYE, Strings.LIQUID_PURE_DYE);

		
		GameRegistry.registerBlock(redDye, Strings.BLOCK_LIQUID_RED_DYE);
		GameRegistry.registerBlock(yellowDye, Strings.BLOCK_LIQUID_YELLOW_DYE);		
		GameRegistry.registerBlock(blueDye, Strings.BLOCK_LIQUID_BLUE_DYE);
		GameRegistry.registerBlock(whiteDye, Strings.BLOCK_LIQUID_WHITE_DYE);
		GameRegistry.registerBlock(pureDye, Strings.BLOCK_LIQUID_PURE_DYE);
		
	}
}
