package com.ipsis.mackit.item;

import com.ipsis.mackit.lib.Strings;

public class ItemFixerFoamPellet extends ItemMK {

	public ItemFixerFoamPellet(int id) {
		
		super(id);
		this.setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.FIXER_FOAM_PELLET_NAME);
		this.setMaxStackSize(8);
	}
}
