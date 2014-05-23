package com.ipsis.mackit.manager;

import java.util.HashMap;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.ipsis.cofhlib.util.ItemHelper;
import com.ipsis.mackit.helper.LogHelper;

/*
 * OreDict Dye -> Liquid Dye Components
 */
public class DyeManager {

	private HashMap<Integer, DyeRecipe> recipes;

	public DyeManager() {

		recipes = new HashMap<Integer, DyeRecipe>();			
		addRecipes();
	}

	private void addRecipes() {

		addRecipe(new ItemStack(Items.dye, 1, 0), 33, 33, 33, 0);	/* black */
		addRecipe(new ItemStack(Items.dye, 1, 1), 100, 0, 0, 0); /* red */
		addRecipe(new ItemStack(Items.dye, 1, 2), 0, 50, 50, 0); /* green */
		addRecipe(new ItemStack(Items.dye, 1, 3), 33, 33, 33, 0);/* brown */
		addRecipe(new ItemStack(Items.dye, 1, 4), 0, 0, 100, 0); /* blue */
		addRecipe(new ItemStack(Items.dye, 1, 5), 50, 0, 50, 0); /* purple */

		addRecipe(new ItemStack(Items.dye, 1, 6), 0, 0, 0, 0); /* cyan */ /* TODO */
		addRecipe(new ItemStack(Items.dye, 1, 7), 20, 20, 20, 40); /* light gray */
		addRecipe(new ItemStack(Items.dye, 1, 8), 0, 0, 0, 0); /* gray */ /* TODO */
		addRecipe(new ItemStack(Items.dye, 1, 9), 50, 0, 0, 50); /* pink */ /* TODO */
		addRecipe(new ItemStack(Items.dye, 1, 10), 0, 33, 33, 33);/* lime */
		addRecipe(new ItemStack(Items.dye, 1, 11), 0, 100, 0, 0); /* yellow */
		addRecipe(new ItemStack(Items.dye, 1, 12), 0, 0, 50, 50); /* light blue */
		addRecipe(new ItemStack(Items.dye, 1, 13), 0, 0, 0, 0); /* magenta */ /* TODO */
		addRecipe(new ItemStack(Items.dye, 1, 14), 50, 50, 0, 0); /* orange */
		addRecipe(new ItemStack(Items.dye, 1, 15), 0, 0, 0, 100); /* white */

	}

	private void addRecipe(ItemStack source, int red, int yellow, int blue, int white) {

		recipes.put(ItemHelper.getHashCode(source), new DyeRecipe(source, red, yellow, blue, white));
	}

	public DyeRecipe getRecipe(ItemStack dye) {

		if (dye == null)
			return null;
		
		return recipes.get(ItemHelper.getHashCode(dye));
	}
}
