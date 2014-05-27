package com.ipsis.mackit.item;

public class ItemDyeGun extends ItemMK {

	private static final int TANK_CAPACITY = 1000; // mB
	
	public ItemDyeGun() {
		
		super();
		this.setMaxStackSize(1);
		this.setMaxDamage(TANK_CAPACITY);
	}
}
