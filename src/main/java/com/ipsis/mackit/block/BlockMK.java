package com.ipsis.mackit.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.ipsis.mackit.MacKit;

/* default getUnlocalized name returns "tile.mackit:<name>"	 */
public class BlockMK extends Block {

	public BlockMK(int id, Material material) {
		
		super(id, material);
		super.setCreativeTab(MacKit.tabsMacKit);
	}
	
	
}
