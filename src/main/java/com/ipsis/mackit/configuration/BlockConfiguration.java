package com.ipsis.mackit.configuration;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;

import com.ipsis.mackit.lib.BlockIds;
import com.ipsis.mackit.lib.Reference;
import com.ipsis.mackit.lib.Strings;

import cpw.mods.fml.common.FMLLog;

public class BlockConfiguration {

	protected static void init(File configFile) {
		
		Configuration blockConfig = new Configuration(configFile);
		
		try {
			blockConfig.load();
			
			BlockIds.ENCHANTER = blockConfig.getBlock(Strings.ENCHANTER_NAME,  BlockIds.ENCHANTER_DEFAULT).getInt(BlockIds.ENCHANTER_DEFAULT);
			BlockIds.FIXED_EARTH = blockConfig.getBlock(Strings.FIXED_EARTH_NAME, BlockIds.FIXED_EARTH_DEFAULT).getInt(BlockIds.FIXED_EARTH_DEFAULT);
			BlockIds.BEAVER_BLOCK = blockConfig.getBlock(Strings.BEAVER_BLOCK_NAME, BlockIds.BEAVER_BLOCK_DEFAULT).getInt(BlockIds.BEAVER_BLOCK_DEFAULT);
			
			/* Machines */
			BlockIds.MACHINE = blockConfig.getBlock(Strings.MACHINE_BLOCK_NAME, BlockIds.MACHINE_DEFAULT).getInt(BlockIds.MACHINE_DEFAULT);
			
			BlockIds.FLUID_RED_DYE = blockConfig.getBlock(Strings.RED_DYE_BLOCK_NAME, BlockIds.FLUID_RED_DYE_DEFAULT).getInt(BlockIds.FLUID_RED_DYE_DEFAULT);
			BlockIds.FLUID_YELLOW_DYE = blockConfig.getBlock(Strings.YELLOW_DYE_BLOCK_NAME, BlockIds.FLUID_YELLOW_DYE_DEFAULT).getInt(BlockIds.FLUID_YELLOW_DYE_DEFAULT);
			BlockIds.FLUID_BLUE_DYE = blockConfig.getBlock(Strings.BLUE_DYE_BLOCK_NAME, BlockIds.FLUID_BLUE_DYE_DEFAULT).getInt(BlockIds.FLUID_BLUE_DYE_DEFAULT);
			BlockIds.FLUID_WHITE_DYE = blockConfig.getBlock(Strings.WHITE_DYE_BLOCK_NAME, BlockIds.FLUID_WHITE_DYE_DEFAULT).getInt(BlockIds.FLUID_WHITE_DYE_DEFAULT);
			BlockIds.FLUID_PURE_DYE = blockConfig.getBlock(Strings.PURE_DYE_BLOCK_NAME, BlockIds.FLUID_PURE_DYE_DEFAULT).getInt(BlockIds.FLUID_PURE_DYE_DEFAULT);
		}
		catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " had a problem loading its block configuration");
		}
		finally {
			blockConfig.save();
		}
	}
}
