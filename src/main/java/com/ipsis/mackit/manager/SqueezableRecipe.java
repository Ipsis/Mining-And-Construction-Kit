package com.ipsis.mackit.manager;

import net.minecraft.item.ItemStack;

public class SqueezableRecipe {

	private ItemStack source;
	private ItemStack dye;
	
	public SqueezableRecipe(ItemStack source, ItemStack dye) {
		
		this.source = source;
		this.dye = dye;
	}
	
	public ItemStack getDye() {
		
		return dye;
	}
}
