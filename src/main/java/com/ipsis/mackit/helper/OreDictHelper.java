package com.ipsis.mackit.helper;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictHelper {
	
	private static HashMap<Integer, String> validOreIds = new HashMap<Integer, String>();
	
	public static void loadOres() {
		
		String[] names = OreDictionary.getOreNames();
		for (String name : names) {
			
			if (name.startsWith("ore"))
				validOreIds.put(OreDictionary.getOreID(name), name);
		}
	}

	public static boolean isOre(ItemStack itemstack) {
		
		int[] ids = OreDictionary.getOreIDs(itemstack);
		
		if (ids != null) {
			
			for (int id : ids) {				
				if (validOreIds.containsKey(id))
					return true;
			}
		}
		
		return false;
	}
}
