package com.ipsis.mackit.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.item.ItemStack;

import com.ipsis.mackit.block.machinesm.IMachineRecipe;
import com.ipsis.mackit.helper.LogHelper;

public class PainterManager {
	
	private static HashMap<String, PainterRecipe> recipeMap = new HashMap<String, PainterRecipe>();
	
	private static String createKey(ItemStack input, ItemStack dye) {
		
		if (input == null || dye == null)
			return "";
		
		return input.getUnlocalizedName() + ":" + dye.getUnlocalizedName();
	}
	
	public static void addRecipe(ItemStack input, ItemStack dye, ItemStack output, int pureAmount) {
		
		String s = createKey(input, dye);
		
		if (s != "")
			recipeMap.put(s, new PainterRecipe(input, dye, output, pureAmount));
	}
	
	public static boolean canPaint(ItemStack input, ItemStack dye) {
		
		String s = createKey(input, dye);
		if (s == "")
			return false;
		
		return recipeMap.containsKey(s);
	}
	
	public static PainterRecipe getRecipe(ItemStack input, ItemStack dye) {
		
		if (input == null || dye == null)
			return null;
		
		return recipeMap.get(createKey(input, dye)); 
	}
	
	public static void debugDumpMap() {
		
		Iterator iter = recipeMap.entrySet().iterator();
		while (iter.hasNext()) {
			
			Map.Entry pairs = (Map.Entry)iter.next();	
			LogHelper.info("[PainterManager2] recipeMap: " + pairs.getKey() + " -> " + pairs.getValue());		
		}		
	}
	
	public static class PainterRecipe implements IMachineRecipe {
		
		public ItemStack dye;
		public ItemStack input;
		public ItemStack output;
		public int pureAmount;
		private static final int RECIPE_ENERGY = 100;
		
		public PainterRecipe(ItemStack dye, ItemStack input, ItemStack output, int pureAmount) {
			
			this.dye = dye;
			this.input = input;
			this.output = output;
			this.pureAmount = pureAmount;
		}
		
		@Override
		public int getEnergy() {

			return RECIPE_ENERGY;
		}
		
		public ItemStack getOutput() {
			
			return output;
		}
		
		@Override
		public String toString() {

			return input + " + " + dye + " + " + pureAmount + " -> " + output;
		}
	}

}
