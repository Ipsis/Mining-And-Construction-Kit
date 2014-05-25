package com.ipsis.mackit.helper;

import com.ipsis.mackit.manager.MKManagers;

import net.minecraft.item.ItemStack;

public class PainterHelper {
	
	private int itemCount;
	private boolean valid;
	private ItemStack inputDye;
	private ItemStack inputItem;
	
	public PainterHelper() {
		
		reset();
	}
	
	public void reset() {
		
		itemCount = 0;
		valid = true;
		inputDye = null;
		inputItem = null;
	}
	
	public boolean verify(ItemStack currItem) {
		
		if (MKManagers.squeezerMgr.isDye(currItem)) {
			
			if (inputDye == null) 
				inputDye = currItem;
			else
				valid = false;
		} else {
			
			if (inputItem == null) {
				
				inputItem = currItem;
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
