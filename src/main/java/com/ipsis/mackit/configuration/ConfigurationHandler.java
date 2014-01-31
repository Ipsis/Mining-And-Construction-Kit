package com.ipsis.mackit.configuration;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class ConfigurationHandler {

	public static Configuration configuration;
	
	public static void init(String configPath) {

		BlockConfiguration.init(new File(configPath + "block.properties"));
		ItemConfiguration.init(new File(configPath + "item.properties"));
	}
}
