package com.ipsis.mackit.block;

import com.ipsis.cofhlib.util.EntityHelper;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
	
	@Override
	public boolean isOpaqueCube() {

		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {

		if (world.isRemote)
			return;
				
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileDyeLeech)
			((TileDyeLeech)te).setBiome();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x,	int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {

		if (entityPlayer.isSneaking())
			return false;
		
		if (!world.isRemote) {
			
			TileEntity te = world.getTileEntity(x, y, z);
			if (te != null && te instanceof TileDyeLeech) {
				((TileDyeLeech) te).tryProduce();
			}
		}
		
		return true;
	}
}
