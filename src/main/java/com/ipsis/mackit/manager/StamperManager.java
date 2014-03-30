package com.ipsis.mackit.manager;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class StamperManager {
	
	private static int COUNT = 16;
	
	
	public StamperManager() {		
	}
	
	public Icon getIcon(int idx) {
		
		if (idx < 0 || idx >= COUNT)
			return null;
		
		return Item.dyePowder.getIconFromDamage(idx);
	}
	
	public int getPrev(int idx) {
		
		if (idx == 0)
			return (COUNT - 1);
		
		return idx - 1;
	}
	
	public int getNext(int idx) {
		
		if (idx == COUNT - 1)
			return 0;
		
		return idx + 1;
	}
	
	public ItemStack getOutput(int idx) {
		
		if (idx < 0 || idx >= COUNT)
			return null;
		
		return new ItemStack(Item.dyePowder, 1, idx);
	}

}
