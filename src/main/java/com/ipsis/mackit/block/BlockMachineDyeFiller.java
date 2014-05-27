package com.ipsis.mackit.block;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.reference.Gui;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMachineDyeFiller extends BlockFaced {
	
	public BlockMachineDyeFiller(String name) {
		
		super(Material.iron, name, new String[]{ "machine_bottom", "machine_top", "_front", "machine_side", "machine_side", "machine_side" } );
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if (player.isSneaking())
			return false;
		
		if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TileMachineDyeFiller)			
			player.openGui(MacKit.instance, Gui.DYE_FILLER, world, x, y, z);
		
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TileMachineDyeFiller();
	}
}
