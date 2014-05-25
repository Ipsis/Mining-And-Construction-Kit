package com.ipsis.mackit.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ipsis.mackit.item.MKItems;

public class SlotDyeBlank extends Slot {

	public SlotDyeBlank(IInventory inventory, int x, int y, int z) {

		super(inventory, x, y, z);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		
		return itemStack.getItem() == MKItems.itemDyeBlank;
	}
}
