package com.ipsis.mackit.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.reference.Gui;

public class BlockMachineSqueezer extends BlockFacedMachine {
	
	public BlockMachineSqueezer(String name) {
		
		super(Material.iron, name);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if (player.isSneaking())
			return false;
		
		if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TileMachineSqueezer)			
			player.openGui(MacKit.instance, Gui.SQUEEZER, world, x, y, z);
		
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TileMachineSqueezer();
	}

}
