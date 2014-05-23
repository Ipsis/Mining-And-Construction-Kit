package com.ipsis.mackit.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;

import com.ipsis.cofhlib.util.ItemHelper;
import com.ipsis.mackit.block.machinesm.IMachineRecipe;
import com.ipsis.mackit.block.machinesm.IRecipeManager;
import com.ipsis.mackit.helper.LogHelper;

public class SqueezerManager {
	
	private HashMap<Integer, SqueezerRecipe> recipes;

	public SqueezerManager() {

		recipes = new HashMap<Integer, SqueezerRecipe>();

		addDyeOreIds();
		addRecipes();
	}

	private void addRecipe(ItemStack source, ItemStack dye) {

		/* Only add recipes where the dye is valid */
		if (MKManagers.dyeMgr.getRecipe(dye) != null)
			recipes.put(ItemHelper.getHashCode(source), new SqueezerRecipe(source, dye));
	}

	public SqueezerRecipe getRecipe(ItemStack source) {

		if (source == null)
			return null;
				
		int id = ItemHelper.getHashCode(source);
		return recipes.get(id);
	}

	/*
	 * Add the item->dye recipes
	 */
	private void addRecipes() {
		
		/* Dye->Dye */
		addRecipe(new ItemStack(Items.dye, 1, 0), new ItemStack(Items.dye, 1, 0));
		addRecipe(new ItemStack(Items.dye, 1, 1), new ItemStack(Items.dye, 1, 1));
		addRecipe(new ItemStack(Items.dye, 1, 2), new ItemStack(Items.dye, 1, 2));
		addRecipe(new ItemStack(Items.dye, 1, 3), new ItemStack(Items.dye, 1, 3));
		addRecipe(new ItemStack(Items.dye, 1, 4), new ItemStack(Items.dye, 1, 4));
		addRecipe(new ItemStack(Items.dye, 1, 5), new ItemStack(Items.dye, 1, 5));
		addRecipe(new ItemStack(Items.dye, 1, 6), new ItemStack(Items.dye, 1, 6));
		addRecipe(new ItemStack(Items.dye, 1, 7), new ItemStack(Items.dye, 1, 7));
		addRecipe(new ItemStack(Items.dye, 1, 8), new ItemStack(Items.dye, 1, 8));
		addRecipe(new ItemStack(Items.dye, 1, 9), new ItemStack(Items.dye, 1, 9));
		addRecipe(new ItemStack(Items.dye, 1, 10), new ItemStack(Items.dye, 1, 10));
		addRecipe(new ItemStack(Items.dye, 1, 11), new ItemStack(Items.dye, 1, 11));
		addRecipe(new ItemStack(Items.dye, 1, 12), new ItemStack(Items.dye, 1, 12));
		addRecipe(new ItemStack(Items.dye, 1, 13), new ItemStack(Items.dye, 1, 13));
		addRecipe(new ItemStack(Items.dye, 1, 14), new ItemStack(Items.dye, 1, 14));
		addRecipe(new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 15));
		

		/* Shapeless recipes */
		List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
		for (IRecipe irecipe : allrecipes) {
			if (irecipe instanceof ShapelessRecipes) {

				/*
				 * Only add shapeless recipes where single item creates a dye
				 * (can be 1 or more of the same dye)
				 */
				ShapelessRecipes r = (ShapelessRecipes) irecipe;
				if (r.getRecipeSize() == 1) {
					ItemStack out = irecipe.getRecipeOutput();
					ItemStack in = (ItemStack) (r.recipeItems.get(0));

					if (isDye(out)) {

						addRecipe(in, out);
					}
				}
			}
		}

		/* dont like this but dont know of a better way! */
		Map allsmelting = FurnaceRecipes.smelting().getSmeltingList();
		Iterator i = allsmelting.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry pairs = (Map.Entry) i.next();

			/**
			 * If the source item is actually a block eg. cactus
			 * then the damage value may not be correct.
			 * We therefore create a new stack using the item for the block.
			 * We are using the damage value as part of the hashmap, so this
			 * makes sure a lookup will work.
			 */
			
			ItemStack in = ((ItemStack)pairs.getKey());			
			ItemStack cleanItem = new ItemStack(in.getItem());
			ItemStack out = ((ItemStack)pairs.getValue()).copy();
			
			if (isDye(out)) {

				addRecipe(cleanItem, out);
			}
		}

		/* test painter recipe */
		/*
		allrecipes = CraftingManager.getInstance().getRecipeList();
		for (IRecipe irecipe : allrecipes) {

			if (irecipe instanceof ShapelessRecipes) {

				ShapelessRecipes r = (ShapelessRecipes) irecipe;

			} else if (irecipe instanceof ShapedRecipes) {

				ShapedRecipes r = (ShapedRecipes) irecipe;

				ItemStack out = irecipe.getRecipeOutput();
				for (ItemStack in : r.recipeItems) {
					if (isDye(in)) {

						LogHelper.error("Recipe uses dye input " + in + " = " + out);
					}
				}
			}
		} */

	}

	/* Is the itemstack a dye */
	private boolean isDye(ItemStack input) {
		
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

	/*
	 * OreDictionary information
	 */
	private int[] dyeOreIds;
	private String[] dyeOreNames = { "dyeBlack", "dyeRed", "dyeGreen",
			"dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray",
			"dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue",
			"dyeMagenta", "dyeOrange", "dyeWhite" };

	private void addDyeOreIds() {

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
}
