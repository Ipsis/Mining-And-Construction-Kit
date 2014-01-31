package com.ipsis.mackit.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public abstract class TileMachineInventory extends TileEntity implements ISidedInventory {

	private ItemStack[] inventory;
	
	public TileMachineInventory() {
		
		inventory = new ItemStack[getSizeInventory()];
	}
	
	private boolean isSlotValidForInventory(int slot) {
		
		if (slot >= 0 && slot < inventory.length)
			return true;
		
		return false;
	}
	
	/*
	 * TileEntity
	 */
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		
		super.readFromNBT(compound);
		
		 NBTTagList items = compound.getTagList("Items");
         for (int i = 0; i < items.tagCount(); i++) {
                 NBTTagCompound item = (NBTTagCompound)items.tagAt(i);
                 int slot = item.getByte("Slot");
                 if (slot >= 0 && slot < getSizeInventory()) {
                         setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
                 }
         }		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		
		super.writeToNBT(compound);
		
		NBTTagList items = new NBTTagList();                
        for (int i = 0; i < getSizeInventory(); i++) {
                ItemStack stack = getStackInSlot(i);
                
                if (stack != null) {
                        NBTTagCompound item = new NBTTagCompound();
                        item.setByte("Slot", (byte)i);
                        stack.writeToNBT(item);
                        items.appendTag(item);
                        
                }
        }                

        compound.setTag("Items", items);  
	}
	
	/* 
	 * IInventory
	 */
	@Override
	public int getSizeInventory() {
		
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		
		if (isSlotValidForInventory(slot))
			return inventory[slot];
		
		return null;			
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		
        ItemStack itemstack = getStackInSlot(slot);
        
        if (itemstack != null) {
                if (itemstack.stackSize <= count) {
                        setInventorySlotContents(slot, null);
                } else {
                        itemstack = itemstack.splitStack(count);
                        onInventoryChanged();
                }
        }
        
        return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		
		ItemStack itemstack = getStackInSlot(slot);
        setInventorySlotContents(slot, null);
        return itemstack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		
		if (isSlotValidForInventory(slot)) {
			inventory[slot] = itemstack;
			onInventoryChanged();
		}
	}

	@Override
	public String getInvName() {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInvNameLocalized() {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {

		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5)  < 64;
	}

	@Override
	public void openChest() { }

	@Override
	public void closeChest() { }

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		
		if (isSlotValidForInventory(slot))
			return true;

		return false;
	}

	/*
	 * ISidedInventory
	 */
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		
		// TODO Auto-generated method stub
		return false;
	}
}
