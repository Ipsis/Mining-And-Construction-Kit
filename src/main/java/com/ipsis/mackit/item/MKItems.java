package com.ipsis.mackit.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import com.ipsis.mackit.helper.DyeHelper;

import cpw.mods.fml.common.registry.GameRegistry;

public class MKItems {

	public static void preInit() {

		itemFixerFoamGun = new ItemFixerFoamGun().setUnlocalizedName("fixerFoamGun");
		itemFixerFoamRefill = new ItemFixerFoamRefill().setUnlocalizedName("fixerFoamRefill");
		itemMews = new ItemMews().setUnlocalizedName("mews");
		itemDyeBlank = new ItemDyeBlank().setUnlocalizedName("dyeBlank");
		itemDyeGun = new ItemDyeGun().setUnlocalizedName("dyeGun");
		itemLeech = new ItemLeech().setUnlocalizedName("leech");
		itemTorchPouch = new ItemTorchPouch().setUnlocalizedName("torchPouch");
        itemDivingWeight = new ItemDivingWeight().setUnlocalizedName("divingWeight");
        itemCatCarrier = new ItemCatCarrier().setUnlocalizedName("catCarrier");
		
		int i = 0;
		for (DyeHelper.DyeColor c : DyeHelper.DyeColor.VALID_COLORS) {
			
			dyes[i] = new ItemDyeSponge().setUnlocalizedName("dyeSponge" + c.getName());
			i++;			
		}
		
		GameRegistry.registerItem(itemFixerFoamGun, "item.fixerFoamGun");
		GameRegistry.registerItem(itemFixerFoamRefill, "item.fixerFoamPellet");
		GameRegistry.registerItem(itemMews, "item.mews");
		GameRegistry.registerItem(itemDyeBlank,  "item.dyeBlank");
		GameRegistry.registerItem(itemDyeGun, "item.dyeGun");
		GameRegistry.registerItem(itemLeech, "item.leech");
		GameRegistry.registerItem(itemTorchPouch, "item.torchPouch");
        GameRegistry.registerItem(itemDivingWeight, "item.divingWeight");
        GameRegistry.registerItem(itemCatCarrier, "item.catCarrier");
		
		i = 0;
		for (DyeHelper.DyeColor c : DyeHelper.DyeColor.VALID_COLORS) {
			
			GameRegistry.registerItem(dyes[i], "item.dyeSponge" + c.getName());
			i++;			
		}
	}
	
	public static void initialise() {

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
	public static Item itemMews;
	public static Item itemDyeBlank;
	public static Item itemDyeGun;
	public static Item itemLeech;
	public static Item itemTorchPouch;
    public static Item itemDivingWeight;
    public static Item itemCatCarrier;

	/* Sponges */
	public static Item[] dyes = new Item[DyeHelper.DyeColor.VALID_COLORS.length];
}
