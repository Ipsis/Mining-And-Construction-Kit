package com.ipsis.mackit.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.ipsis.cofhlib.util.ItemHelper;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.helper.PainterHelper;

/**
 * 
 * This contains the recipes that the painter can use.
 * 
 * The valid recipes are
 * 
 * A recipe that only contains 2 item type
 * A recipe that uses only 1 dye.
 *
 */
public class PainterManager {
	
	private HashMap<Integer, PainterRecipe> recipes;
	
	public PainterManager() {
		
		recipes = new HashMap<Integer, PainterRecipe>();
	}
	
	public PainterRecipe getRecipe(ItemStack src) {
		
		if (src == null)
			return null;
		
		return recipes.get(ItemHelper.getHashCode(src));
	}
	
	private void addRecipe(ItemStack srcDye, ItemStack src, int srcCount, ItemStack output) {

		LogHelper.error("PainterManager: addRecipe " + src + ":" + srcCount + "->" + output);
		recipes.put(ItemHelper.getHashCode(src), new PainterRecipe(srcDye, src, srcCount, output));
		
		/**
		 * The added recipe takes a dye and an item and produces a dyed item
		 * This means that the output is also a valid input ....
		 */
		
	}
	
	private void handleShapelessRecipe(ShapelessRecipes r) {
		
		boolean valid = true;
		PainterHelper helper = new PainterHelper();
		helper.reset();
		
		/* Ignore recipes that produce dyes */
		if (MKManagers.dyeHelper.isDye(r.getRecipeOutput()) == true)
				return;
		
		Iterator iter = r.recipeItems.iterator();
		while (iter.hasNext() && valid == true) {
			
			ItemStack currIn = (ItemStack)iter.next();			
			if (currIn == null)
				continue;
			
			valid = helper.verify(currIn);						
		}
		
		if (valid && helper.getInputDye() != null && helper.getInputItem() != null && helper.getItemCount() > 0)
			addRecipe(helper.getInputDye(), helper.getInputItem(), helper.getItemCount(), r.getRecipeOutput());			
	}
	
	private void handleShapedRecipe(ShapedRecipes r) {
		
		boolean valid = true;
		PainterHelper helper = new PainterHelper();
		helper.reset();
		
		/* Ignore recipes that produce dyes */
		if (MKManagers.dyeHelper.isDye(r.getRecipeOutput()) == true)
				return;
		
		for (ItemStack currIn : r.recipeItems) {
		
			if (currIn == null)
				continue;
			
			valid = helper.verify(currIn);		
			
			if (!valid)
				break;
		}
		
		if (valid && helper.getInputDye() != null && helper.getInputItem() != null && helper.getItemCount() > 0)
			addRecipe(helper.getInputDye(), helper.getInputItem(), helper.getItemCount(), r.getRecipeOutput());	
	}
	
	private void handleShapelessOreRecipe(ShapelessOreRecipe r) {
		
		boolean valid = true;
		PainterHelper helper = new PainterHelper();
		helper.reset();
		
		/* Ignore recipes that produce dyes */
		if (MKManagers.dyeHelper.isDye(r.getRecipeOutput()) == true)
				return;
		
		List recipeInput = r.getInput();
		for (Object o : recipeInput) {
			
			if (o == null)
				continue;
			
			if (o instanceof ItemStack) {
				
				ItemStack currIn = (ItemStack)o;
				valid = helper.verify(currIn);
			} else if (o instanceof ArrayList) {
				
				ArrayList l = (ArrayList)o;
				if (l.size() > 1)
					continue;
				
				ItemStack currIn = (ItemStack)l.get(0);
				if (currIn == null)
					continue;

				valid = helper.verify(currIn);
			}
			
			if (!valid)
				break;
		}
		
		if (valid && helper.getInputDye() != null && helper.getInputItem() != null && helper.getItemCount() > 0)
			addRecipe(helper.getInputDye(), helper.getInputItem(), helper.getItemCount(), r.getRecipeOutput());	
	}
	
	private void handleShapedOreRecipe(ShapedOreRecipe r) {

		boolean valid = true;
		PainterHelper helper = new PainterHelper();
		helper.reset();		
		
		/* Ignore recipes that produce dyes */
		if (MKManagers.dyeHelper.isDye(r.getRecipeOutput()) == true)
				return;
		
		Object[] recipeInput = r.getInput();
		for (Object o : recipeInput) {
			
			if (o == null)
				continue;
			
			if (o instanceof ItemStack) {
				
				ItemStack currIn = (ItemStack)o;
				valid = helper.verify(currIn);				 
			} else if (o instanceof ArrayList) {
				
				ArrayList l = (ArrayList)o;
				if (l.size() > 1)
					continue;
				
				ItemStack currIn = (ItemStack)l.get(0);
				if (currIn == null)
					continue;
				
				valid = helper.verify(currIn);
			}
			
			if (!valid)
				break;
		}
		
		if (valid && helper.getInputDye() != null && helper.getInputItem() != null && helper.getItemCount() > 0)
			addRecipe(helper.getInputDye(), helper.getInputItem(), helper.getItemCount(), r.getRecipeOutput());	
	}
	
	public void loadRecipes() {
				
		List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
		for (IRecipe irecipe : allrecipes) {
			
			if (irecipe instanceof ShapelessRecipes) {
				
				handleShapelessRecipe((ShapelessRecipes)irecipe);							
			} else if (irecipe instanceof ShapedRecipes) {
				
				handleShapedRecipe((ShapedRecipes)irecipe);
			} else if (irecipe instanceof ShapedOreRecipe) {
				
				handleShapedOreRecipe((ShapedOreRecipe)irecipe);			
			} else if (irecipe instanceof ShapelessOreRecipe) {
				
				handleShapelessOreRecipe((ShapelessOreRecipe)irecipe);
			}
		}
	}
	
	/**
	 * Dye selections
	 */
	
	/*
	private static List<ItemStack> outputs;
	
	private void addDyes() {
		
		outputs = new ArrayList<ItemStack>();
		
		outputs.add(new ItemStack(Items.dye, 1, 0));
		outputs.add(new ItemStack(Items.dye, 1, 1));
		outputs.add(new ItemStack(Items.dye, 1, 2));
		outputs.add(new ItemStack(Items.dye, 1, 3));
		outputs.add(new ItemStack(Items.dye, 1, 4));
		outputs.add(new ItemStack(Items.dye, 1, 5));
		outputs.add(new ItemStack(Items.dye, 1, 6));
		outputs.add(new ItemStack(Items.dye, 1, 7));
		outputs.add(new ItemStack(Items.dye, 1, 8));
		outputs.add(new ItemStack(Items.dye, 1, 9));
		outputs.add(new ItemStack(Items.dye, 1, 10));
		outputs.add(new ItemStack(Items.dye, 1, 11));
		outputs.add(new ItemStack(Items.dye, 1, 12));
		outputs.add(new ItemStack(Items.dye, 1, 13));
		outputs.add(new ItemStack(Items.dye, 1, 14));
		outputs.add(new ItemStack(Items.dye, 1, 15));		
	}
	
	public ItemStack getOutput(int idx) {
		
		if (idx < 0 || idx >= outputs.size())
			return null;
		
		return outputs.get(idx).copy();
	} */
	

}
