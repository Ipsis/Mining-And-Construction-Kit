package com.ipsis.mackit.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

import com.ipsis.mackit.block.MKBlocks;
import com.ipsis.mackit.creativetab.CreativeTab;
import com.ipsis.mackit.fluid.MKFluids;

import cpw.mods.fml.common.registry.GameRegistry;

public class MKItems {
	

	public static void preInit() {
		
		/* buckets */
		//itemBucket = (ItemBucket) new ItemBucket("thermalfoundation").setUnlocalizedName("bucket").setCreativeTab(CreativeTab.MK_TAB);

		//bucketDyeRed = itemBucket.addItem(0, "bucketDyeRed", 1);
		
		
		itemFixerFoamGun = new ItemFixerFoamGun().setUnlocalizedName("fixerFoamGun");
		itemFixerFoamRefill = new ItemFixerFoamRefill().setUnlocalizedName("fixerFoamRefill");
		itemMews = new ItemMews().setUnlocalizedName("mews");
		itemDyeBlank = new ItemDyeBlank().setUnlocalizedName("dyeBlank");
		
		GameRegistry.registerItem(itemFixerFoamGun, "item.fixerFoamGun");
		GameRegistry.registerItem(itemFixerFoamRefill, "item.fixerFoamPellet");
		GameRegistry.registerItem(itemMews, "item.mews");
		GameRegistry.registerItem(itemDyeBlank,  "item.dyeBlank");;
	}
	
	public static void initialise() {
		
		//BucketHandler.registerBucket(MKBlocks.blockFluidDyeRed, 0, bucketDyeRed);
		
		//FluidContainerRegistry.registerFluidContainer(MKFluids.fluidDyeRed, bucketDyeRed, FluidContainerRegistry.EMPTY_BUCKET);
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
	
	/* buckets */
	public static ItemBucket itemBucket;
	public static ItemStack bucketDyeRed;
}
