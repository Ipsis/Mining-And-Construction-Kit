package ipsis.mackit.item.crafting;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import cofh.util.ItemHelper;

public class ExtractorManager {

	private static final ExtractorManager instance = new ExtractorManager();
	
	HashMap<Integer, ExtractorRecipe> recipes;
	
	private ExtractorManager() {
		
		recipes = new HashMap<Integer, ExtractorRecipe>();
		
		addVanillaRecipes();
	}
	
    public static final ExtractorManager getInstance() {

        return instance;
    }
    
    private void addRecipe(ItemStack source, int redAmount, int yellowAmount, int blueAmount, int whiteAmount) {
    	
    	recipes.put(ItemHelper.getHashCode(source),
    			new ExtractorRecipe(source.itemID, source.getItemDamage()).setRed(redAmount).setYellow(yellowAmount).setBlue(blueAmount).setWhite(whiteAmount));
    }
    
    private void addRecipe(ItemStack source, int redAmount, int yellowAmount, int blueAmount, int whiteAmount, int power) {
    	
    	recipes.put(ItemHelper.getHashCode(source),
    			new ExtractorRecipe(source.itemID, source.getItemDamage()).setRed(redAmount).setYellow(yellowAmount).setBlue(blueAmount).setWhite(whiteAmount).setPower(power));
    }
    
    
    private void addVanillaRecipes() {
    	
    	/** 
    	 * Rose Red Dye
    	 */

    	/* Poppy */
    	addRecipe(new ItemStack(Block.plantRed), FluidContainerRegistry.BUCKET_VOLUME, 0, 0, 0);
    	/* Rosebush - 1.7 */
    	/* Red Tulips - 1.7 */
    	
    	/**
    	 * Orange Dye = Red + Yellow
    	 */    	
    	/* Orange Tulip - 1. 7 */
    	
    	/**
    	 * Yellow
    	 */
    	
    	/* Dandelion */
    	addRecipe(new ItemStack(Block.plantYellow), 0, FluidContainerRegistry.BUCKET_VOLUME, 0, 0);
    	/* Sunflower - 1.7 */

    	
    	/**
    	 * Green
    	 */
    	/* Cactus */
    	addRecipe(new ItemStack(Block.cactus), FluidContainerRegistry.BUCKET_VOLUME / 2, FluidContainerRegistry.BUCKET_VOLUME / 2, 0, 0);
    	
    	
    	/**
    	 * Blue
    	 */
    	/* Lapiz */
    	addRecipe(new ItemStack(Item.dyePowder, 1, 4), 0, 0, FluidContainerRegistry.BUCKET_VOLUME, 0);
    	
    	/**
    	 * Light Blue = Blue + White
    	 */
    	/* Blue Orchid - 1.7 */
    	
    	/**
    	 *  Magenta = Purple + Pink = Red + Blue + Red + White
    	 */
    	/* Lilac - 1.7 */
    	/* Alium - 1.7 */
    	
    	/**
    	 * Pink = Red + White 
    	 */
    	/* Peony - 1.7 */
    	/* Pink Tulips - 1.7 */
    	 
    	 /**
    	  * White
    	  */
    	 /* Bone = 3 bonemeal */
    	 /* Bonemeal */
    	 addRecipe(new ItemStack(Item.bone), 0, 0, 0, FluidContainerRegistry.BUCKET_VOLUME, ExtractorRecipe.DEFUALT_EXTRACTOR_POWER * 3);
    	 addRecipe(new ItemStack(Item.dyePowder, 1, 15), 0, 0, 0, FluidContainerRegistry.BUCKET_VOLUME);
    	
    	 
    	/**
    	 *  Light Gray = Gray + White = Black + White + White
    	 */
    	/* Azure Bluet - 1.7 */
    	/* Oxeye Daisy - 1.7 */
    	/* White Tulip - 1.7 */
    	 
    	/**
    	 *  Black = Red + Yellow + Blue
    	 */
    	/* Ink Sac */
    	 addRecipe(new ItemStack(Item.dyePowder, 1, 0), FluidContainerRegistry.BUCKET_VOLUME / 3, FluidContainerRegistry.BUCKET_VOLUME / 3, FluidContainerRegistry.BUCKET_VOLUME / 3, 0);
    	 
    	/**
    	 * Brown = Red + Green = Red + Yellow + Blue
    	 */
    	/* Cocoa Beans */
    	 addRecipe(new ItemStack(Item.dyePowder, 1, 3), FluidContainerRegistry.BUCKET_VOLUME / 3, FluidContainerRegistry.BUCKET_VOLUME / 3, FluidContainerRegistry.BUCKET_VOLUME / 3, 0);

    	
    }
    
    public void addModRecipes() {
    	
    }
    
    public ExtractorRecipe getRecipe(ItemStack source) {
    	return recipes.get(ItemHelper.getHashCode(source));
    }
    
    public boolean isSource(ItemStack source) {
    	return recipes.containsKey(ItemHelper.getHashCode(source));
    }
    
    public boolean isOutput(ItemStack source, FluidStack fluid) {
    	
    	if (isSource(source) == false)
    		return false;
    	
    	ExtractorRecipe r = getRecipe(source);
    	if (r == null)
    		return false;
    	
    	return r.isOutput(fluid);
    }
}
