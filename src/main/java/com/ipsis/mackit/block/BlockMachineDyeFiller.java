package com.ipsis.mackit.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMachineDyeFiller extends BlockFaced {
	
	public BlockMachineDyeFiller(String name) {
		
		super(Material.iron, name, new String[]{ "machine_bottom", "machine_top", "_front", "machine_side", "machine_side", "machine_side" } );
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TileMachineDyeFiller();
	}
}
