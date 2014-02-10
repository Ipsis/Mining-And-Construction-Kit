package com.ipsis.mackit.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.block.ModBlocks;
import com.ipsis.mackit.lib.GuiIds;

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
	private static final int RECIPE_RS_STACKSIZE = 1;
	
	/* Slots */
	public static final int SLOT_DIRT_FIRST = 0;
	public static final int SLOT_DIRT_LAST = 5;
	public static final int SLOT_RS_BLOCK = 6;
	public static final int SLOT_OUTPUT = 7;
	
	private static ItemStack inputDirt = new ItemStack(Block.dirt, RECIPE_DIRT_STACKSIZE);
	private static ItemStack inputRsBlock = new ItemStack(Block.blockRedstone, RECIPE_RS_STACKSIZE);
	private static ItemStack outputBBBlock = new ItemStack(ModBlocks.beaverBlock, 1);
	
	public TileMachineBBBuilder() {
		
		super(RF_CAPACITY);
	}
	
	@Override
	public int getSizeInventory() {	
		
		return 8;
	}

	
	@Override
	public boolean isMachineReady() {
		
		/* Dirt */
		int dirtCount = 0;
		for (int i = SLOT_DIRT_FIRST; i <= SLOT_DIRT_LAST; i++) {
			ItemStack t = getStackInSlot(i);
			if (t != null && t.isItemEqual(this.inputDirt))
				dirtCount += getStackInSlot(i).stackSize;
		}
		
		if (dirtCount < this.inputDirt.stackSize)
			return false;
		
		/* Redstone block */
		if (getStackInSlot(SLOT_RS_BLOCK) == null || getStackInSlot(SLOT_RS_BLOCK).isItemEqual(this.inputRsBlock) || getStackInSlot(SLOT_RS_BLOCK).stackSize < this.inputRsBlock.stackSize)
			return false;
		
		/* output empty */
		if (getStackInSlot(SLOT_OUTPUT) == null)
			return true;
		
		/* output !empty */
		if (!getStackInSlot(SLOT_OUTPUT).isItemEqual(this.outputBBBlock))
			return false;
		
		if (getStackInSlot(SLOT_OUTPUT).stackSize >= getInventoryStackLimit())
			return false;
		
		return true;
	}

	@Override
	public int getRecipeEnergy() {
		
		return RECIPE_RF_ENERGY;
	}
	
	@Override
	public void setRecipeEnergy(int energy) {
		
		/* constant recipe => nothing to do here */
	}

	@Override
	public void clearRecipe() {
		
		/* constant recipe => nothing to do here */
	}

	@Override
	public void setRecipe() {
		
		/* constant recipe => nothing to do here */		
	}

	@Override
	public void produceOutput() {
		
		int left = RECIPE_DIRT_STACKSIZE;
		for (int i = SLOT_DIRT_FIRST; i < SLOT_DIRT_LAST; i++) {
			ItemStack t = decrStackSize(SLOT_DIRT_FIRST, left);
			if (t != null)
				left -= t.stackSize;
		}
		
				
		decrStackSize(SLOT_RS_BLOCK, 1);
		
		ItemStack output = getStackInSlot(SLOT_OUTPUT);
		if (output != null)
			output.stackSize++;
		else
			output = new ItemStack(ModBlocks.beaverBlock);
		
		setInventorySlotContents(SLOT_OUTPUT, output);
	}
	
	@Override
	public int getGuiID() {
		
		return GuiIds.BBBUILDER;
	}
	
}
