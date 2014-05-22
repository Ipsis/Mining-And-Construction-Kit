package com.ipsis.mackit.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.creativetab.CreativeTab;
import com.ipsis.mackit.reference.Gui;
import com.ipsis.mackit.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPortaChant extends BlockContainer {
	
	private String iconName;
	
	public BlockPortaChant(String name) {
		
		super(Material.iron);
		this.setCreativeTab(CreativeTab.MK_TAB);
		this.setBlockName(name);
	}
	
	@Override
	public Block setBlockName(String name) {

		iconName = name;
		name = Reference.MOD_ID + ":" + name;
		return super.setBlockName(name);
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon icons[];
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		blockIcon = iconRegister.registerIcon(Reference.MOD_ID + ":" + iconName);
		
		icons = new IIcon[2];
		icons[0] = iconRegister.registerIcon(Reference.MOD_ID + ":" + iconName + "_front");
		icons[1] = iconRegister.registerIcon(Reference.MOD_ID + ":" + iconName + "_top");
	}
	
	@Override
	public IIcon getIcon(int side, int metadata) {

		/**
		* As far as I can work out, this will only be called for inventory icons as we have getBlockTexture override.
		* Side 3 is the standard side for you front texture in this situation.
		*/
		if (side == 3)
			return icons[0];
		else if (side == 0)
			return icons[1];
		else
			return blockIcon;
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
	public TileEntity createNewTileEntity(World arg0, int arg1) {

		return new TilePortaChant();
	}

}
