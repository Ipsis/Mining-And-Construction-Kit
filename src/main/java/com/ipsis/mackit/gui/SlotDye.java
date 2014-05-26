package com.ipsis.mackit.gui;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDye extends Slot {

	public SlotDye(IInventory inventory, int x, int y, int z) {

		super(inventory, x, y, z);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		
		return itemStack.getItem() == Items.dye;
	}
}
