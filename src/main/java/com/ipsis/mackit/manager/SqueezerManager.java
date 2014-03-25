package com.ipsis.mackit.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;

import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.util.ItemHelper;

public class SqueezerManager {

	private HashMap<Integer, SqueezerRecipe> recipes;
	
	public SqueezerManager() {
		
		recipes = new HashMap<Integer, SqueezerRecipe>();
		
		addDyeOreIds();
		addRecipes();
	}
	
	private void addRecipe(ItemStack source, ItemStack dye) {
		
		recipes.put(ItemHelper.getHashCode(source), new SqueezerRecipe(source, dye));
	}
	
	public SqueezerRecipe getRecipe(ItemStack source) {
		
		if (isDye(source)) {
			return new SqueezerRecipe(source, source);
		}
		
		int id = ItemHelper.getHashCode(source);
		return recipes.get(id);
	}
	
	
	/*
	 * Add the item->dye recipes
	 */
	private void addRecipes() {
		
		/* Shapeless recipes */
		List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
		for (IRecipe irecipe : allrecipes) {
			if (irecipe instanceof ShapelessRecipes) {
				
				/* 
				 * Only add shapeless recipes where
				 * single item creates a dye (can be 1 or more of the same dye)
				 */
				ShapelessRecipes r = (ShapelessRecipes)irecipe;
				if (r.getRecipeSize() == 1) {
					ItemStack out = irecipe.getRecipeOutput();
					ItemStack in = (ItemStack)(r.recipeItems.get(0));
					
					if (isDye(out)) {
					
						LogHelper.severe("Add vanilla In: " + in + " Out: " + out);
						addRecipe(in, out);
					}					
				}
			}
		}
		
		/* dont like this but dont know of a better way! */
		Map allsmelting = FurnaceRecipes.smelting().getSmeltingList();
		Iterator i = allsmelting.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry pairs = (Map.Entry)i.next();
			
			Integer itemId = (Integer)pairs.getKey();
			if (itemId < Item.itemsList.length) {
			
				ItemStack in = new ItemStack(Item.itemsList[itemId]);
				ItemStack out = (ItemStack)pairs.getValue();
				if (isDye(out)) {
					
					LogHelper.severe("Add smelting In: " + in + " Out: " + out);
					addRecipe(in, out);
				}		
			}
		}		
	}
	
	
	/* Is the itemstack a dye */
	private boolean isDye(ItemStack input) {
		
		int id = OreDictionary.getOreID(input);
		if (id == -1)
			return false;

		for (int i : dyeOreIds)
			if (i == id)
				return true;
		
		return false;
	}
	
	/*
	 * Does this item produce a dye
	 * OR
	 * is it a dye item
	 */
	public boolean isSqueezable(ItemStack input) {
		
		if (isDye(input))
			return true;
		
		if (getRecipe(input) != null)
			return true;
		
		return false;
	}
	
	/*
	 * OreDictionary information
	 */
	private int[] dyeOreIds;
	private String[] dyeOreNames = { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray",
	        "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };
	
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
