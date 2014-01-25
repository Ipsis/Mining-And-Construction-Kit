package com.ipsis.mackit.tileentity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.ipsis.mackit.block.ModBlocks;
import com.ipsis.mackit.lib.BlockIds;

/*
 * Inventory of
 * 6 dirt slots (slots 0-5)
 * 1 redstone block slot (slot 6)
 * 1 output slot (slot 7)
 */

public class TileMachineBBBuilder extends TileMachinePowered {
	
	private static final int RF_CAPACITY = 32000;
	
	/* Recipe */
	private static final int RECIPE_RF_ENERGY = 1000;
	private static final int RECIPE_DIRT_STACKSIZE = 128;
	
	/* Slots */
	public static final int SLOT_DIRT_FIRST = 0;
	public static final int SLOT_DIRT_LAST = 5;
	public static final int SLOT_RS_BLOCK = 6;
	public static final int SLOT_OUTPUT = 7;
	
	public TileMachineBBBuilder() {
		super(RF_CAPACITY);
	}
	
	@Override
	public int getSizeInventory() {	
		return 8;
	}

	/*
	 * Abstract functions from TileMachinePowered
	 */
	
	@Override
	protected boolean isMachineReady() {
		
		/* Dirt */
		int dirtCount = 0;
		for (int i = SLOT_DIRT_FIRST; i <= SLOT_DIRT_LAST; i++)
			dirtCount += getStackInSlot(i).stackSize;
		
		if (dirtCount < RECIPE_DIRT_STACKSIZE)
			return false;
		
		/* Redstone block */
		if (getStackInSlot(SLOT_RS_BLOCK) == null || getStackInSlot(SLOT_RS_BLOCK).getItem().itemID != Item.redstone.itemID)
			return false;
		
		if (getStackInSlot(SLOT_OUTPUT) == null)
			return true;
		
		if (getStackInSlot(SLOT_OUTPUT).getItem().itemID != BlockIds.BEAVER_BLOCK)
			return false;
		
		return true;
	}

	@Override
	protected int getRecipeEnergy() {
		return RECIPE_RF_ENERGY;
	}

	@Override
	protected void clearRecipe() {
		/* constant recipe => nothing to do here */
	}

	@Override
	protected void setRecipe() {
		/* constant recipe => nothing to do here */		
	}

	@Override
	protected void produceOutput() {
		
		int left = RECIPE_DIRT_STACKSIZE;
		for (int i = SLOT_DIRT_FIRST; i < SLOT_DIRT_LAST; i++) {
			/* ???? */
		}
		
		decrStackSize(SLOT_RS_BLOCK, 1);
		
		ItemStack output = getStackInSlot(SLOT_OUTPUT);
		if (output != null)
			output.stackSize++;
		else
			output = new ItemStack(ModBlocks.beaverBlock);
		
		setInventorySlotContents(SLOT_OUTPUT, output);
	}
	
	

}
