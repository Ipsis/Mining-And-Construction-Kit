package com.ipsis.mackit.plugins.nei;

import com.ipsis.mackit.helper.LogHelper;

import codechicken.nei.api.IConfigureNEI;

/**
 * An nei configuration entry point should implement this class and have name "NEI<someting>Config"
 *
 */
public class NEIMacKitConfigure implements IConfigureNEI {

	@Override
	public String getName() {
		
		return "Mining & Construction Kit";
	}

	@Override
	public String getVersion() {

		return "0.0.1";
	}

	@Override
	public void loadConfig() {
		
	}

}
