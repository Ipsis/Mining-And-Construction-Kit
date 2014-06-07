package com.ipsis.mackit.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.ipsis.cofhlib.render.IconRegistry;
import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.reference.Gui;
import com.ipsis.mackit.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachineDyeFiller extends BlockFacedMachine {
	
	public BlockMachineDyeFiller(String name) {
		
		super(Material.iron, name);
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
