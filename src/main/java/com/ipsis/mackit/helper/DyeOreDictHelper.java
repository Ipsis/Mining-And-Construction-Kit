package com.ipsis.mackit.helper;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class DyeOreDictHelper {

	/*
	 * OreDictionary information
	 */
	private int[] dyeOreIds;
	private static String[] dyeOreNames = { 
        "dyeBlack",
        "dyeRed",
        "dyeGreen",
        "dyeBrown",
        "dyeBlue",
        "dyePurple",
        "dyeCyan",
        "dyeLightGray",
        "dyeGray",
        "dyePink",
        "dyeLime",
        "dyeYellow",
        "dyeLightBlue",
        "dyeMagenta",
        "dyeOrange",
        "dyeWhite",
        "dustLapis"
        };

	public DyeOreDictHelper() {
		
	}

	public void loadOres() {

		int c = 0;
		dyeOreIds = new int[dyeOreNames.length];

		for (String s : dyeOreNames) {
			int id = OreDictionary.getOreID(s);
			if (id != -1) {
				dyeOreIds[c] = id;
				c++;
			}
		}
	}	
	
	/* Is the itemstack a dye */
	public boolean isDye(ItemStack input) {
			
		boolean dye = false;
		
		boolean t = false;
		if (input.getItem() == Items.dye)
			t = true;
		
		if (input != null) {
			
			int id = OreDictionary.getOreID(input);
			if (id != -1) {	
				for (int i = 0; i < dyeOreIds.length && dye == false; i++) {

					if (dyeOreIds[i] == id)
						dye = true;
				}
			}
		}
		
		if (t != dye)
			LogHelper.info("isDye: " + input + " not dye but is Items.dye");
		
		return dye;
	}
}
