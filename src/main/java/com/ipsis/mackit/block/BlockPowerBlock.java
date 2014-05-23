package com.ipsis.mackit.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPowerBlock extends BlockFaced {

	public BlockPowerBlock(String name) {
		
		super(Material.iron, name, new String[]{ "", "", "", "", "", "" } );
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TilePowerBlock();
	}

}
