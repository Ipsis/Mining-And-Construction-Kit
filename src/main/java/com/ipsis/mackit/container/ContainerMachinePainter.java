package com.ipsis.mackit.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.ipsis.cofhlib.gui.slot.SlotOutput;
import com.ipsis.mackit.block.TileMachinePainter;
import com.ipsis.mackit.fluid.MKFluids;
import com.ipsis.mackit.gui.SlotDye;
import com.ipsis.mackit.helper.PlayerHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerMachinePainter extends Container {

	TileMachinePainter te;
	
	public ContainerMachinePainter(InventoryPlayer invPlayer, TileMachinePainter te) {
		
		this.te = te;
		
		this.addSlotToContainer(new Slot(te, te.INPUT_SLOT, 52, 35));
		this.addSlotToContainer(new SlotOutput(te, te.OUTPUT_SLOT, 113, 35));
		
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
	
	/**********************
	 *  GUI Crafting Update
	 **********************/
	private void sendFluidStack(ICrafting iCrafting, FluidStack f, int id) {

		if (f != null)
			iCrafting.sendProgressBarUpdate(this, id, f.amount);
		else
			iCrafting.sendProgressBarUpdate(this, id, 0);
	}
	
	private static final int ENERGY_STORED_ID = 0;
	private static final int CONSUMED_ENERGY_ID = 1;
	private static final int RECIPE_ENERGY_ID = 2;
	private static final int PURE_TANK_ID = 3;
	private static final int SELECTED_ID = 4;
	
	@Override
	public void addCraftingToCrafters(ICrafting iCrafting) {

		super.addCraftingToCrafters(iCrafting);
		
		iCrafting.sendProgressBarUpdate(this, ENERGY_STORED_ID, te.getEnergyStorage().getEnergyStored());
		iCrafting.sendProgressBarUpdate(this, CONSUMED_ENERGY_ID, te.getConsumedEnergy());		
		iCrafting.sendProgressBarUpdate(this, RECIPE_ENERGY_ID, te.getRecipeEnergy());
		
		iCrafting.sendProgressBarUpdate(this, SELECTED_ID, te.getSelected());
		
		/* Tanks */
		sendFluidStack(iCrafting, te.tankMgr.getTank(te.PURE_TANK).getFluid(), PURE_TANK_ID);
	}
	
	private int lastSelected;
	private int lastEnergyStored;
	private int lastRecipeConsumed;
	private int lastRecipeEnergy;
	
	@Override
	public void detectAndSendChanges() {

		super.detectAndSendChanges();
		
        for (Object crafter : this.crafters) {
            ICrafting iCrafting = (ICrafting) crafter;
			
            if (lastEnergyStored !=  te.getEnergyStorage().getEnergyStored())
            	iCrafting.sendProgressBarUpdate(this, ENERGY_STORED_ID,  te.getEnergyStorage().getEnergyStored());
            
            if (lastRecipeConsumed != te.getConsumedEnergy())
            	iCrafting.sendProgressBarUpdate(this, CONSUMED_ENERGY_ID, te.getConsumedEnergy());
            
            if (lastRecipeEnergy != te.getRecipeEnergy())
            	iCrafting.sendProgressBarUpdate(this, RECIPE_ENERGY_ID, te.getRecipeEnergy());
            
            /* Cheaty just now */
    		sendFluidStack(iCrafting, te.tankMgr.getTank(te.PURE_TANK).getFluid(), PURE_TANK_ID);
    		
			if (lastSelected != te.getSelected())
				iCrafting.sendProgressBarUpdate(this, SELECTED_ID, te.getSelected());		
		}
		
        lastSelected = te.getSelected();
        lastEnergyStored =  te.getEnergyStorage().getEnergyStored();
        lastRecipeConsumed = te.getConsumedEnergy();
        lastRecipeEnergy = te.getRecipeEnergy();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {

		if (id == ENERGY_STORED_ID) {
			te.getEnergyStorage().setEnergyStored(data);
		} else if (id == CONSUMED_ENERGY_ID) {
			te.setConsumedEnergy(data);
		} else if (id == RECIPE_ENERGY_ID) {
			te.setRecipeEnergy(data);
		} else if (id == PURE_TANK_ID) {
			te.tankMgr.setTank(te.PURE_TANK, MKFluids.fluidDyePure, data);
		} else if (id == SELECTED_ID) {
			te.setSelected(data);
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {

		return null;
	}
	
}
