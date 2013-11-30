package ipsis.mackit.tileentity;

import ipsis.mackit.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TileMachineBBBuilder extends TileMachine implements IInventory {
	
	/* 
	 * 4 dirt input, 1 redstone input
	 * 1 output
	 */
	private ItemStack[] items;
	private static final int FIRST_DIRT_SLOT = 0;
	private static final int LAST_DIRT_SLOT = 3;
	private static final int REDSTONE_SLOT = 4;
	private static final int OUTPUT_SLOT = 5;

	static final int RECIPE_DIRT_STACKSIZE = 128;
	static final int RECIPE_REDSTONE_STACKSIZE = 1;
	static final int RECIPE_ENERGY = 100;
	static final int RECIPE_OUTPUT_STACKSIZE = 1;
	
	private int invDirtAmount = 0;
	
	private static final ItemStack DIRT = new ItemStack(Block.dirt);
	private static final ItemStack REDSTONE = new ItemStack(Item.redstone);
	private static final ItemStack BEAVER_BLOCK = new ItemStack(ModBlocks.waterFiller);
	
	
	public TileMachineBBBuilder() {
		super();
		
		items = new ItemStack[6];
	}
	
	private int getInvDirtAmount() {
		if (invDirtAmount == -1) {
			invDirtAmount = 0;
			for (int i = 0; i < 4; i++) {
				if (items[i] != null && items[i].isItemEqual(DIRT))
					invDirtAmount += items[i].stackSize;
			}
		}
		
		return invDirtAmount;
	}
	
	private boolean isInputReady() {
		if (items[REDSTONE_SLOT] == null)
			return false;
		
		if (items[REDSTONE_SLOT].stackSize < 1)
			return false;
		
		if (!items[REDSTONE_SLOT].isItemEqual(REDSTONE))
			return false;
			
		if (getInvDirtAmount() < RECIPE_DIRT_STACKSIZE)
			return false;
		
		return true;
	}

	@Override
	public boolean isMachineReady() {
		
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
	public void clearSavedRecipeSource() { /* not used */ }

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
	public void setRecipeSource() {
		recipeEnergy = RECIPE_ENERGY;
	}

	@Override
	public boolean hasSourceChanged() {
		return isInputReady();
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
        invDirtAmount = -1;
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

}
