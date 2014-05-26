package com.ipsis.mackit.manager;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class StamperManager {
	
	private static List<ItemStack> outputs;
	
	public StamperManager() {
		
		outputs = new ArrayList<ItemStack>();
		
		/**
		 * TODO disables the dyes we dont want eg. Lapis
		 */
		outputs.add(new ItemStack(Items.dye, 1, 0));	// black
		outputs.add(new ItemStack(Items.dye, 1, 1));	// red
		outputs.add(new ItemStack(Items.dye, 1, 2));	// green
		outputs.add(new ItemStack(Items.dye, 1, 3));	// brown
		outputs.add(new ItemStack(Items.dye, 1, 4));	// blue
		outputs.add(new ItemStack(Items.dye, 1, 5));	// purple
		outputs.add(new ItemStack(Items.dye, 1, 6));	// cyan
		outputs.add(new ItemStack(Items.dye, 1, 7));	// silver
		outputs.add(new ItemStack(Items.dye, 1, 8));	// gray
		outputs.add(new ItemStack(Items.dye, 1, 9));	// pink
		outputs.add(new ItemStack(Items.dye, 1, 10));	// lime
		outputs.add(new ItemStack(Items.dye, 1, 11));	// yellow
		outputs.add(new ItemStack(Items.dye, 1, 12));	// lightBlue
		outputs.add(new ItemStack(Items.dye, 1, 13));	// magenta
		outputs.add(new ItemStack(Items.dye, 1, 14));	// orange
		outputs.add(new ItemStack(Items.dye, 1, 15));	// white
	}
	
	public ItemStack getOutput(int idx) {
		
		if (idx < 0 || idx >= outputs.size())
			return null;
		
		return outputs.get(idx).copy();
	}
	
	public IIcon getIcon(int idx) {
	
		if (idx < 0 || idx >= outputs.size())
			return null;
		
		return Items.dye.getIconFromDamage(idx);
	}
	
	public int getFirstIdx() {
		
		if (outputs.isEmpty())
			return -1;
		
		return 0;
	}
	
	public int getLastIdx() {
		
		if (outputs.isEmpty())
			return -1;
		
		return outputs.size() - 1;
	}
	
	public int getNextIdx(int idx) {
		
		if (outputs.isEmpty())
			return -1;
		
		int next = idx + 1;
		if (next > outputs.size() - 1)
			return getFirstIdx();

		return next;
	}
	
	public int getPrevIdx(int idx) {
		
		if (outputs.isEmpty())
			return -1;
		
		int prev = idx - 1;
		if (prev < 0)
			return outputs.size() - 1;
		
		return prev;		
	}	
}
