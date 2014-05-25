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
 * TODO the duplicated code for each recipe type HAS TO GO!
 */
public class PainterManager {
	
	private HashMap<Integer, PainterRecipe> recipes;
	
	public PainterManager() {
		
		recipes = new HashMap<Integer, PainterRecipe>();
	}
	
	private void addRecipe(ItemStack src, int srcCount, ItemStack output) {
		
		if (src != null && output == null)
			recipes.put(ItemHelper.getHashCode(src), new PainterRecipe(src, srcCount, output));
	}
	
	private void handleShapelessRecipe(ShapelessRecipes r) {
		
		ItemStack currDye = null;
		ItemStack currItemStack = null;
		boolean ignore = false;
		int itemCount = 0;
		
		/* Ignore recipes that produce dyes */
		if (MKManagers.squeezerMgr.isDye(r.getRecipeOutput()) == true)
				return;
		
		Iterator iter = r.recipeItems.iterator();
		while (iter.hasNext() && ignore == false) {
			
			ItemStack currIn = (ItemStack)iter.next();			
			if (currIn == null)
				continue;
			
			if (MKManagers.squeezerMgr.isDye(currIn)) {
				
				if (currDye == null)
					currDye = currIn;
				else
					ignore = true;
			} else if (currItemStack == null) {
				
				itemCount = 1;
				currItemStack = currIn;
			} else if (currItemStack.isItemEqual(currIn) == false) {
				
				ignore = true;
			} else {
				
				itemCount++;
			}							
		}
		
		if (!ignore && currDye != null)
			addRecipe(currItemStack, itemCount, r.getRecipeOutput());			
	}
	
	private void handleShapedRecipe(ShapedRecipes r) {
		
		ItemStack currDye = null;
		ItemStack currItemStack = null;
		boolean ignore = false;
		int itemCount = 0;
		
		/* Ignore recipes that produce dyes */
		if (MKManagers.squeezerMgr.isDye(r.getRecipeOutput()) == true)
				return;
		
		for (ItemStack currIn : r.recipeItems) {
			
			if (currIn == null)
				continue;
			
			if (MKManagers.squeezerMgr.isDye(currIn)) {
				
				if (currDye == null)
					currDye = currIn;
				else
					ignore = true;
			} else if (currItemStack == null) {
				
				itemCount = 1;
				currItemStack = currIn;
			} else if (currItemStack.isItemEqual(currIn) == false) {
				
				ignore = true;
			} else {
				
				itemCount++;
			}	
			
			if (ignore)
				break;
		}
		
		if (!ignore && currDye != null)
			addRecipe(currItemStack, itemCount, r.getRecipeOutput());	
	}
	
	private void handleShapelessOreRecipe(ShapelessOreRecipe r) {
		
		ItemStack currDye = null;
		ItemStack currItemStack = null;
		boolean ignore = false;
		int itemCount = 0;
		
		/* Ignore recipes that produce dyes */
		if (MKManagers.squeezerMgr.isDye(r.getRecipeOutput()) == true)
				return;
		
		List recipeInput = r.getInput();
		for (Object o : recipeInput) {
			
			if (o == null)
				continue;
			
			if (o instanceof ItemStack) {
				
				ItemStack currIn = (ItemStack)o;
				
				if (MKManagers.squeezerMgr.isDye(currIn)) {
					
					if (currDye == null)
						currDye = currIn;
					else
						ignore = true;
				} else if (currItemStack == null) {
					
					itemCount = 1;
					currItemStack = currIn;
				} else if (currItemStack.isItemEqual(currIn) == false) {
					
					ignore = true;
				} else {
					
					itemCount++;
				}	
			} else if (o instanceof ArrayList) {
				
				ArrayList l = (ArrayList)o;
				if (l.size() > 1)
					continue;
				
				ItemStack currIn = (ItemStack)l.get(0);
				if (currIn == null)
					continue;
				
				if (MKManagers.squeezerMgr.isDye(currIn)) {
					
					if (currDye == null)
						currDye = currIn;
					else
						ignore = true;
				} else if (currItemStack == null) {
					
					itemCount = 1;
					currItemStack = currIn;
				} else if (currItemStack.isItemEqual(currIn) == false) {
					
					ignore = true;
				} else {
					
					itemCount++;
				}	
			}
			
			if (ignore)
				break;
		}
		
		if (!ignore && currDye != null)
			addRecipe(currItemStack, itemCount, r.getRecipeOutput());	
	}
	
	private void handleShapedOreRecipe(ShapedOreRecipe r) {

		ItemStack currDye = null;
		ItemStack currItemStack = null;
		boolean ignore = false;
		int itemCount = 0;
		
		/* Ignore recipes that produce dyes */
		if (MKManagers.squeezerMgr.isDye(r.getRecipeOutput()) == true)
				return;
		
		Object[] recipeInput = r.getInput();
		for (Object o : recipeInput) {
			
			if (o == null)
				continue;
			
			if (o instanceof ItemStack) {
				
				ItemStack currIn = (ItemStack)o;
				
				if (MKManagers.squeezerMgr.isDye(currIn)) {
					
					if (currDye == null)
						currDye = currIn;
					else
						ignore = true;
				} else if (currItemStack == null) {
					
					itemCount = 1;
					currItemStack = currIn;
				} else if (currItemStack.isItemEqual(currIn) == false) {
					
					ignore = true;
				} else {
					
					itemCount++;
				}	
				 
			} else if (o instanceof ArrayList) {
				
				ArrayList l = (ArrayList)o;
				if (l.size() > 1)
					continue;
				
				ItemStack currIn = (ItemStack)l.get(0);
				if (currIn == null)
					continue;
				
				if (MKManagers.squeezerMgr.isDye(currIn)) {
					
					if (currDye == null)
						currDye = currIn;
					else
						ignore = true;
				} else if (currItemStack == null) {
					
					itemCount = 1;
					currItemStack = currIn;
				} else if (currItemStack.isItemEqual(currIn) == false) {
					
					ignore = true;
				} else {
					
					itemCount++;
				}	
			}
			
			if (ignore)
				break;
		}
		
		if (!ignore && currDye != null)
			addRecipe(currItemStack, itemCount, r.getRecipeOutput());	
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
