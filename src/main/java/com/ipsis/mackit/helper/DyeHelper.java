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
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.ipsis.cofhlib.util.inventory.ComparableItemStack;
import com.ipsis.mackit.fluid.MKFluids;
import com.ipsis.mackit.item.MKItems;
import com.ipsis.mackit.manager.MKManagers;

public class DyeHelper {

	public static final String DYE_RED = "Red";
	public static final String DYE_YELLOW = "Yellow";
	public static final String DYE_BLUE = "Blue";
	public static final String DYE_WHITE = "White";
	
	public static enum DyeColor { 
		
		BLACK(0, "Black"),
        RED(1, "Red"),
        GREEN(2, "Green"),
        BROWN(3, "Brown"),
        BLUE(4, "Blue"),
        PURPLE(5, "Purple"),
        CYAN(6, "Cyan"),
        LIGHTGRAY(7, "LightGray"),
        GRAY(8, "Gray"),
        PINK(9, "Pink"),
        LIME(10, "Lime"),
        YELLOW(11, "Yellow"),
        LIGHTBLUE(12, "LightBlue"),
        MAGENTA(13, "Magenta"),
        ORANGE(14, "Orange"),
        WHITE(15, "White");
		
		public static final DyeColor[] VALID_COLORS = {BLACK, RED, GREEN, BROWN, BLUE, PURPLE, CYAN, LIGHTGRAY, 
														GRAY, PINK, LIME, YELLOW, LIGHTBLUE, MAGENTA, ORANGE, WHITE};
        
        private int dmg;
		private String name;
		
		private DyeColor(int dmg, String name) {
			
			this.dmg = dmg;
			this.name = name;
		}
		
		public int getDmg() {
			
			return dmg;
		}
		
		public String getName() {
			
			return name;
		}
		
		public ItemStack getItemStack() {
			
			return new ItemStack(Items.dye, 1, dmg);
		}
		
		public IIcon getIcon() {
			
			return Items.dye.getIconFromDamage(dmg);
		}
		
		public static DyeColor getFromDmg(int dmg) {
			
			if (dmg < 0 || dmg > 15)
				return WHITE;
			
			/* cheaty - ordinal is actually the damage */
			return VALID_COLORS[dmg];
		}		
	};
	
	private static HashMap<ComparableItemStack, DyeRecipe> dyeRecipeMap = new HashMap<ComparableItemStack, DyeRecipe>();
	private static HashMap<ComparableItemStack, ItemStack> dyeSourceMap = new HashMap<ComparableItemStack, ItemStack>();	
	
	/**
	 * The proportion of RYBW that is produced for each dye type
	 * Stored in 72ths (LCM of 2,3,4,6,8,9!)
	 */
	private static final int LCM = 72;
	public static final int DYE_BASE_AMOUNT = LCM;
	private static final int[][] DYE_PROPS = new int[][] {
		/*     Red,     Yellow,      Blue,     White */
		
		{     LCM/3,     LCM/3,     LCM/3,         0 },		/* Black */
		{       LCM,	     0,         0,         0 },		/* Red */
		{         0,      LCM/2,    LCM/2,         0 },		/* Green */
		{ 3*(LCM/4),      LCM/8,    LCM/8,         0 },		/* Brown */
		{         0,          0,      LCM,         0 },		/* Blue */
		{     LCM/2,          0,    LCM/2,         0 },		/* Purple */
		{         0,          0,    LCM/4, 3*(LCM/4) },		/* Cyan */
		{     LCM/9,      LCM/9,    LCM/9, 2*(LCM/3) },		/* Light Gray */
		{     LCM/6,      LCM/6,    LCM/6,     LCM/2 },		/* Gray */
		{     LCM/2,          0,        0,     LCM/2 },		/* Pink */
		{         0,      LCM/4,    LCM/4,     LCM/2 },		/* Lime */
		{         0,        LCM,        0,         0 },		/* Yellow */
		{         0,          0,    LCM/2,     LCM/2 },		/* Light Blue */
		{     LCM/2,          0,    LCM/4,     LCM/4 },		/* Magenta */
		{     LCM/2,      LCM/2,        0,         0 },		/* Orange */
		{         0,          0,        0,       LCM }		/* White */
	};

	public static void initialise() {
		
		for (int i = 0; i < 16; i++) {
			if (DYE_PROPS[i][0] + DYE_PROPS[i][1] + DYE_PROPS[i][2] + DYE_PROPS[i][3] != LCM)
				LogHelper.error("DyeHelper: init - dye proportions broken");
		}
		
		for (int i = 0; i < 16; i++)
			dyeRecipeMap.put(new ComparableItemStack(new ItemStack(Items.dye, 1, i)),
					new DyeRecipe(new ItemStack(Items.dye, 1, i), DYE_PROPS[i][0], DYE_PROPS[i][1], DYE_PROPS[i][2], DYE_PROPS[i][3]));

		/* Add the default dyes as sources */
		for (int i = 0; i < 16; i++)
			dyeSourceMap.put(new ComparableItemStack(new ItemStack(Items.dye, 1, i)), new ItemStack(Items.dye, 1, i));
		
		/* Add the sponge recipes */
		for (int i = 0; i < MKItems.dyes.length; i++)
			dyeSourceMap.put(new ComparableItemStack(new ItemStack(MKItems.dyes[i])), new ItemStack(Items.dye, 1, i));
	}
	
	private static void addItemRecipe(ItemStack in, ItemStack out) {
		
		/* output must be a valid dye */
		if (!DyeOreDictHelper.isDye(out))
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
			LogHelper.debug("[DyeHelper] dyeRecipeMap: " + t.toItemStack() + " -> " + pairs.getValue());		
		}
		
		iter = dyeSourceMap.entrySet().iterator();
		while (iter.hasNext()) {
			
			Map.Entry pairs = (Map.Entry)iter.next();
			ComparableItemStack t = (ComparableItemStack)pairs.getKey();
			LogHelper.debug("[DyeHelper] dyeSourceMap: " + t.toItemStack() + " -> " + (ItemStack)pairs.getValue());		
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
