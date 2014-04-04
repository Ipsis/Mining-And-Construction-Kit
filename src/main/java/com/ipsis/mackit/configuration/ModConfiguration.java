package com.ipsis.mackit.configuration;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;

import com.ipsis.mackit.lib.Reference;

import cpw.mods.fml.common.FMLLog;

public class ModConfiguration {

	public static final String CATEGORY_DYES = "dyes";
	
	protected static void init(File configFile) {
		
		Configuration modConfig = new Configuration(configFile);
		
		try {
			modConfig.load();
			
			/* Dye Configuration */
			ConfigurationSettings.ENABLE_STAMPER_BLACK_DYE = modConfig.get(CATEGORY_DYES, ConfigurationSettings.ENABLE_STAMPER_BLACK_DYE_CONFIGNAME, 
																ConfigurationSettings.ENABLE_STAMPER_BLACK_DYE_DEFAULT).getBoolean(ConfigurationSettings.ENABLE_STAMPER_BLACK_DYE_DEFAULT);
			ConfigurationSettings.ENABLE_STAMPER_WHITE_DYE = modConfig.get(CATEGORY_DYES, ConfigurationSettings.ENABLE_STAMPER_WHITE_DYE_CONFIGNAME, 
																ConfigurationSettings.ENABLE_STAMPER_WHITE_DYE_DEFAULT).getBoolean(ConfigurationSettings.ENABLE_STAMPER_WHITE_DYE_DEFAULT);
			ConfigurationSettings.ENABLE_STAMPER_BLUE_DYE = modConfig.get(CATEGORY_DYES, ConfigurationSettings.ENABLE_STAMPER_BLUE_DYE_CONFIGNAME, 
																ConfigurationSettings.ENABLE_STAMPER_BLUE_DYE_DEFAULT).getBoolean(ConfigurationSettings.ENABLE_STAMPER_BLUE_DYE_DEFAULT);
		}
		catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " had a problem loading its mod configuration");
		}
		finally {
			modConfig.save();
		}
	}
}
