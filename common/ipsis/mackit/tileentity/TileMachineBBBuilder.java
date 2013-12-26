package ipsis.mackit.tileentity;

import ipsis.mackit.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileMachineBBBuilder extends TileMachine implements IInventory, ISidedInventory {
	
	/* 
	 * 6 dirt input, 1 redstone input
	 * 1 output
	 */
	private ItemStack[] items;
	private static final int FIRST_DIRT_SLOT = 0;
	private static final int LAST_DIRT_SLOT = 5;
	private static final int REDSTONE_SLOT = 6;
	private static final int OUTPUT_SLOT = 7;
	
	private static final int[] accessSlots = new int[] {0, 1, 2, 3, 4, 5, 6, 7};

	private static final int RECIPE_DIRT_STACKSIZE = 128;
	private static final int RECIPE_REDSTONE_STACKSIZE = 1;
	private static final int RECIPE_ENERGY = 1000;
	private static final int RECIPE_OUTPUT_STACKSIZE = 1;
	
	
	private static final ItemStack DIRT = new ItemStack(Block.dirt);
	private static final ItemStack REDSTONE = new ItemStack(Item.redstone);
	private static final ItemStack BEAVER_BLOCK = new ItemStack(ModBlocks.waterFiller);
	
	
	public TileMachineBBBuilder() {
		super();
		
		items = new ItemStack[8];
	}
	
	private boolean isInputReady() {
		if (items[REDSTONE_SLOT] == null)
			return false;
		
		if (items[REDSTONE_SLOT].stackSize < RECIPE_REDSTONE_STACKSIZE)
			return false;
		
		if (!items[REDSTONE_SLOT].isItemEqual(REDSTONE))
			return false;
			
		int invDirtAmount = 0;
		for (int i = FIRST_DIRT_SLOT; i <= LAST_DIRT_SLOT; i++) {
			if (items[i] != null && items[i].isItemEqual(DIRT))
				invDirtAmount += items[i].stackSize;
		}
		
		if (invDirtAmount < RECIPE_DIRT_STACKSIZE)
			return false;
		
		return true;
	}
	

	@Override
	public boolean canMachineStart() {
		if (!isInputReady())
			return false;
		
		if (items[OUTPUT_SLOT] == null)
			return true;
		
		/* this shouldn't happen */
		if (!items[OUTPUT_SLOT].isItemEqual(BEAVER_BLOCK))
			return false;
		
		if (items[OUTPUT_SLOT].stackSize + RECIPE_OUTPUT_STACKSIZE > items[OUTPUT_SLOT].getMaxStackSize())
			return false;

		return true;
	}

	@Override
	public void clearSavedRecipe() { /* not used */ }

	@Override
	public void createOutput() {
		
		if (items[OUTPUT_SLOT] == null) {
			items[OUTPUT_SLOT] = BEAVER_BLOCK.copy();
			items[OUTPUT_SLOT].stackSize = RECIPE_OUTPUT_STACKSIZE;
		} else if (items[OUTPUT_SLOT].isItemEqual(BEAVER_BLOCK)) {
			if (items[OUTPUT_SLOT].stackSize + RECIPE_OUTPUT_STACKSIZE <= items[OUTPUT_SLOT].getMaxStackSize())
				items[OUTPUT_SLOT].stackSize += RECIPE_OUTPUT_STACKSIZE;
		}
		
		int remove = RECIPE_DIRT_STACKSIZE;
		for (int i = FIRST_DIRT_SLOT; i <= LAST_DIRT_SLOT; i++) {
			if (remove <= 0)
				break;
			
			ItemStack items = decrStackSize(i, remove);
			if (items != null) {
				remove -= items.stackSize;
			}
		}
		
		decrStackSize(REDSTONE_SLOT, 1);
			
	}

	@Override
	public void setRecipe() {	}
	
	@Override
	public int getRecipeEnergy() {
		return RECIPE_ENERGY;
	}
	
	/* IInventory */
	@Override
	public int getSizeInventory() {
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i < items.length)
			return items[i];
		
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int count) {
		ItemStack itemstack = getStackInSlot(i);
		
		if (itemstack != null) {
			if (itemstack.stackSize <= count) {
				setInventorySlotContents(i, null);
			} else {
				itemstack = itemstack.splitStack(count);
				onInventoryChanged();
			}
		}
		
		return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack itemstack = getStackInSlot(i);
		setInventorySlotContents(i, null);
		return itemstack;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if (i < items.length) {
			items[i] = itemstack;
			onInventoryChanged();
		}
	}

	@Override
	public String getInvName() {
		return "machineBBBuilder";
	}

	@Override
	public boolean isInvNameLocalized() {
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
    public void onInventoryChanged()
    {
        super.onInventoryChanged();
        
        /* recheck the input if there is a change */
        setIsInventoryOk(isInputReady());
    }

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (i >= FIRST_DIRT_SLOT && i <= LAST_DIRT_SLOT) {
			if (itemstack.isItemEqual(DIRT))
				return true;
		} else if (i == REDSTONE_SLOT) {
			if (itemstack.isItemEqual(REDSTONE))
				return true;
		}
		
		return false;
	}

	/* ISidedInventory */
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return accessSlots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		return isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		if (!itemstack.isItemEqual(BEAVER_BLOCK))
			return false;
		
		if (slot != OUTPUT_SLOT)
			return false;
		
		return true;
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
	
	/* Gui */
	public void sendGUINetworkData(Container container, ICrafting iCrafting) {
		super.sendGUINetworkData(container, iCrafting);
	}
	

	public void getGUINetworkData(int id, int data) {		
		super.getGUINetworkData(id, data);
	}
	

}
