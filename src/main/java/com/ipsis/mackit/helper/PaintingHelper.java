package com.ipsis.mackit.helper;

import com.ipsis.mackit.manager.MKManagers;

import net.minecraft.item.ItemStack;

/**
 * 
 * Helper class to identify recipes where we only want
 * 2 items where
 * 
 * 1 dye item
 * 1 or more items of the same type
 */
public class PaintingHelper {
	
	private int itemCount;
	private boolean valid;
	private ItemStack inputDye;
	private ItemStack inputItem;
	
	public PaintingHelper() {
		
		reset();
	}
	
	public void reset() {
		
		itemCount = 0;
		valid = true;
		inputDye = null;
		inputItem = null;
	}
	
	public boolean verify(ItemStack currItem) {
		
		if (MKManagers.dyeOreDictHelper.isDye(currItem)) {
			
			if (inputDye == null) 
				inputDye = currItem.copy();
			else
				valid = false;
		} else {
			
			if (inputItem == null) {
				
				inputItem = currItem.copy();
				itemCount = 1;
			} else {
				
				if (inputItem.isItemEqual(currItem))				
					itemCount++;
				else
					valid = false;
			}
		}
		
		return this.valid;
	}
	
	public int getItemCount() {
		
		return itemCount;
	}
	
	public ItemStack getInputItem() {
		
		return inputItem;
	}
	
	public ItemStack getInputDye() {
		
		return inputDye;
	}

}
