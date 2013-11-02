package ipsis.mackit.configuration;

import ipsis.mackit.lib.BlockIds;
import ipsis.mackit.lib.ItemIds;
import ipsis.mackit.lib.Strings;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class ConfigurationHandler {

	public static Configuration configuration;
	
	public static void init(File configFile) {
		
		configuration = new Configuration(configFile);
		
		try {
			configuration.load();
			
			/* Block configuration */
			BlockIds.DYE_MACHINE = configuration.getBlock(Strings.BLOCK_DYE_MACHINE, BlockIds.DYE_MACHINE_DEFAULT).getInt(BlockIds.DYE_MACHINE_DEFAULT);
			BlockIds.FIXED_EARTH = configuration.getBlock(Strings.BLOCK_FIXED_EARTH, BlockIds.FIXED_EARTH_DEFAULT).getInt(BlockIds.FIXED_EARTH_DEFAULT);
			BlockIds.DYE_TRANSPOSER = configuration.getBlock(Strings.BLOCK_DYE_TRANSPOSER,  BlockIds.DYE_TRANSPOSER_DEFAULT).getInt(BlockIds.DYE_TRANSPOSER_DEFAULT);
			BlockIds.WATER_FILLER = configuration.getBlock(Strings.BLOCK_WATER_FILLER, BlockIds.WATER_FILLER_DEFAULT).getInt(BlockIds.WATER_FILLER_DEFAULT);
			BlockIds.WATER_FILLER_MACHINE = configuration.getBlock(Strings.BLOCK_WATER_FILLER_MACHINE, BlockIds.WATER_FILLER_MACHINE_DEFAULT).getInt(BlockIds.WATER_FILLER_MACHINE_DEFAULT);
			
			BlockIds.LIQUID_DYE_RED = configuration.getBlock(Strings.BLOCK_LIQUID_RED_DYE, BlockIds.LIQUID_DYE_RED_DEFAULT).getInt(BlockIds.LIQUID_DYE_RED_DEFAULT);
			BlockIds.LIQUID_DYE_YELLOW = configuration.getBlock(Strings.BLOCK_LIQUID_YELLOW_DYE, BlockIds.LIQUID_DYE_YELLOW_DEFAULT).getInt(BlockIds.LIQUID_DYE_YELLOW_DEFAULT);
			BlockIds.LIQUID_DYE_GREEN = configuration.getBlock(Strings.BLOCK_LIQUID_GREEN_DYE, BlockIds.LIQUID_DYE_GREEN_DEFAULT).getInt(BlockIds.LIQUID_DYE_GREEN_DEFAULT);
			BlockIds.LIQUID_DYE_BLUE = configuration.getBlock(Strings.BLOCK_LIQUID_BLUE_DYE, BlockIds.LIQUID_DYE_BLUE_DEFAULT).getInt(BlockIds.LIQUID_DYE_BLUE_DEFAULT);
			BlockIds.LIQUID_DYE_WHITE = configuration.getBlock(Strings.BLOCK_LIQUID_WHITE_DYE, BlockIds.LIQUID_DYE_WHITE_DEFAULT).getInt(BlockIds.LIQUID_DYE_WHITE_DEFAULT);
			BlockIds.LIQUID_DYE_BLACK = configuration.getBlock(Strings.BLOCK_LIQUID_BLACK_DYE, BlockIds.LIQUID_DYE_BLACK_DEFAULT).getInt(BlockIds.LIQUID_DYE_BLACK_DEFAULT);
			BlockIds.LIQUID_DYE_BROWN = configuration.getBlock(Strings.BLOCK_LIQUID_BROWN_DYE, BlockIds.LIQUID_DYE_BROWN_DEFAULT).getInt(BlockIds.LIQUID_DYE_BROWN_DEFAULT);
			
			/* Item configuration */
			ItemIds.DIFFUSER_HEAD = configuration.getItem(Strings.DIFFUSER_HEAD_NAME, ItemIds.DIFFUSER_HEAD_DEFAULT).getInt(ItemIds.DIFFUSER_HEAD_DEFAULT);
			ItemIds.FIXER_FOAM_GUN = configuration.getItem(Strings.FIXER_FOAM_GUN_NAME, ItemIds.FIXER_FOAM_GUN_DEFAULT).getInt(ItemIds.FIXER_FOAM_GUN_DEFAULT);
			ItemIds.FIXER_FOAM_PELLET = configuration.getItem(Strings.FIXER_FOAM_PELLET_NAME, ItemIds.FIXER_FOAM_PELLET_DEFAULT).getInt(ItemIds.FIXER_FOAM_PELLET_DEFAULT);
			ItemIds.MACKIT_CASING = configuration.getItem(Strings.MACKIT_CASING_NAME, ItemIds.MACKIT_CASING_DEFAULT).getInt(ItemIds.MACKIT_CASING_DEFAULT);
			ItemIds.MACKIT_PORTABLE_CASING = configuration.getItem(Strings.MACKIT_PORTABLE_CASING_NAME, ItemIds.MACKIT_PORTABLE_CASING_DEFAULT).getInt(ItemIds.MACKIT_PORTABLE_CASING_DEFAULT);
		}
		catch (Exception e) {
			// Log an error
		}
		finally {
			configuration.save();
		}
	}
}