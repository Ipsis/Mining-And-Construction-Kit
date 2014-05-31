package com.ipsis.mackit.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemFood;
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
 */
public class PainterManager {
	
	static class PaintSrc {
		
		public ItemStack itemStack;
		public ItemStack dye;
		
		public PaintSrc(ItemStack itemStack, ItemStack dye) {
			
			this.itemStack = itemStack;
			this.dye = dye;
		}
	}
	
	private HashMap<PaintSrc, PainterRecipe> recipes;
	
	public PainterManager() {
		
		recipes = new HashMap<PaintSrc, PainterRecipe>();
	}
	
	public PainterRecipe getRecipe(ItemStack srcDye, ItemStack src) {
		
		if (src == null || srcDye == null)
			return null;
		
		return recipes.get(new PaintSrc(src, srcDye));
	}
	
	private void addRecipe(ItemStack srcDye, ItemStack src, int srcCount, ItemStack output) {

		/* Skip anything that is a food */
		if (output.getItem() instanceof ItemFood)
			return;

		/**
		 * We need two lists
		 * 1. src item + dye = dyed item [srcItemStack + srcDye -> outputItemStack]
		 * 2. output item - dye = src item [outputItemStack -> srcItemStack]
		 * 
		 * The second list is a list of items that can have the dye stripped from them
		 */
		
		LogHelper.error("PainterManager: PAINT " + src + "+" + srcDye + " painted -> " + output); 
		recipes.put(new PaintSrc(src, srcDye), new PainterRecipe(src, srcDye, srcCount, output));
		
		ItemStack one_output = output.copy();
		one_output.stackSize = 1;
		ItemStack one_src = src.copy();
		one_src.stackSize = 1;
		LogHelper.error("PainterManager: STRIP " + one_output + " stripped -> " + one_src);
		MKManagers.dyeStripperMgr.addRecipe(one_output, one_src);
	}
	
	private void handleShapelessRecipe(ShapelessRecipes r) {
		
		boolean valid = true;
		PainterHelper helper = new PainterHelper();
		helper.reset();
		
		/* Ignore recipes that produce dyes */
		if (MKManagers.dyeOreDictHelper.isDye(r.getRecipeOutput()) == true)
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
		if (MKManagers.dyeOreDictHelper.isDye(r.getRecipeOutput()) == true)
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
		if (MKManagers.dyeOreDictHelper.isDye(r.getRecipeOutput()) == true)
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
		if (MKManagers.dyeOreDictHelper.isDye(r.getRecipeOutput()) == true)
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
}
