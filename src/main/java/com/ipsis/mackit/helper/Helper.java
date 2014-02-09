package com.ipsis.mackit.helper;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;

public class Helper {

	/**
	 * Return the direction that the entity is facing (F value)
	 * @param entity the entity
	 * @return direction (ForgeDirection WEST/NORTH/EAST/SOUTH ony)
	 */
	public static ForgeDirection getFacing(EntityLivingBase entity) {
		
		int v = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		if (v == 0)
			return ForgeDirection.SOUTH;
		else if (v == 1)
			return ForgeDirection.WEST;
		else if (v == 2)
			return ForgeDirection.NORTH;
		else
			return ForgeDirection.EAST;
	}
	
	public static void writeToNBTForgeDirection(String s, ForgeDirection f, NBTTagCompound nbtTagCompound) {
		
		nbtTagCompound.setByte(s, (byte)f.ordinal());
	}
	
	public static ForgeDirection readFromNBTForgeDirection(String s, NBTTagCompound nbtTagCompound) {
		
		return ForgeDirection.getOrientation((int)nbtTagCompound.getByte(s));
	}
	
	/**
	 * Check that the itemstack is of type block.
	 * Does NOT check NBT
	 * @param itemStack the itemStack to compare
	 * @param block the block to compare
	 * @oaram useMetadata check the metadata
	 * @return true if the itemStack is of the same type as the block
	 */
	public static boolean isItemStackOfBlock(ItemStack itemStack, Block block, boolean useMetadata) {
		
		if (itemStack != null && itemStack.stackSize > 0 && itemStack.itemID == block.blockID)
			return true;
		
		return false;
	}
	
	/**
	 * Check that the itemstack is of type block.
	 * Does NOT check NBT
	 * Does NOT check metadata
	 * @param itemStack the itemStack to compare
	 * @param block the block to compare
	 * @return true if the itemStack is of the same type as the block
	 */
	public static boolean isItemStackOfBlock(ItemStack itemStack, Block block) {
		
		return isItemStackOfBlock(itemStack, block, false);
	}
}
