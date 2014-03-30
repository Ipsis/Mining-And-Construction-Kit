package com.ipsis.mackit.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ipsis.mackit.tileentity.TileMachineStamper;

public class ContainerMachineStamper extends ContainerPowered {

	private TileMachineStamper stamperTe;
	
	public ContainerMachineStamper(InventoryPlayer invPlayer, TileMachineStamper te) {
		
		super(invPlayer, te, 6, 153, 6, 95);
		this.stamperTe  = te;
		
		this.addSlotToContainer(new Slot(te, TileMachineStamper.SLOT_INPUT, 33, 35));	
		this.addSlotToContainer(new Slot(te, TileMachineStamper.SLOT_OUTPUT, 119, 35));	

	}
	
	public TileMachineStamper getTileEntity() {
		
		return this.stamperTe;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {

		return this.stamperTe.isUseableByPlayer(entityplayer);
	}
	
	/*
	 * Gui Updating
	 */
	
	@Override
	public void addCraftingToCrafters(ICrafting player) {

		super.addCraftingToCrafters(player);
		
		player.sendProgressBarUpdate(this, 0, stamperTe.getSelectedIndex());
	}
	
	private int lastIndex;
	
	@Override
	public void detectAndSendChanges() {

		super.detectAndSendChanges();
		
		for (Object player : crafters) {
			
			if (lastIndex != stamperTe.getSelectedIndex())
				((ICrafting)player).sendProgressBarUpdate(this, 0, stamperTe.getSelectedIndex());
		}
		
		lastIndex = stamperTe.getSelectedIndex();
				
	}
	
	@Override
	public void updateProgressBar(int id, int data) {

		super.updateProgressBar(id, data);
		
		if (id == 0)
			stamperTe.setSelectedIndex(data);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {

		return null;
	}
}
