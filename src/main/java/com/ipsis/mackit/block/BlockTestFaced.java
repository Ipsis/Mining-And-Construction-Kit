package com.ipsis.mackit.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTestFaced extends BlockFaced {
	
	public BlockTestFaced(Material m, String name) {
		
		super(m, name);
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TileTestFaced();
	}

}
