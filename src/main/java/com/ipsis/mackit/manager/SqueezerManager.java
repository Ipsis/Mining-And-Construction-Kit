package com.ipsis.mackit.manager;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.ipsis.cofhlib.util.inventory.ComparableItemStack;
import com.ipsis.mackit.block.machinesm.IMachineRecipe;
import com.ipsis.mackit.helper.DyeHelper;
import com.ipsis.mackit.helper.LogHelper;

public class SqueezerManager {
	
	private HashMap<ComparableItemStack, SqueezerRecipe> recipes;

	public SqueezerManager() {

		recipes = new HashMap<ComparableItemStack, SqueezerRecipe>();
	}
	
	public void addRecipes() {
		
		ComparableItemStack[] sources = DyeHelper.getDyeSources();
		for (ComparableItemStack s : sources) {
			
			ItemStack itemStack = s.toItemStack();			
			ItemStack d = DyeHelper.getDyeFromSource(itemStack);
			if (d != null) {
				DyeHelper.DyeRecipe r = DyeHelper.getDyeRecipe(d);
				if (r != null)
					recipes.put(s.clone(), new SqueezerRecipe(itemStack.copy(), r.copy()));
			}
		}
	}

	public SqueezerRecipe getRecipe(ItemStack source) {

		if (source == null)
			return null;
		
		return recipes.get(new ComparableItemStack(source));
	}
	
	public boolean isSqueezable(ItemStack input) {
		
		if (input == null)
			return false;
		
		return recipes.get(input) != null ? true : false;
		
	}
	
	public static class SqueezerRecipe implements IMachineRecipe {
		
		private ItemStack source;
		private DyeHelper.DyeRecipe recipe;
		private static final int RECIPE_ENERGY = 1000;
		
		public SqueezerRecipe(ItemStack source, DyeHelper.DyeRecipe recipe) {
			
			this.source = source;
			this.recipe = recipe;
		}
		
		@Override
		public int getEnergy() {

			return RECIPE_ENERGY;
		}
		
		public FluidStack getRed() {
			
			return recipe.getRed();
		}
		
		public FluidStack getYellow() {
			
			return recipe.getYellow();
		}
		
		public FluidStack getBlue() {
			
			return recipe.getBlue();
		}
		
		public FluidStack getWhite() {
			
			return recipe.getWhite();
		}
		
		@Override
		public String toString() {

			return source + " -> " + "red " + recipe.getRed() + " yellow " + recipe.getYellow() + " blue " + recipe.getBlue() + " white " + recipe.getWhite();
		}
	}
}
