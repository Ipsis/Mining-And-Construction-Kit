package com.ipsis.mackit.manager;

import net.minecraft.item.ItemStack;

public class SqueezerRecipe {

	private ItemStack source;
	private ItemStack dye;
	
	public SqueezerRecipe(ItemStack source, ItemStack dye) {
		
		this.source = source;
		this.dye = dye;
	}
	
	public ItemStack getDye() {
		
		return dye;
	}
	
	@Override
	public String toString() {

		return String.format("SqueezerRecipe: source=%s %d dye=%s", source, dye.stackSize, dye);
	}
}
