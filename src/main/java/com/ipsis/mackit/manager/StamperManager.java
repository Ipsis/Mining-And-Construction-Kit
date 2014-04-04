package com.ipsis.mackit.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import com.ipsis.mackit.configuration.ConfigurationSettings;
import com.ipsis.mackit.util.ColourHelper;

public class StamperManager {
	
	private static int MIN_VAL = 0;
	private static int MAX_VAL = 15;
	private static List<Integer> outputs;

	
	public StamperManager() {
		
		outputs = new ArrayList<Integer>();

		
		if (ConfigurationSettings.ENABLE_STAMPER_BLACK_DYE == true)
			outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_BLACK));
		
		outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_RED));
		outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_GREEN));
		outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_BROWN));

		
		if (ConfigurationSettings.ENABLE_STAMPER_BLUE_DYE == true)
			outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_BLUE));
		
		outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_PURPLE));
		outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_CYAN));
		outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_LIGHT_GRAY));
		outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_GRAY));
		outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_PINK));
		outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_LIME));
		outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_YELLOW));
		outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_LIGHT_BLUE));
		outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_MAGENTA));
		outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_ORANGE));
		
		if (ConfigurationSettings.ENABLE_STAMPER_WHITE_DYE == false)
			outputs.add(ColourHelper.getDyeDamage(ColourHelper.DYE_WHITE));	
	}
	
	public Icon getIcon(int idx) {
		
		if (idx < MIN_VAL || idx > MAX_VAL)
			return null;
		
		return Item.dyePowder.getIconFromDamage(idx);
	}
	
	public int getFirst() {
		
		return outputs.get(0);
	}
	
	public int getLast() {
		
		return outputs.get(outputs.size() - 1);
	}
	
	public int getNext(int idx) {
		
		if (idx < MIN_VAL || idx > MAX_VAL)
			return getFirst();
		
		int i = outputs.indexOf(idx);		
		if (i == -1)
			return getFirst();
		
		/* last */
		if (i == outputs.size() - 1)
			return getFirst();
		else
			return outputs.get(i + 1);
			
	}
	
	public int getPrev(int idx) {

		if (idx < MIN_VAL || idx > MAX_VAL)
			return getFirst();
		
		int i = outputs.indexOf(idx);		
		if (i == -1)
			return getFirst();
		
		if (i == 0)
			return getLast();
		else
			return outputs.get(i - 1);
	}
	
	
	public ItemStack getOutput(int idx) {
		
		if (idx < MIN_VAL || idx > MAX_VAL)
			return null;
		
		return new ItemStack(Item.dyePowder, 1, idx);
	}

}
