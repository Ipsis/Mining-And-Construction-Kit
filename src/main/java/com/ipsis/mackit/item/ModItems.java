package com.ipsis.mackit.item;

import com.ipsis.mackit.lib.ItemIds;
import com.ipsis.mackit.lib.Strings;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
	
	public static ItemMK foamFixerGun;
	public static ItemMK foamFixerPellet;
	public static ItemMK dyeBlank;

	public static void init() {
	
		/* Initialise items */
		foamFixerGun = new ItemFixerFoamGun(ItemIds.FIXER_FOAM_GUN);
		foamFixerPellet = new ItemFixerFoamPellet(ItemIds.FIXER_FOAM_PELLET);
		dyeBlank = new ItemDyeBlank(ItemIds.DYE_BLANK);
		
		/* Add to GameRegistry */
		GameRegistry.registerItem(foamFixerGun, "item." + Strings.FIXER_FOAM_GUN_NAME);
		GameRegistry.registerItem(foamFixerPellet, "item." + Strings.FIXER_FOAM_PELLET_NAME);
		GameRegistry.registerItem(dyeBlank, "item." + Strings.DYE_BLANK_NAME);
	}
}
