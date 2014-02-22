package com.ipsis.mackit.util;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class SqueezerManager {

	private ArrayList<SqueezerRecipe> recipes = new ArrayList<SqueezerRecipe>();
	
	public class SqueezerRecipe {
		
		private ItemStack input;
		private FluidStack[] outputs;
	}
}
