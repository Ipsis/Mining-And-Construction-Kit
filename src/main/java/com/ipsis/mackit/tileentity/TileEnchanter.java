package com.ipsis.mackit.tileentity;

import java.util.List;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEnchanter extends TileEntity implements IInventory {

	private ItemStack[] inventory;
	private Random rand = new Random();
	
	public static final int INVENTORY_SIZE = 2;
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_OUTPUT = 1;
	
	public TileEnchanter() {
		
		inventory = new ItemStack[INVENTORY_SIZE];
	}

	@Override
	public int getSizeInventory() {

		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex) {

		return inventory[slotIndex];
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int amount) {

		ItemStack itemStack = getStackInSlot(slotIndex);
		if (itemStack != null)
		{
			if (itemStack.stackSize <= amount)
			{
				setInventorySlotContents(slotIndex, null);
			}
			else
			{
				itemStack = itemStack.splitStack(amount);
				if (itemStack.stackSize == 0)
				{
					setInventorySlotContents(slotIndex, null);
				}
			}
		}
		
		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {

		ItemStack itemStack = getStackInSlot(slotIndex);
		if (itemStack != null)
		{
			setInventorySlotContents(slotIndex, null);
		}
		return itemStack;
	}

	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {

		inventory[slotIndex] = itemStack;
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
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
	public void openChest() {

		
	}

	@Override
	public void closeChest() {

		
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {

		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {

		super.readFromNBT(nbtTagCompound);
		
        NBTTagList tagList = nbtTagCompound.getTagList("Items");
        inventory = new ItemStack[this.getSizeInventory()];
        for (int idx = 0; idx < tagList.tagCount(); idx++)
        {
            NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(idx);
            byte slotIndex = tagCompound.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < inventory.length)
            {
                inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {

		super.writeToNBT(nbtTagCompound);
		
		NBTTagList tagList = new NBTTagList();
		for (int idx = 0; idx < inventory.length; idx++)
		{
			if (inventory[idx] != null)
			{
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot",  (byte)idx);
				inventory[idx].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);;
			}
		}
		nbtTagCompound.setTag("Items", tagList);
	}
	
	/*
	 * Try to enchant the item at the requested level
	 * Pretty much the same as vanilla enchanting as it is supposed to work the same way!
	 */
	public boolean enchantItem(EntityPlayer player, int levels) {
		
		ItemStack sourceItemStack = inventory[SLOT_INPUT];
		
		if (sourceItemStack == null || levels <= 0)
			return false;
		
		if (player.experienceLevel < levels && !player.capabilities.isCreativeMode)
			return false;
		
		if (!worldObj.isRemote)
		{
			List list = EnchantmentHelper.buildEnchantmentList(this.rand, sourceItemStack, levels);
			boolean isBook = sourceItemStack.itemID == Item.book.itemID;
			
			if (list != null)
			{
				/* Can enchant this item */
				player.addExperienceLevel(-levels);
				
				/* Update the item id for a book */
				if (isBook)
					sourceItemStack.itemID = Item.enchantedBook.itemID;

				/*
				 * Books only get one enchant, other items can get multiple
				 */
                for (int k = 0; k < list.size(); ++k)
                {
                    EnchantmentData enchant = (EnchantmentData)list.get(k);
                    
                    if (isBook)
                    {
                    	Item.enchantedBook.addEnchantment(sourceItemStack, enchant);
                    	break;
                    }
                    
                    sourceItemStack.addEnchantment(enchant.enchantmentobj, enchant.enchantmentLevel);                    
                }                
			}			
		}	
		
		return true;
	}
}
