package ipsis.mackit.block;

import ipsis.mackit.core.util.LogHelper;
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
	
	public static void init() {
		
		LogHelper.debug("Initialising blocks");
		fixedEarth = new BlockFixedEarth(BlockIds.FIXED_EARTH);
		dyeTransposer = new BlockDyeTransposer(BlockIds.DYE_TRANSPOSER);
		waterFiller = new BlockWaterFiller(BlockIds.WATER_FILLER);
		
		
		GameRegistry.registerBlock(fixedEarth, ItemBlockFixedEarth.class ,Strings.BLOCK_FIXED_EARTH);
		GameRegistry.registerBlock(dyeTransposer, Strings.BLOCK_DYE_TRANSPOSER);
		GameRegistry.registerBlock(waterFiller, Strings.BLOCK_WATER_FILLER);
	}
}
