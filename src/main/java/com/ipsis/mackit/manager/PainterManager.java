package com.ipsis.mackit.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
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
 * TODO probably need to store the dye as well!
 */
public class PainterManager {
	
	private HashMap<Integer, PainterRecipe> recipes;
	
	public PainterManager() {
		
		recipes = new HashMap<Integer, PainterRecipe>();
	}
	
	private void addRecipe(ItemStack src, int srcCount, ItemStack output) {

		LogHelper.error("PainterManager: addRecipe " + src + ":" + srcCount + "->" + output);
		recipes.put(ItemHelper.getHashCode(src), new PainterRecipe(src, srcCount, output));
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
			addRecipe(helper.getInputItem(), helper.getItemCount(), r.getRecipeOutput());			
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
			addRecipe(helper.getInputItem(), helper.getItemCount(), r.getRecipeOutput());
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
			addRecipe(helper.getInputItem(), helper.getItemCount(), r.getRecipeOutput());
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
			addRecipe(helper.getInputItem(), helper.getItemCount(), r.getRecipeOutput());	
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

}
