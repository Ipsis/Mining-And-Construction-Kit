package ipsis.mackit.item.crafting;

import ipsis.mackit.fluid.ModFluids;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * RYB
 * 
 * Minecraft dyeing
 * 
 * red
 * yellow
 * black
 * brown
 * white
 * green
 * 
 * orange     - red + yellow
 * cyan       - green + blue
 * purple     - red + blue
 * gray       - black + white
 * light blue - blue + white
 * pink       - red + white
 * lime       - green + white
 * magenta    - purple + pink - red + blue + red + white
 * light gray - gray + white - black + white + white
 * 
 * 
 *   
 */

public class DyeCraftingManager {
	
	private static final DyeCraftingManager instance = new DyeCraftingManager();
	private ArrayList<DyeSource> sources = new ArrayList<DyeSource>();
	private ArrayList<ItemStack> stamperRecipes = new ArrayList<ItemStack>();
	
	private FluidStack pureDyeRecipe[];
	
	private DyeCraftingManager() {
		
		pureDyeRecipe = new FluidStack[5];
		pureDyeRecipe[0] = new FluidStack(ModFluids.fluidRedDye.getID(), 150);
		pureDyeRecipe[1] = new FluidStack(ModFluids.fluidYellowDye.getID(), 150);
		pureDyeRecipe[2] = new FluidStack(ModFluids.fluidBlueDye.getID(), 150);
		pureDyeRecipe[3] = new FluidStack(ModFluids.fluidWhiteDye.getID(), 150);
		pureDyeRecipe[4] = new FluidStack(FluidRegistry.WATER.getID(), 150);
		
		addVanillaRecipes();
		addStamperRecipes();
	}
	
    public static final DyeCraftingManager getInstance() {

        return instance;
    }
    
    private FluidStack[] createFluids(boolean red, boolean yellow, boolean blue, boolean white) {
    	ArrayList<FluidStack> fluids = new ArrayList<FluidStack>();
    	
    	int count = 0;
    	if (red)
    		count++;
    	if (yellow)
    		count++;
    	if (blue)
    		count++;
    	if (white)
    		count++;
    	
    	if (count == 0)
    		return null;
    	
    	int amount = 1000/count;
    	
    	if (red) {
    		FluidStack f = new FluidStack(ModFluids.fluidRedDye.getID(), amount);
    		fluids.add(f);
    	}
    	
    	if (yellow) {
    		FluidStack f = new FluidStack(ModFluids.fluidYellowDye.getID(), amount);
    		fluids.add(f);
    	}
    	
    	if (blue) {
    		FluidStack f = new FluidStack(ModFluids.fluidBlueDye.getID(), amount);
    		fluids.add(f);
    	}
    	
    	if (white) {
    		FluidStack f = new FluidStack(ModFluids.fluidWhiteDye.getID(), amount);
    		fluids.add(f);
    	}
    	
    	return fluids.toArray(new FluidStack[fluids.size()]);
    }
    
    private void addVanillaRecipes() {
    	
    	/* 1.6 versions only! */
    	/* rose red - poppy, rose bush, red tulips */
    	addDyeSourceRecipe(new ItemStack(Block.plantRed), createFluids(true, false, false, false));
    
    	
    	/* yellow - dandelion, sunflower */
    	addDyeSourceRecipe(new ItemStack(Block.plantYellow), createFluids(false, true, false, false));
    	
    	/* cactus green - cactus */
    	addDyeSourceRecipe(new ItemStack(Block.cactus), createFluids(true, true, false, false));
    	
    	/* lapis lazuli - lapis lazuli ore */
    	addDyeSourceRecipe(new ItemStack(Item.dyePowder, 1, 4), createFluids(false, false, true, false));
    	
    	/* light blue - blue orchid - createFluids(false, false, true, true) */
    	
    	/* magenta - lilac, alium - createFluids(true, false, true, true) */
    	
    	/* pink - peony, pink tulips - createFluids(true, false, false, true) */
    	
    	/* white - bone or bonemeal */
    	addDyeSourceRecipe(new ItemStack(Item.dyePowder, 1, 15), new FluidStack(ModFluids.fluidWhiteDye, 300));
    	addDyeSourceRecipe(new ItemStack(Item.bone), new FluidStack(ModFluids.fluidWhiteDye, 1000));
    	
    	/* light gray dye - azure bluet, oxeye daisy, white tulip - createFluids(true, true, true, true)*/
    	
    	/* black - ink sack */
    	addDyeSourceRecipe(new ItemStack(Item.dyePowder, 1, 0), createFluids(true, true, true, false));
    	
    	/* brown - cocoa beans */
    	addDyeSourceRecipe(new ItemStack(Item.dyePowder, 1, 3), createFluids(true, true, true, false));
    }
    
    private void addStamperRecipes() {
    	
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 0)); /* ink sack */
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 1));
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 2)); /* cactus green */
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 3)); /* cocoa beans */
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 4)); /* lapis lazuli */
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 5));
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 6));
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 7));
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 8));
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 9));
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 10));
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 11));
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 12));
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 13));
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 14));
    	stamperRecipes.add(new ItemStack(Item.dyePowder, 1, 15)); /* bone meal */
    }
	
    public boolean isDyeSource(ItemStack itemStack) {
    	
    	for (DyeSource source: sources) {
    		if (source.getItemId() == itemStack.itemID)
    			return true;
    	}
    	return false;
    }
    
    public FluidStack[] getDyeSourceOutput(ItemStack itemstack) {
    	for (DyeSource source: sources) {
    		if (source.getItemId() == itemstack.itemID)
    			return source.getOutput();
    	}
    	
    	return null;
    }
    
    public FluidStack getDyeSourceOutput(ItemStack itemstack, FluidStack fluid) {
    	
    	if (!isDyeSource(itemstack))
    		return null;
    	
    	for (DyeSource source: sources) {
    		if (source.getItemId() == itemstack.itemID) {
    			return new FluidStack(fluid.fluidID, source.getOutputAmount(fluid));
    		}
    	}
    	
    	return null;
    }
    
    public boolean isDyeSourceOutput(ItemStack itemstack, FluidStack fluid) {
    	
    	if (!isDyeSource(itemstack))
    		return false;
    	
    	for (DyeSource source: sources) {
    		if (source.getItemId() == itemstack.itemID) {
    			return source.isOutput(fluid);
    		}
    	}
    	
    	return false;
    	
    }
    
    public FluidStack[] getPureDyeRecipe() {
    	return pureDyeRecipe;
    }
    
    /* item to liquid dyes */
    public void addDyeSourceRecipe(ItemStack itemStack, FluidStack[] fluids) {
    	
    	DyeSource source = new DyeSource(itemStack.itemID);
    	for (FluidStack fluid: fluids) {
    		source.addOutputFluid(fluid);
    	}
    	
    	sources.add(source);    	
    }
    
    public void addDyeSourceRecipe(ItemStack itemStack, FluidStack fluid) {
    	DyeSource source = new DyeSource(itemStack.itemID);
    	source.addOutputFluid(fluid);	
    	sources.add(source); 
    }
    
    /* item to dyed item */
    public void addDyeRecipe(ItemStack itemStack, ItemStack output) {
    	
    	
    }
    
    /* item to dyed item at non-standard pure dye usage */
    public void addDyeRecipe(ItemStack itemStack, ItemStack output, int amount) {
    	
    }
    

}
