package com.ipsis.mackit.util;

import net.minecraft.item.ItemStack;

/*
 * Selected methods from CoFHLib by KingLemming
 */

public class ItemHelper {

	/**
	 * Get a hashcode based on the ItemStack's ID and Metadata. As both of these
	 * are shorts, this should be collision-free for non-NBT sensitive
	 * ItemStacks.
	 * 
	 * @param stack
	 *            The ItemStack to get a hashcode for.
	 * @return The hashcode.
	 */
	public static int getHashCode(ItemStack stack) {

		return stack.getItemDamage() | stack.itemID << 16;
	}
}
