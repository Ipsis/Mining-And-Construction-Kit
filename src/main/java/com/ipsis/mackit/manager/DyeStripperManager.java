package com.ipsis.mackit.manager;

import java.util.HashMap;

import net.minecraft.item.ItemStack;

public class DyeStripperManager {

	private HashMap<ItemStack, ItemStack> recipes;
	
	public DyeStripperManager() {
		
		recipes = new HashMap<ItemStack, ItemStack>();
	}
	
	public void addRecipe(ItemStack input, ItemStack origin) {
		
		recipes.put(input, origin);
	}
	
	public ItemStack getOrigin(ItemStack input) {
		
		return recipes.get(input);
	}
}
