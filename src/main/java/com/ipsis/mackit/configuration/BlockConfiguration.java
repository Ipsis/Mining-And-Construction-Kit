package com.ipsis.mackit.configuration;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;

import com.ipsis.mackit.lib.BlockIds;
import com.ipsis.mackit.lib.Reference;
import com.ipsis.mackit.lib.Strings;

import cpw.mods.fml.common.FMLLog;

public class BlockConfiguration {

	protected static void init(File configFile)
	{
		Configuration blockConfig = new Configuration(configFile);
		
		try
		{
			blockConfig.load();
			
			BlockIds.ENCHANTER = blockConfig.getBlock(Strings.ENCHANTER_NAME,  BlockIds.ENCHANTER_DEFAULT).getInt(BlockIds.ENCHANTER_DEFAULT);
			BlockIds.FIXED_EARTH = blockConfig.getBlock(Strings.FIXED_EARTH_NAME, BlockIds.FIXED_EARTH_DEFAULT).getInt(BlockIds.FIXED_EARTH_DEFAULT);
			BlockIds.BEAVER_BLOCK = blockConfig.getBlock(Strings.BEAVER_BLOCK_NAME, BlockIds.BEAVER_BLOCK_DEFAULT).getInt(BlockIds.BEAVER_BLOCK_DEFAULT);
		}
		catch (Exception e)
		{
			FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " had a problem loading its block configuration");
		}
		finally
		{
			blockConfig.save();
		}
	}
}
