package com.ipsis.mackit.manager;

import net.minecraft.item.ItemStack;

import com.ipsis.mackit.block.machinesm.IMachineRecipe;

public class PainterRecipe implements IMachineRecipe {
	
	private static final int PURE_FLUID_AMOUNT = 100;
	private static final int RECIPE_ENERGY = 40;

	private ItemStack srcDye;
	private ItemStack srcItem;
	private ItemStack output;
	private int srcCount;
	
	public PainterRecipe(ItemStack srcDye, ItemStack srcItem, int srcCount, ItemStack output) {
		
		this.srcDye = srcDye;
		this.srcItem = srcItem;
		this.srcCount = srcCount;
		this.output = output;
	}
	
	public ItemStack getSrcDye() {
		
		return srcDye;
	}
	
	public ItemStack getSrcItem() {
		
		return srcItem;
	}
	
	public ItemStack getOutput() {
		
		return output;
	}
	
	public int getSrcCount() {
		
		return srcCount;
	}

	@Override
	public int getEnergy() {

		return RECIPE_ENERGY;
	}
}
