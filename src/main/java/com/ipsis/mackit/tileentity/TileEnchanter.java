package com.ipsis.mackit.tileentity;

import java.util.List;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.ipsis.mackit.client.gui.inventory.GuiEnchanter;
import com.ipsis.mackit.network.PacketHandler;

import cpw.mods.fml.common.network.Player;

public class TileEnchanter extends TileEntity implements IInventory, ISidedInventory {

	private ItemStack[] inventory;
	private Random rand = new Random();
	
	public enum OutputSlot {
		INV_SLOT_OUTPUT1(1),
		INV_SLOT_OUTPUT2(2),
		INV_SLOT_OUTPUT3(3),
		INV_SLOT_OUTPUT4(4),
		INV_SLOT_OUTPUT5(5),
		INV_SLOT_OUTPUT6(6);
		
		private final int slot;
		OutputSlot(int slot) {
			this.slot = slot;
		}
		
		public int slot() {
			return slot;
		}
	}
	
	public static final int INVENTORY_SIZE = OutputSlot.values().length + 1;
	public static final int INV_SLOT_INPUT = 0;

	public static final byte MIN_ENCHANT_LEVEL = 1;
	public static final byte MAX_ENCHANT_LEVEL = 30;

	private byte enchantLevel;
	private boolean canEnchant;	
	
	public TileEnchanter() {
		
		inventory = new ItemStack[INVENTORY_SIZE];
		enchantLevel = 1;
		canEnchant = false;
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
        
        enchantLevel = nbtTagCompound.getByte("EnchantLevel");
        updateCanEnchant();
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
		nbtTagCompound.setByte("EnchantLevel", enchantLevel);
	}
	
	public byte getEnchantLevel() {
		return enchantLevel;
	}
	
	/* Gui update only */
	public void setEnchantLevel(byte level) {
		enchantLevel = level;
	}	
	
	public void incEnchantLevel() {
		enchantLevel++;
		if (enchantLevel > MAX_ENCHANT_LEVEL)
			enchantLevel = MAX_ENCHANT_LEVEL;
	}
	
	public void decEnchantLevel() {
		enchantLevel--;
		if (enchantLevel < MIN_ENCHANT_LEVEL)
			enchantLevel = MIN_ENCHANT_LEVEL;
	}
	
	public boolean getCanEnchant() {
		return canEnchant;
	}
	
	/* Gui update only */
	public void setCanEnchant(boolean enchant) {
		canEnchant = enchant;
	}
	
	private EntityPlayer getEnchantingPlayer() {
		return this.worldObj.getClosestPlayer((double)((float)this.xCoord + 0.5F), (double)((float)this.yCoord + 0.5F), (double)((float)this.zCoord + 0.5F), 3.0D);
	}
	
	public void enchantItem(Player player) {

		if (worldObj.isRemote)
			return;		
			
		EntityPlayer entityPlayer = (EntityPlayer)player;
		
		/* validate slots contents first */
		updateCanEnchant();
		if (!canEnchant)
			return;
		
		if (entityPlayer.experienceLevel < enchantLevel && !entityPlayer.capabilities.isCreativeMode)
			return;
		
		int oslot = getOutputSlot();
		if (oslot == -1)
			return;
		
		/* take a copy of the input, but we only want 1 of whatever it is! */
		ItemStack sourceStack = inventory[INV_SLOT_INPUT].copy();
		sourceStack.stackSize = 1;
		
		List list = EnchantmentHelper.buildEnchantmentList(this.rand, sourceStack, enchantLevel);
		boolean isBook = sourceStack.itemID == Item.book.itemID;
		
		if (list != null)
		{
			/* remove from inventory and cleanup */
			inventory[INV_SLOT_INPUT].stackSize--;
			if (inventory[INV_SLOT_INPUT].stackSize <= 0)
				inventory[INV_SLOT_INPUT] = null;
			
			/* Can enchant this item */
			entityPlayer.addExperienceLevel(-enchantLevel);
			
			/* Update the item id for a book */
			if (isBook)
				sourceStack.itemID = Item.enchantedBook.itemID;

			/*
			 * Books only get one enchant, other items can get multiple
			 */
            for (int k = 0; k < list.size(); ++k)
            {
                EnchantmentData enchant = (EnchantmentData)list.get(k);
                
                if (isBook)
                {
                	Item.enchantedBook.addEnchantment(sourceStack, enchant);
                	break;
                }
                
                sourceStack.addEnchantment(enchant.enchantmentobj, enchant.enchantmentLevel);                    
            }
            
            /* move to the output */
            inventory[oslot] = sourceStack;            
		}	onInventoryChanged();			
	}
	
	private boolean freeOutputSlot() {
		
		for (OutputSlot s: OutputSlot.values()) {
			if (inventory[s.slot()] == null)
				return true;
		}
		return false;
	}
	
	private int getOutputSlot() {
		
		for (OutputSlot s: OutputSlot.values()) {
			if (inventory[s.slot()] == null)
				return s.slot();
		}
		return -1; /* no outputs available */
	}
	
	private void updateCanEnchant() {
	
		if (inventory[INV_SLOT_INPUT] == null || inventory[INV_SLOT_INPUT].stackSize <= 0) {
			canEnchant = false;
			return;
		}
		
		if (!freeOutputSlot()) {
			canEnchant = false;
			return;
		}
		
		ItemStack itemStack = inventory[INV_SLOT_INPUT].copy();
		itemStack.stackSize = 1;
		if (!itemStack.isItemEnchantable()) {
			canEnchant = false;
			return;
		}
		
		canEnchant = true;
	}
	
	/* IInventory */
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
				onInventoryChanged();
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
		
		onInventoryChanged();
		
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
	public void onInventoryChanged() {
		super.onInventoryChanged();
		
		updateCanEnchant();
	}
	
	public void handleInterfacePacket(byte eventId, int data, Player player) {
		
		if (eventId == PacketHandler.INTERFACE_PKT_BUTTON) {
			if (data == GuiEnchanter.GUI_BUTTON_DESR) {
				decEnchantLevel();
			} else if (data == GuiEnchanter.GUI_BUTTON_INCR) {
				incEnchantLevel();
			} else if (data == GuiEnchanter.GUI_BUTTON_ENCHANT) {
				enchantItem(player);
			}
		}
	}
	
	/**
	 * ISidedInventory
	 * Can only extract output items
	 */
	
	private static int[] sidedInfo = new int[]{ 
		OutputSlot.INV_SLOT_OUTPUT1.slot(), 
		OutputSlot.INV_SLOT_OUTPUT2.slot(),
		OutputSlot.INV_SLOT_OUTPUT3.slot(),
		OutputSlot.INV_SLOT_OUTPUT4.slot(),
		OutputSlot.INV_SLOT_OUTPUT5.slot(),
		OutputSlot.INV_SLOT_OUTPUT6.slot()
	};

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		
		if (side == 0 || side == 1)
			return null;
		else
			return sidedInfo;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {

		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		
		if (side == 0 || side == 1)
			return false;
		
		if (slot >= OutputSlot.INV_SLOT_OUTPUT1.slot() && slot <= OutputSlot.INV_SLOT_OUTPUT6.slot())
			return true;
		
		return false;
	}
}
