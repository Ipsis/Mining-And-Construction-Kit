package com.ipsis.mackit.manager;

import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.ipsis.mackit.util.ItemHelper;

public class DyeManager {
		
		private HashMap<Integer, DyeRecipe> recipes;
		
		public DyeManager() {
			
			recipes = new HashMap<Integer, DyeRecipe>();			
			addRecipes();
		}
		
		private void addRecipes() {
			
			addRecipe(new ItemStack(Item.dyePowder, 1, 0), 33, 33, 33, 0);	/* black */
			addRecipe(new ItemStack(Item.dyePowder, 1, 1), 100, 0, 0, 0); /* red */
			addRecipe(new ItemStack(Item.dyePowder, 1, 2), 0, 50, 50, 0); /* green */
			addRecipe(new ItemStack(Item.dyePowder, 1, 3), 33, 33, 33, 0);/* brown */
			addRecipe(new ItemStack(Item.dyePowder, 1, 4), 0, 0, 100, 0); /* blue */
			addRecipe(new ItemStack(Item.dyePowder, 1, 4), 50, 0, 50, 0); /* purple */
			/* cyan */
			addRecipe(new ItemStack(Item.dyePowder, 1, 4), 20, 20, 20, 40); /* light gray */
			/* gray */
			/* pink */
			addRecipe(new ItemStack(Item.dyePowder, 1, 4), 0, 33, 33, 33);/* lime */
			addRecipe(new ItemStack(Item.dyePowder, 1, 0), 0, 100, 0, 0); /* yellow */
			addRecipe(new ItemStack(Item.dyePowder, 1, 4), 0, 0, 50, 50); /* light blue */
			/* magenta */
			addRecipe(new ItemStack(Item.dyePowder, 1, 4), 50, 50, 0, 0); /* orange */
			addRecipe(new ItemStack(Item.dyePowder, 1, 4), 0, 0, 0, 100); /* white */
			
		}
		
		private void addRecipe(ItemStack source, int red, int yellow, int blue, int white) {
			
			recipes.put(ItemHelper.getHashCode(source), new DyeRecipe(source, red, yellow, blue, white));
		}
		
		public DyeRecipe getRecipe(ItemStack dye) {
			
			int id = ItemHelper.getHashCode(dye);
			return recipes.get(id);
		}
	
}
