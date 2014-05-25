package com.ipsis.mackit.block;

import net.minecraft.block.material.Material;

public class BlockMachinePainter extends BlockFaced {

	public BlockMachinePainter(String name) {
		
		super(Material.iron, name, new String[]{ "machine_bottom", "machine_top", "_front", "machine_side", "machine_side", "machine_side" } );
	}
}
