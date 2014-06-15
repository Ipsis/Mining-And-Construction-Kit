package com.ipsis.mackit.helper;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class DyeOreDictHelper {
	
	public static boolean isDye(ItemStack input) {
		
		if (input != null) {
	
			int id = OreDictionary.getOreID("dye");
			int[] ids = OreDictionary.getOreIDs(input);
			
			if (ids.length != 0) {
				for (int curr : ids) {
					if (curr == id)
						return true;
				}
			}
		}
		
		return false;
	}
}
