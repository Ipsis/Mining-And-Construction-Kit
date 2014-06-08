package com.ipsis.mackit.util;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.reference.Reference;

public class ConfigHelper {
	
	/* Stamper recipes */
	public static boolean disableStamperLapis;
	public static boolean disableStamperBonemeal;
	public static boolean disableStamperInkSac;
	public static boolean disableStamperCocoaBeans;
	
	private static final String SECT_STAMPER = "Stamper";
	
	private static Configuration configuration;	
	
	public static void init(File configFile) {
		
		configuration = new Configuration(configFile);

		try {
			
			configuration.load();

			configuration.addCustomCategoryComment(SECT_STAMPER, "Toggle stamper abilities");
			disableStamperLapis = configuration.get(SECT_STAMPER, "disableLapis", false).getBoolean(false);
			disableStamperBonemeal = configuration.get(SECT_STAMPER, "disableBonemeal", false).getBoolean(false);
			disableStamperInkSac = configuration.get(SECT_STAMPER, "disableStamperInkSac", false).getBoolean(false);
			disableStamperCocoaBeans = configuration.get(SECT_STAMPER, "disableStamperCocoaBeans", false).getBoolean(false);
		}
		catch (Exception e) {
			
			LogHelper.error(Reference.MOD_NAME + " has had a problem loading its general configuration");
		}
		finally {

			configuration.save();
		}    
	}
}
