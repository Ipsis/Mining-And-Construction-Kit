package com.ipsis.mackit.manager;

import net.minecraft.item.ItemStack;

public class PainterRecipe {

	private ItemStack src;
	private ItemStack output;
	private int srcCount;
	
	public PainterRecipe(ItemStack src, int srcCount, ItemStack output) {
		
		this.src = src;
		this.srcCount = srcCount;
		this.output = output;
	}
	
	public ItemStack getSrc() {
		
		return src;
	}
	
	public ItemStack getOutput() {
		
		return output;
	}
	
	public int getSrcCount() {
		
		return srcCount;
	}
}
