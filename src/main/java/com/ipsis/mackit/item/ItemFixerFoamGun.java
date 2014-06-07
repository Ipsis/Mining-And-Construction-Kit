package com.ipsis.mackit.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.ipsis.cofhlib.util.BlockCoord;
import com.ipsis.cofhlib.util.EntityHelper;
import com.ipsis.mackit.block.MKBlocks;
import com.ipsis.mackit.helper.RotateHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFixerFoamGun extends ItemMK {

	/* Blocks to change when facing east (+x, +z) */
	private static BlockCoord blocks[] = {
		new BlockCoord(0, 1, -1), new BlockCoord(0, 1, 0), new BlockCoord(0, 1, 1),
		new BlockCoord(0, 0, -1), new BlockCoord(0, 0, 0), new BlockCoord(0, 0, 1),
		new BlockCoord(0, -1, -1), new BlockCoord(0, -1, 0), new BlockCoord(0, -1, 1),
		
		new BlockCoord(1, 1, -1), new BlockCoord(1, 1, 0), new BlockCoord(1, 1, 1),
		new BlockCoord(1, 0, -1), new BlockCoord(1, 0, 0), new BlockCoord(1, 0, 1),
		new BlockCoord(1, -1, -1), new BlockCoord(1, -1, 0), new BlockCoord(1, -1, 1),
		
		new BlockCoord(2, 1, -1), new BlockCoord(2, 1, 0), new BlockCoord(2, 1, 1),
		new BlockCoord(2, 0, -1), new BlockCoord(2, 0, 0), new BlockCoord(2, 0, 1),
		new BlockCoord(2, -1, -1), new BlockCoord(2, -1, 0), new BlockCoord(2, -1, 1),
	};
	
	public ItemFixerFoamGun()  {
		
		super();
		this.setMaxStackSize(1);
		this.setMaxDamage(64);
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack itemStack,
			EntityPlayer entityPlayer, World world, int x, int y,
			int z, int hitSide, float hitX, float hitY, float hitZ) {
		
			if (world.isRemote)
				return false;
			
			if (!entityPlayer.capabilities.isCreativeMode && itemStack.getItemDamage() == 64)
				return false;					
						
			Block b = world.getBlock(x, y, z);
			if (b == null)
				return false;
			
			if (b != Blocks.sand && b != Blocks.gravel)
				return false;
			
		    if (!entityPlayer.capabilities.isCreativeMode)
				itemStack.setItemDamage(itemStack.getItemDamage() + 1);
				
			applyFoam(entityPlayer, world, x, y, z);
			return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack,	EntityPlayer entityPlayer, List info, boolean useExtraInformation) {
		
		info.add("Charges Left " + (itemStack.getMaxDamage() - itemStack.getItemDamage()));
	}
	
	private void changeBlock(World world, BlockCoord p) {
	
		Block b = world.getBlock(p.x, p.y, p.z);
		
		if (b == null)
			return;
		
		if (b == Blocks.sand)
			world.setBlock(p.x, p.y, p.z, MKBlocks.fixedSand);
		else if (b == Blocks.gravel)
			world.setBlock(p.x, p.y, p.z, MKBlocks.fixedGravel);
	}
	
	private void applyFoam(EntityPlayer entityPlayer, World world, int x, int y, int z) {
		
		ForgeDirection d = EntityHelper.getEntityFacingForgeDirection(entityPlayer);
		d = d.getOpposite();
		
		/**
		 * Modify a 3x3x3 area in the direction the player is facing
		 */
		
		for (BlockCoord p : blocks) {
			BlockCoord tp = RotateHelper.rotatePointXZ(ForgeDirection.EAST, d, p);
			changeBlock(world, new BlockCoord(x + tp.x, y + tp.y, z + tp.z));
		}
		
		return;
	}
}
