package com.ipsis.mackit.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/*
 * Handles the player inventory and hotbar
 * Takes up slots 0-35
 */
public class ContainerMK extends Container {

	private TileEntity te;
	
	private final int PLAYER_INV_ROWS = 3;
	private final int PLAYER_INV_COLS = 9;
	
	public ContainerMK(InventoryPlayer invPlayer, TileEntity te, int hotbarX, int hotbarY, int playerInvX, int playerInvY) {
		
		this.te = te;
		
		/* Player hotbar */
		for (int x = 0; x < PLAYER_INV_COLS; x++)
			this.addSlotToContainer(new Slot(invPlayer, x, hotbarX + x * 18, hotbarY));
		
		/* Player inventory */
		for (int y = 0; y < PLAYER_INV_ROWS; y++)  {
			for (int x = 0; x < PLAYER_INV_COLS; x++)
				this.addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, playerInvX + x * 18, playerInvY + y * 18));
		}			
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		
		if (te != null)
			return true;

		return false;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		
		/* only supports transferring TO the player */
		if (slotIndex < 36)
			return null;
		
		Slot slot = getSlot(slotIndex);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			ItemStack result = stack.copy();
			
			/* merge into player area 0 -> 35 */
			if (!mergeItemStack(stack, 0, 36, false))
				return null;
						
			if (stack.stackSize == 0)
				slot.putStack(null); /* Clear the slot */
			else
				slot.onSlotChanged();
			
			slot.onPickupFromSlot(player, stack);
			
			return result;
		}
		
		return null;
	}
}
