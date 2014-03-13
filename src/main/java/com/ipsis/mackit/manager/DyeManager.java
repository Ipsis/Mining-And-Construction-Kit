package com.ipsis.mackit.manager;

import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.util.ItemHelper;

public class DyeManager {
		
		private HashMap<Integer, DyeRecipe> recipes;
		
		public DyeManager() {
			
			recipes = new HashMap<Integer, DyeRecipe>();			
			addRecipes();
		}
		
		private void addRecipes() {
			
			addRecipe(new ItemStack(Item.dyePowder, 1, 0), 33, 33, 33, 0, DyeRecipe.BLUE);	/* black */
			addRecipe(new ItemStack(Item.dyePowder, 1, 1), 100, 0, 0, 0, DyeRecipe.RED); /* red */
			addRecipe(new ItemStack(Item.dyePowder, 1, 2), 0, 50, 50, 0, DyeRecipe.YELLOW); /* green */
			addRecipe(new ItemStack(Item.dyePowder, 1, 3), 33, 33, 33, 0, DyeRecipe.RED);/* brown */
			addRecipe(new ItemStack(Item.dyePowder, 1, 4), 0, 0, 100, 0, DyeRecipe.BLUE); /* blue */
			addRecipe(new ItemStack(Item.dyePowder, 1, 5), 50, 0, 50, 0, DyeRecipe.RED); /* purple */
			/* cyan */
			addRecipe(new ItemStack(Item.dyePowder, 1, 7), 20, 20, 20, 40, DyeRecipe.WHITE); /* light gray */
			/* gray */
			/* pink */
			addRecipe(new ItemStack(Item.dyePowder, 1, 10), 0, 33, 33, 33, DyeRecipe.YELLOW);/* lime */
			addRecipe(new ItemStack(Item.dyePowder, 1, 11), 0, 100, 0, 0, DyeRecipe.YELLOW); /* yellow */
			addRecipe(new ItemStack(Item.dyePowder, 1, 12), 0, 0, 50, 50, DyeRecipe.BLUE); /* light blue */
			/* magenta */
			addRecipe(new ItemStack(Item.dyePowder, 1, 14), 50, 50, 0, 0, DyeRecipe.YELLOW); /* orange */
			addRecipe(new ItemStack(Item.dyePowder, 1, 15), 0, 0, 0, 100, DyeRecipe.WHITE); /* white */
			
		}
		
		private void addRecipe(ItemStack source, int red, int yellow, int blue, int white, int defaultOutput) {
			
			recipes.put(ItemHelper.getHashCode(source), new DyeRecipe(source, red, yellow, blue, white, defaultOutput));
		}
		
		public DyeRecipe getRecipe(ItemStack dye) {
			
			LogHelper.severe("getRecipe: dye=" + dye);
			int id = ItemHelper.getHashCode(dye);
			return recipes.get(id);
		}
	
}
