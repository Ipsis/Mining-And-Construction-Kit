package com.ipsis.mackit.item;

import net.minecraft.item.ItemMultiTextureTile;

import com.ipsis.mackit.block.ModBlocks;
import com.ipsis.mackit.lib.Strings;

public class ItemBlockMachine extends ItemMultiTextureTile {
	
	public ItemBlockMachine(int id) {
		
		super (id, ModBlocks.machineBlock, Strings.MACHINE_NAMES);
	}

}
