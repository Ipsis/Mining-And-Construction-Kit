package com.ipsis.mackit.configuration;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;

import com.ipsis.mackit.lib.ItemIds;
import com.ipsis.mackit.lib.Reference;
import com.ipsis.mackit.lib.Strings;

import cpw.mods.fml.common.FMLLog;

public class ItemConfiguration {

	protected static void init(File configFile)
	{
		Configuration itemConfig = new Configuration(configFile);
		
		try
		{
			itemConfig.load();
			
			ItemIds.FIXER_FOAM_GUN = itemConfig.getItem(Strings.FIXER_FOAM_GUN_NAME, ItemIds.FIXER_FOAM_GUN_DEFAULT).getInt(ItemIds.FIXER_FOAM_GUN_DEFAULT);		
		}
		catch (Exception e)
		{
			FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " had a problem loading its item configuration");
		}
		finally
		{
			itemConfig.save();
		}
	}
}
