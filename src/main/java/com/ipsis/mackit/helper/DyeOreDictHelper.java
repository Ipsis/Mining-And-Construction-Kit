package com.ipsis.mackit.helper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class DyeOreDictHelper {

	/*
	 * OreDictionary information
	 */
	private int[] dyeOreIds;
	private static String[] dyeOreNames = { "dyeBlack", "dyeRed", "dyeGreen",
			"dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray",
			"dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue",
			"dyeMagenta", "dyeOrange", "dyeWhite" };

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
		
		if (input == null)
			return false;

		int id = OreDictionary.getOreID(input);
		if (id == -1)
			return false;

		for (int i : dyeOreIds)
			if (i == id)
				return true;

		return false;
	}
}
