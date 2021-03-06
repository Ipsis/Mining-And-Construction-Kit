package com.ipsis.mackit.block;

import com.ipsis.mackit.helper.LogHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

public abstract class TileInventory extends TileEntity implements IInventory {

	/* exists, but has no items in it */
	public ItemStack[] inventory = new ItemStack[0];
		
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int slot = nbttagcompound1.getByte("Slot") & 0xff;
			if (slot >= 0 && slot < inventory.length) {
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(nbttagcompound1));
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {

		super.writeToNBT(nbttagcompound);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inventory.length; i++) {
			ItemStack stack = getStackInSlot(i);

			if (stack != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				stack.writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}
	
	@Override
	public void closeInventory() {
		
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {

		if (inventory[slot] == null)
			return null;
		
		if (inventory[slot].stackSize <= count) {
			ItemStack s = inventory[slot];
			inventory[slot] = null;
			markDirty();
			return s;
		}
		
		ItemStack s = inventory[slot].splitStack(count);
		if (inventory[slot].stackSize <= 0)
			inventory[slot] = null;
		
		markDirty();
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
		markDirty();
	}

}
