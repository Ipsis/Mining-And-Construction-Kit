package com.ipsis.mackit.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.tileentity.TileEntity;

import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.lib.GuiIds;
import com.ipsis.mackit.tileentity.TileMachinePowered;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerPowered extends ContainerMK {
	
	private int lastEnergyStored;	/* how much energy is stored */
	private int lastRecipeConsumed;	/* how much energy has been consumed for the current recipe */
	
	private TileMachinePowered powerTe;
	
	public ContainerPowered(InventoryPlayer invPlayer, TileMachinePowered te, int hotbarX, int hotbarY, int playerInvX, int playerInvY) {
	
		super(invPlayer, te, hotbarX, hotbarY, playerInvX, playerInvY);
		this.powerTe = te;
	}
	
	/*
	 * Gui Updating
	 */
	
	@Override
	public void addCraftingToCrafters(ICrafting iCrafting) {

		super.addCraftingToCrafters(iCrafting);
		
		iCrafting.sendProgressBarUpdate(this, GuiIds.GUI_UPD_ENERGY_STORED, powerTe.storage.getEnergyStored());
		iCrafting.sendProgressBarUpdate(this, GuiIds.GUI_UPD_RECIPE_CONSUMED, powerTe.getEnergyConsumed());
	}
	
	@Override
	public void detectAndSendChanges() {

		super.detectAndSendChanges();
		
        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;
            
            if (lastEnergyStored != powerTe.storage.getEnergyStored())
            	icrafting.sendProgressBarUpdate(this, GuiIds.GUI_UPD_ENERGY_STORED, powerTe.storage.getEnergyStored());
            
            if (lastRecipeConsumed != powerTe.getEnergyConsumed())
            	icrafting.sendProgressBarUpdate(this, GuiIds.GUI_UPD_RECIPE_CONSUMED, powerTe.getEnergyConsumed());
        }
        
        lastEnergyStored = powerTe.storage.getEnergyStored();
        lastRecipeConsumed = powerTe.getEnergyConsumed();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		
		if (id == GuiIds.GUI_UPD_ENERGY_STORED)
			powerTe.storage.setEnergyStored(data);
		else if (id == GuiIds.GUI_UPD_RECIPE_CONSUMED)
			powerTe.setEnergyConsumed(data);
	}

}
