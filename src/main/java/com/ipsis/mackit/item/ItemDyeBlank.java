package com.ipsis.mackit.item;

import com.ipsis.mackit.lib.Strings;

public class ItemDyeBlank extends ItemMK {
	
	public ItemDyeBlank(int id) {
		
		super(id);
		this.setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.DYE_BLANK_NAME);
		this.setMaxStackSize(64);
	}

}
