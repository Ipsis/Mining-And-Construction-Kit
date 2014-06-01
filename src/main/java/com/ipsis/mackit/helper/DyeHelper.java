package com.ipsis.mackit.helper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidStack;

import com.ipsis.cofhlib.util.inventory.ComparableItemStack;
import com.ipsis.mackit.fluid.MKFluids;
import com.ipsis.mackit.manager.MKManagers;

public class DyeHelper {

	public static final String DYE_RED = "Red";
	public static final String DYE_YELLOW = "Yellow";
	public static final String DYE_BLUE = "Blue";
	public static final String DYE_WHITE = "White";
	
	private static HashMap<ComparableItemStack, DyeRecipe> dyeRecipeMap = new HashMap<ComparableItemStack, DyeRecipe>();
	private static HashMap<ComparableItemStack, ItemStack> dyeSourceMap = new HashMap<ComparableItemStack, ItemStack>();	
	
	private static final int[][] amounts = new int[][]{
		{  33,  33,  33,   0 },		/* black */
		{ 100,	 0,	  0,   0 },		/* red */
		{   0,  50,  50,   0 },		/* green */
		{  33,  33,  33,   0 },		/* brown */
		{   0,   0, 100,   0 },		/* blue */
		{  50,   0,  50,   0 },		/* purple */
		{   0,   0,   0,   0 }, 	/* TODO cyan */
		{  20,  20,  20,  40 },		/* light gray */
		{   0,   0,   0,   0 },		/* TODO gray */
		{  50,   0,   0,  50 },		/* TODO pink */
		{   0,  33,  33,  33 },		/* TODO lime */
		{   0, 100,   0,   0 },		/* yellow */
		{   0,   0,  50,  50 },		/* light blue */
		{   0,   0,   0,   0 },		/* TODO magenta */
		{  50,  50,   0,   0 },		/* orange */
		{   0,   0,   0, 100 }		/* white */
	};

	public static void initialise() {
		
		for (int i = 0; i < 16; i++)
			dyeRecipeMap.put(new ComparableItemStack(new ItemStack(Items.dye, 1, i)),
					new DyeRecipe(new ItemStack(Items.dye, 1, i), amounts[i][0], amounts[i][1], amounts[i][2], amounts[i][3]));

		/* Add the default dyes as sources */
		for (int i = 0; i < 16; i++)
			dyeSourceMap.put(new ComparableItemStack(new ItemStack(Items.dye, 1, i)), new ItemStack(Items.dye, 1, i));
	}
	
	private static void addItemRecipe(ItemStack in, ItemStack out) {
		
		/* output must be a valid dye */
		if (!MKManagers.dyeOreDictHelper.isDye(out))
			return;
		
		/* output must be a dye that we are handling */
		if (getDyeRecipe(out) == null)
			return;
		
		dyeSourceMap.put(new ComparableItemStack(in), out.copy());
	}
	
	/**
	 * Load the item to dye recipes
	 * This parses the recipe lists, so call as late as possible
	 */
	public static void loadItemRecipes() {
		
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

					addItemRecipe(in, out);
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
			 * We may be using the damage value as part of the hashmap, so this
			 * makes sure a lookup will work.
			 */
			
			ItemStack in = ((ItemStack)pairs.getKey());			
			ItemStack cleanItem = new ItemStack(in.getItem());
			ItemStack out = ((ItemStack)pairs.getValue()).copy();
			
			addItemRecipe(cleanItem, out);
		}
	}
	
	public static DyeRecipe getDyeRecipe(ItemStack dye) {
		
		if (dye == null || dye.getItem() != Items.dye)
			return null;
		
		return dyeRecipeMap.get(new ComparableItemStack(dye));
	}
	
	public static ItemStack getDyeFromSource(ItemStack s) {
		
		return dyeSourceMap.get(new ComparableItemStack(s));
	}
	
	public static ComparableItemStack[] getDyeSources() {
		
		return dyeSourceMap.keySet().toArray(new ComparableItemStack[0]);
	}
	
	public static void debugDumpMap() {
		
		Iterator iter = dyeRecipeMap.entrySet().iterator();
		while (iter.hasNext()) {
			
			Map.Entry pairs = (Map.Entry)iter.next();		
			ComparableItemStack t = (ComparableItemStack)pairs.getKey();
			LogHelper.info("[DyeHelper] dyeRecipeMap: " + t.toItemStack() + " -> " + pairs.getValue());		
		}
		
		iter = dyeSourceMap.entrySet().iterator();
		while (iter.hasNext()) {
			
			Map.Entry pairs = (Map.Entry)iter.next();
			ComparableItemStack t = (ComparableItemStack)pairs.getKey();
			LogHelper.info("[DyeHelper] dyeSourceMap: " + t.toItemStack() + " -> " + (ItemStack)pairs.getValue());		
		}
		
	}
	
	public static class DyeRecipe {
		
		private ItemStack srcDye;
		private HashMap<String, FluidStack> outputs;
		
		public DyeRecipe(ItemStack srcDye, int red, int yellow, int blue, int white) {
			
			this.srcDye = srcDye;
			this.outputs = new HashMap<String, FluidStack>();
			
			this.outputs.put(DYE_RED, new FluidStack(MKFluids.fluidDyeRed, red));
			this.outputs.put(DYE_YELLOW,  new FluidStack(MKFluids.fluidDyeYellow, yellow));
			this.outputs.put(DYE_BLUE,  new FluidStack(MKFluids.fluidDyeBlue, blue));
			this.outputs.put(DYE_WHITE,  new FluidStack(MKFluids.fluidDyeWhite, white));		
		}
		
		public ItemStack getSource() {
			
			return this.srcDye;
		}
		
		public FluidStack getRed() {
			
			return this.outputs.get(DYE_RED);
		}
		
		public FluidStack getYellow() {
			
			return this.outputs.get(DYE_YELLOW);
		}
		
		public FluidStack getBlue() {
			
			return this.outputs.get(DYE_BLUE);
		}
		
		public FluidStack getWhite() {
			
			return this.outputs.get(DYE_WHITE);
		}
		
		public DyeRecipe copy() {
			
			DyeRecipe r = new DyeRecipe(srcDye.copy(), getRed().amount, getYellow().amount, getBlue().amount, getWhite().amount);
			return r;
		}
		
		@Override
		public String toString() {

			return srcDye + " red:" + getRed().amount + " yellow:" + getYellow().amount + " blue:" + getBlue().amount + " white:" + getWhite().amount;
		}
	}
}
