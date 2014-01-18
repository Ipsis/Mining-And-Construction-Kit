package com.ipsis.mackit.item;

import com.ipsis.mackit.block.ModBlocks;
import com.ipsis.mackit.lib.Strings;

import net.minecraft.item.ItemMultiTextureTile;

public class ItemBlockFixedEarth extends ItemMultiTextureTile {
	
	public ItemBlockFixedEarth(int id) {
		
		super(id, ModBlocks.fixedEarth, Strings.FIXED_EARTH_NAMES);
	}
}
