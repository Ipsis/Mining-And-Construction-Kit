package com.ipsis.mackit.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityInventoryMK extends TileEntity implements IInventory {

	/* exists, but has no items in it */
	public ItemStack[] inventory = new ItemStack[0];
	
	@Override
	public void closeInventory() {
		
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {

		if (inventory[slot] == null)
			return null;
		
		/* cap at how much is in the slot */
		if (inventory[slot].stackSize <= count)
			count = inventory[slot].stackSize;
	
		ItemStack s = inventory[slot].splitStack(count);
		if (inventory[slot].stackSize <= 0)
			inventory[slot] = null;
		
		return s;
	}

	@Override
	public String getInventoryName() {

		return null;
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public int getSizeInventory() {

		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {

		return inventory[slot];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {

		ItemStack s = inventory[slot];
		if (s != null)
			inventory[slot] = null;
		
		return s;
	}

	@Override
	public boolean hasCustomInventoryName() {

		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {

		return true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {

		return true;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {

		inventory[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();

	}

}
