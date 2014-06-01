package com.ipsis.mackit.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Colour out of space
 *
 */
public class BlockDyeLeech extends BlockFaced {

	public BlockDyeLeech(String name) {
		
		super(Material.iron, name, new String[]{ "dyeLeech", "dyeLeech_top", "dyeLeech", "dyeLeech", "dyeLeech", "dyeLeech" } );
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TileDyeLeech();
	}
}
