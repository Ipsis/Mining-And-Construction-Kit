package com.ipsis.mackit.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ipsis.cofhlib.gui.slot.SlotOutput;
import com.ipsis.mackit.block.TileVegas;
import com.ipsis.mackit.gui.SlotOre;
import com.ipsis.mackit.gui.SlotToken;
import com.ipsis.mackit.helper.PlayerHelper;

public class ContainerVegas extends Container {

	TileVegas te;
	
	public ContainerVegas(InventoryPlayer invPlayer, TileVegas te) {

		this.te = te;
		
		this.addSlotToContainer(new SlotOre(te, te.INPUT_SLOT, 42, 25));
		this.addSlotToContainer(new SlotToken(te, te.TOKEN_SLOT, 42, 43));
		this.addSlotToContainer(new SlotOutput(te, te.OUTPUT_SLOT, 114, 35));
		
		/* Player inventory */
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++)
				this.addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 6 + x * 18, 95 + y * 18));
		}
		
		/* Player hotbar */
		for (int x = 0; x < 9; x++)
			this.addSlotToContainer(new Slot(invPlayer, x, 6 + x * 18, 153));
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {

		return PlayerHelper.canInteractWithPlayer(entityPlayer, te.xCoord, te.yCoord, te.zCoord); 
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slot) {

		return null;
	}
}
