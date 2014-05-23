package com.ipsis.mackit.manager;

import net.minecraft.item.ItemStack;

import com.ipsis.mackit.block.machinesm.IMachineRecipe;

public class SqueezerRecipe implements IMachineRecipe {

	private ItemStack source;
	private ItemStack dye;

	public SqueezerRecipe(ItemStack source, ItemStack dye) {

		this.source = source;
		this.dye = dye;
	}

	public ItemStack getDye() {

		return dye;
	}
	
	public DyeRecipe getDyeRecipe() {
		
		return MKManagers.dyeMgr.getRecipe(dye);
	}

	@Override
	public String toString() {

		return String.format("SqueezerRecipe: source=%s %d dye=%s", source, dye.stackSize, dye);
	}

	@Override
	public int getEnergy() {

		return 1000;
	}
	
	@Override
	public int getEnergyTick() {

		return 10;
	}
}
