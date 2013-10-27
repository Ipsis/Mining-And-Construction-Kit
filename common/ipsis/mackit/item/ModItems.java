package ipsis.mackit.item;

import ipsis.mackit.lib.ItemIds;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

	/* Mod item instances */
	public static Item mackitCasing;
	public static Item mackitPortableCasing;
	public static Item fixerFoamGun;
	public static Item fixerFoamPellet;
	
	public static void init() {
		
		mackitCasing = new ItemMacKitCasing(ItemIds.MACKIT_CASING);
		mackitPortableCasing = new ItemMacKitPortableCasing(ItemIds.MACKIT_PORTABLE_CASING);
		fixerFoamGun = new ItemFixerFoamGun(ItemIds.FIXER_FOAM_GUN);
		fixerFoamPellet = new ItemFixerFoamPellet(ItemIds.FIXER_FOAM_PELLET);
		
		GameRegistry.addRecipe(new ItemStack(mackitCasing), new Object[] { 
			"iii", 
			"g g", 
			"iii", 
			Character.valueOf('i'), Item.ingotIron, 
			Character.valueOf('g'), Item.goldNugget });
		GameRegistry.addRecipe(new ItemStack(mackitPortableCasing), new Object[] { 
			"igi", 
			"p p", 
			"igi", 
			Character.valueOf('i'), Item.ingotIron, 
			Character.valueOf('g'), Block.glass, 
			Character.valueOf('p'), Block.thinGlass });
		GameRegistry.addShapelessRecipe(new ItemStack(fixerFoamPellet, 4), new Object[] { 
			Block.sand, 
			Block.gravel, 
			Item.slimeBall, 
			Item.bucketWater });
		
	}
}
