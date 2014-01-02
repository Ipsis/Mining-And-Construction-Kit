package com.ipsis.mackit.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ipsis.mackit.tileentity.TileEnchanter;

public class ContainerEnchanter extends Container {
	
	private final int PLAYER_INV_ROWS = 3;
	private final int PLAYER_INV_COLS = 9;
	
	private TileEnchanter tileEnchanter;
	
	public ContainerEnchanter(InventoryPlayer inventoryPlayer, TileEnchanter tileEnchanter) {
		
		this.tileEnchanter = tileEnchanter;
		
		this.addSlotToContainer(new Slot(tileEnchanter, TileEnchanter.SLOT_INPUT, 24, 35));
		this.addSlotToContainer(new Slot(tileEnchanter, TileEnchanter.SLOT_OUTPUT, 132, 35));
		
		/* Player hotbar */
		for (int x = 0; x < PLAYER_INV_COLS; x++) {
			this.addSlotToContainer(new Slot(inventoryPlayer, x, 6 + x * 18, 153));
		}
		
		/* Player inventory */
		for (int y = 0; y < PLAYER_INV_ROWS; y++)  {
			for (int x = 0; x < PLAYER_INV_COLS; x++) {
				this.addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, 6 + x * 18, 95 + y * 18));
			}
		}		
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {

		return tileEnchanter.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		return null;
	}
	
}
