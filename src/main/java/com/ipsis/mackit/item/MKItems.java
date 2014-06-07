package com.ipsis.mackit.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

import com.ipsis.cofhcore.item.ItemBucket;
import com.ipsis.cofhcore.util.fluid.BucketHandler;
import com.ipsis.mackit.block.MKBlocks;
import com.ipsis.mackit.creativetab.CreativeTab;
import com.ipsis.mackit.fluid.MKFluids;
import com.ipsis.mackit.helper.DyeHelper;
import com.ipsis.mackit.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

public class MKItems {
	

	public static void preInit() {
		
		/* buckets */
		itemBucket = (ItemBucket) new ItemBucket(Reference.MOD_ID).setUnlocalizedName("bucket").setCreativeTab(CreativeTab.MK_TAB);
		bucketDyeRed = itemBucket.addItem(0, "bucketDyeRed", 1);
		bucketDyeYellow = itemBucket.addItem(1, "bucketDyeYellow", 1);
		bucketDyeBlue = itemBucket.addItem(2, "bucketDyeBlue", 1);
		bucketDyeWhite = itemBucket.addItem(3, "bucketDyeWhite", 1);
		bucketDyePure = itemBucket.addItem(4, "bucketDyePure", 1);

		
		itemFixerFoamGun = new ItemFixerFoamGun().setUnlocalizedName("fixerFoamGun");
		itemFixerFoamRefill = new ItemFixerFoamRefill().setUnlocalizedName("fixerFoamRefill");
		itemMews = new ItemMews().setUnlocalizedName("mews");
		itemDyeBlank = new ItemDyeBlank().setUnlocalizedName("dyeBlank");
		itemDyeGun = new ItemDyeGun().setUnlocalizedName("dyeGun");
		itemLeech = new ItemLeech().setUnlocalizedName("leech");
		
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
		
		i = 0;
		for (DyeHelper.DyeColor c : DyeHelper.DyeColor.VALID_COLORS) {
			
			GameRegistry.registerItem(dyes[i], "item.dyeSpong" + c.getName());
			i++;			
		}
	}
	
	public static void initialise() {
		
		BucketHandler.registerBucket(MKBlocks.blockFluidDyeRed, 0, bucketDyeRed);
		BucketHandler.registerBucket(MKBlocks.blockFluidDyeYellow, 0, bucketDyeYellow);
		BucketHandler.registerBucket(MKBlocks.blockFluidDyeBlue, 0, bucketDyeBlue);
		BucketHandler.registerBucket(MKBlocks.blockFluidDyeWhite, 0, bucketDyeWhite);
		BucketHandler.registerBucket(MKBlocks.blockFluidDyePure, 0, bucketDyePure);
		
		FluidContainerRegistry.registerFluidContainer(MKFluids.fluidDyeRed, bucketDyeRed, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(MKFluids.fluidDyeYellow, bucketDyeYellow, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(MKFluids.fluidDyeBlue, bucketDyeBlue, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(MKFluids.fluidDyeWhite, bucketDyeWhite, FluidContainerRegistry.EMPTY_BUCKET);
		FluidContainerRegistry.registerFluidContainer(MKFluids.fluidDyePure, bucketDyePure, FluidContainerRegistry.EMPTY_BUCKET);
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
	

	/* Sponges */
	public static Item[] dyes = new Item[DyeHelper.DyeColor.VALID_COLORS.length];
	
	/* buckets */
	public static ItemBucket itemBucket;
	public static ItemStack bucketDyeRed;
	public static ItemStack bucketDyeYellow;
	public static ItemStack bucketDyeBlue;
	public static ItemStack bucketDyeWhite;
	public static ItemStack bucketDyePure;
}
