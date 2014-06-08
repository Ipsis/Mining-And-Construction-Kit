package com.ipsis.mackit.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.ipsis.cofhlib.util.inventory.ComparableItemStack;
import com.ipsis.mackit.manager.MKManagers;
import com.ipsis.mackit.manager.PainterManager;

/**
 * Maps dyed items to their un-dyed equivalent
 *
 */
public class DyedOriginHelper {

	private static HashMap<ComparableItemStack, ItemStack> originMap = new HashMap<ComparableItemStack, ItemStack>();
	

	/**
	 * Looks at all the recipes available and creates the dyed->un-dyed map
	 */
	public static void load() {
		
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
	
	public static ItemStack getOrigin(ItemStack itemStack) {
		
		ComparableItemStack s = new ComparableItemStack(itemStack);
		s.stackSize = 1;
		return originMap.get(s);
	}
	
	public static boolean hasOrigin(ItemStack itemStack) {
		
		ComparableItemStack s = new ComparableItemStack(itemStack);
		s.stackSize = 1;
		return originMap.containsKey(s);
	}
	
	public static ItemStack[] getDyeOrigins() {
		
		return originMap.values().toArray(new ItemStack[0]);
	}
	
	private static void addRecipe(ItemStack srcDye, ItemStack src, int srcCount, ItemStack output) {

		/* Skip anything that is a food */
		if (output.getItem() instanceof ItemFood || src.getItem() instanceof ItemFood)
			return;
		
		/* output could be multiple items but we only want 1 */
		ComparableItemStack key = new ComparableItemStack(output);
		key.stackSize = 1;
		originMap.put(key, src.copy());
		
		/* Origin must also be the origin of itself, so you can dye the origin items */
		ComparableItemStack key2 = new ComparableItemStack(src);
		key2.stackSize = 1;
		originMap.put(key2, src.copy());
		
		/* Also add the painter recipe */
		ItemStack out = output.copy();
		out.stackSize = 1;
		
		PainterManager.addRecipe(src.copy(), srcDye.copy(), out, DyeHelper.DYE_BASE_AMOUNT / srcCount);
	}
	
	private static void handleShapelessRecipe(ShapelessRecipes recipe) {

		boolean valid = true;
		PaintingHelper helper = new PaintingHelper();
		helper.reset();
		
		/* Ignore recipes that produce dyes */
		if (MKManagers.dyeOreDictHelper.isDye(recipe.getRecipeOutput()) == true)
				return;
		
		Iterator iter = recipe.recipeItems.iterator();
		while (iter.hasNext() && valid == true) {
			
			ItemStack currIn = (ItemStack)iter.next();			
			if (currIn == null)
				continue;
			
			valid = helper.verify(currIn);						
		}			
		
		if (valid && helper.getInputDye() != null && helper.getInputItem() != null && helper.getItemCount() > 0)
			addRecipe(helper.getInputDye(), helper.getInputItem(), helper.getItemCount(), recipe.getRecipeOutput());		
	}
	
	private static void handleShapedRecipe(ShapedRecipes recipe) {
		
		boolean valid = true;
		PaintingHelper helper = new PaintingHelper();
		helper.reset();
		
		/* Ignore recipes that produce dyes */
		if (MKManagers.dyeOreDictHelper.isDye(recipe.getRecipeOutput()) == true)
				return;
		
		for (ItemStack currIn : recipe.recipeItems) {
		
			if (currIn == null)
				continue;
			
			valid = helper.verify(currIn);		
			
			if (!valid)
				break;
		}
		
		if (valid && helper.getInputDye() != null && helper.getInputItem() != null && helper.getItemCount() > 0)
			addRecipe(helper.getInputDye(), helper.getInputItem(), helper.getItemCount(), recipe.getRecipeOutput());			
	}
	
	private static void handleShapedOreRecipe(ShapedOreRecipe recipe) {
		
		boolean valid = true;
		PaintingHelper helper = new PaintingHelper();
		helper.reset();		
		
		/* Ignore recipes that produce dyes */
		if (MKManagers.dyeOreDictHelper.isDye(recipe.getRecipeOutput()) == true)
				return;
		
		Object[] recipeInput = recipe.getInput();
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
			addRecipe(helper.getInputDye(), helper.getInputItem(), helper.getItemCount(), recipe.getRecipeOutput());			
	}
	
	private static void handleShapelessOreRecipe(ShapelessOreRecipe recipe) {

		boolean valid = true;
		PaintingHelper helper = new PaintingHelper();
		helper.reset();
		
		/* Ignore recipes that produce dyes */
		if (MKManagers.dyeOreDictHelper.isDye(recipe.getRecipeOutput()) == true)
				return;
		
		List recipeInput = recipe.getInput();
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
			addRecipe(helper.getInputDye(), helper.getInputItem(), helper.getItemCount(), recipe.getRecipeOutput());	
	}
	
	public static void debugDumpMap() {
		
		Iterator iter = originMap.entrySet().iterator();
		while (iter.hasNext()) {
			
			Map.Entry pairs = (Map.Entry)iter.next();		
			ComparableItemStack t = (ComparableItemStack)pairs.getKey();
			LogHelper.debug("[DyedOriginHelper] : originMap: " + t.toItemStack() + " -> " + pairs.getValue());		
		}		
	}
}
