package com.ipsis.mackit.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

import com.ipsis.mackit.block.TilePortaChant;

public class ContainerPortaChant extends Container {

	public ContainerPortaChant(InventoryPlayer invPlayer, TilePortaChant te) {

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
		// TODO Auto-generated method stub
		return true;
	}
}
