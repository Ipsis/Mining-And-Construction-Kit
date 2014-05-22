package com.ipsis.mackit.gui;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/*
 * Slot can only hold enchantable items with no enchantments.
 */

public class SlotEnchantable extends Slot {

	public SlotEnchantable(IInventory inventory, int x, int y, int z) {

		super(inventory, x, y, z);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		
		/* allow a stack of unenchanted books */
		if (itemStack.getItem() == Items.book && !itemStack.isItemEnchanted())
				return true;

		/* Block anything that is not  enchantable */
		if (!itemStack.isItemEnchantable())
			return false;
		
		/* Block anything that is already enchanted */
		if (itemStack.isItemEnchanted())
			return false;
		
		return true;
	}

}
