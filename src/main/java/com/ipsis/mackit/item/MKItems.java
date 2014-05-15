package com.ipsis.mackit.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class MKItems {
	

	public static void preInit() {
		
		itemFixerFoamGun = new ItemFixerFoamGun().setUnlocalizedName("fixerFoamGun");
		itemFixerFoamRefill = new ItemFixerFoamRefill().setUnlocalizedName("fixerFoamPellet");
		
		GameRegistry.registerItem(itemFixerFoamGun, "item.fixerFoamGun");
		GameRegistry.registerItem(itemFixerFoamRefill, "item.fixerFoamPellet");
	}
	
	public static void initialize() {
		
		loadItems();
	}
	
	private static void loadItems() {
		
		
	}
	
	public static void postInit() {
		
		GameRegistry.addShapedRecipe(new ItemStack(itemFixerFoamGun, 1, 64),
				"bsb", "iwi", "iwi",
				'b', new ItemStack(Items.bucket),
				's', new ItemStack(Items.slime_ball),
				'i', new ItemStack(Items.iron_ingot),
				'w', new ItemStack(Blocks.planks));
		GameRegistry.addShapelessRecipe(new ItemStack(itemFixerFoamGun, 1, 0), new ItemStack(itemFixerFoamGun, 1, 64), new ItemStack(itemFixerFoamRefill, 1));
	}
	
	public static Item itemFixerFoamGun;
	public static Item itemFixerFoamRefill;
}
