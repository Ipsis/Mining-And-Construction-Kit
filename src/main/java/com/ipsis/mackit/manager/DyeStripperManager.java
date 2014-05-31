package com.ipsis.mackit.manager;

import java.util.HashMap;

import net.minecraft.item.ItemStack;

import com.ipsis.cofhlib.util.inventory.ComparableItemStack;

public class DyeStripperManager {

	private HashMap<ComparableItemStack, ItemStack> recipes;
	
	public DyeStripperManager() {
		
		recipes = new HashMap<ComparableItemStack, ItemStack>();
	}
	
	public void addRecipe(ItemStack input, ItemStack origin) {
		
		recipes.put(new ComparableItemStack(input), origin.copy());
	}
	
	public ItemStack getOrigin(ItemStack input) {
		
		return recipes.get(new ComparableItemStack(input));
	}
}
