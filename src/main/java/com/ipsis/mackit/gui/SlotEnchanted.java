package com.ipsis.mackit.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotEnchanted extends Slot {
	
	public SlotEnchanted(IInventory inventory, int x, int y, int z) {

		super(inventory, x, y, z);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		
		return itemStack.isItemEnchanted();	
	}

}
