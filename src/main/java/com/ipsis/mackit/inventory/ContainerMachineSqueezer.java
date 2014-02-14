package com.ipsis.mackit.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

import com.ipsis.mackit.tileentity.TileMachineBBBuilder;
import com.ipsis.mackit.tileentity.TileMachineSqueezer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerMachineSqueezer extends Container {

	private final int PLAYER_INV_ROWS = 3;
	private final int PLAYER_INV_COLS = 9;
	
	private TileMachineSqueezer te;
	
	public ContainerMachineSqueezer(InventoryPlayer inventoryPlayer, TileMachineSqueezer tileMachineSqueezer) {
		
		this.te = tileMachineSqueezer;
		
		this.addSlotToContainer(new Slot(tileMachineSqueezer, 0, 76, 35));
		
		/* Player hotbar */
		for (int x = 0; x < PLAYER_INV_COLS; x++)
			this.addSlotToContainer(new Slot(inventoryPlayer, x, 6 + x * 18, 153));
		
		/* Player inventory */
		for (int y = 0; y < PLAYER_INV_ROWS; y++)  {
			for (int x = 0; x < PLAYER_INV_COLS; x++)
				this.addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, 6 + x * 18, 95 + y * 18));
		}	
	}
	
	public TileMachineSqueezer getTileEntity() {
		
		return this.te;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {

		return this.te.isUseableByPlayer(entityplayer);
	}
	
	/*
	 * Gui Updating
	 * 
	 * current energy stored
	 * recipe energy used
	 * recipe energy total
	 * 
	 * fluid tank stored
	 */
	private static final int GUI_UPD_ENERGY_STORED = 0;
	private static final int GUI_UPD_ENERGY_CONSUMED = 1;
	
	@Override
	public void addCraftingToCrafters(ICrafting iCrafting) {
		
		super.addCraftingToCrafters(iCrafting);
	
		iCrafting.sendProgressBarUpdate(this, GUI_UPD_ENERGY_STORED, te.storage.getEnergyStored());
		iCrafting.sendProgressBarUpdate(this, GUI_UPD_ENERGY_CONSUMED, te.getEnergyConsumed());
	}
	
	private int lastEnergyStored;
	private int lastEnergyConsumed;
	
	@Override
	public void detectAndSendChanges() {
		
		super.detectAndSendChanges();
		
        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;
            
            if (lastEnergyStored != te.storage.getEnergyStored())
            	icrafting.sendProgressBarUpdate(this, GUI_UPD_ENERGY_STORED, te.storage.getEnergyStored());
            
            if (lastEnergyConsumed != te.getEnergyConsumed())
            	icrafting.sendProgressBarUpdate(this, GUI_UPD_ENERGY_CONSUMED, te.getEnergyConsumed());
        }
        
        lastEnergyStored = te.storage.getEnergyStored();
        lastEnergyConsumed = te.getEnergyConsumed();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		
		if (id == GUI_UPD_ENERGY_STORED)
			te.storage.setEnergyStored(data);
		else if (id == GUI_UPD_ENERGY_CONSUMED)
			te.setEnergyConsumed(data);
	}
	
	
	
}
