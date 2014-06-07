package com.ipsis.mackit.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ipsis.mackit.creativetab.CreativeTab;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.manager.MKManagers;
import com.ipsis.mackit.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBeaverBlock extends BlockContainer {

	private String iconName;
	
	public BlockBeaverBlock(String name) {
		
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
	private IIcon modeIcons[];
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		blockIcon = iconRegister.registerIcon(Reference.MOD_ID + ":" + iconName);
		
		modeIcons = new IIcon[3];
		modeIcons[0] = iconRegister.registerIcon(Reference.MOD_ID + ":" + iconName + "_surface");
		modeIcons[1] = iconRegister.registerIcon(Reference.MOD_ID + ":" + iconName + "_cube");
		modeIcons[2] = iconRegister.registerIcon(Reference.MOD_ID + ":" + iconName + "_tower");		
	}
	
	@Override
	public IIcon getIcon(int side, int metadata) {

		/**
		* As far as I can work out, this will only be called for inventory icons as we have getBlockTexture override.
		* Side 3 is the standard side for you front texture in this situation.
		*/
		if (side == 0 || side == 1)
			return blockIcon;
		else
			return modeIcons[0];
	}
	
	@Override
	public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {

		if (side == 0 || side == 1) {
			return blockIcon;
		} else {
		
			TileEntity te = iblockaccess.getTileEntity(x, y, z);
			if (te != null && te instanceof TileBeaverBlock) {
				TileBeaverBlock bte = (TileBeaverBlock)te;
				return modeIcons[bte.getMode()];
			} else {
				return blockIcon;
			}
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {

		return new TileBeaverBlock();
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block id) {

		if (!world.isRemote && world.isBlockIndirectlyGettingPowered(x, y, z)) {
			TileEntity te = world.getTileEntity(x, y, z);
			if (te != null && te instanceof TileBeaverBlock)
				((TileBeaverBlock)te).setRunning();
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x,	int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {

		if (entityPlayer.isSneaking())
			return false;
		
		if (!world.isRemote) {
			
			TileEntity te = world.getTileEntity(x, y, z);
			if (te != null && te instanceof TileBeaverBlock) {
				((TileBeaverBlock) te).setNextMode();
			}
		}
		
		return true;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		
		return false;
	}
	
}
