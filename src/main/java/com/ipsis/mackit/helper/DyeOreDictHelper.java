package com.ipsis.mackit.helper;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class DyeOreDictHelper {

	/*
	 * OreDictionary information
	 */
	private static HashMap<Integer, Object> dyeMap = new HashMap<Integer, Object>();
	private static final String[] dyeOreNames = { 
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


	public static void loadDyes() {

		for (String s : dyeOreNames) {
			
			int id = OreDictionary.getOreID(s);
			if (id != -1)
				dyeMap.put(id,  null);
		}
	}	
	
	/* Is the itemstack a dye */
	public boolean isDye(ItemStack input) {
				
		boolean dye = false;
		
		if (input != null) {
			
			int[] ids = OreDictionary.getOreIDs(input);
			if (ids.length != 0) {
				for (int id : ids) {
					if (dyeMap.containsKey(id)) {
						dye = true;
						break;
					}
				}
			}
		}
		
		return dye;
	}
}
