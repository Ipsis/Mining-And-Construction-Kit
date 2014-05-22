package com.ipsis.mackit.block;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.reference.Gui;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPortaChant extends BlockFaced {
	
	public BlockPortaChant(String name) {
		
		super(Material.iron, name,
				new String[]{ "", "_top", "_front", "", "", "" }
		);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if (player.isSneaking())
			return false;
		
		if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TilePortaChant)			
			player.openGui(MacKit.instance, Gui.PORTA_CHANT, world, x, y, z);
		
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TilePortaChant();
	}

}
