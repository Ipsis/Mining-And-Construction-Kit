package com.ipsis.mackit.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.lib.GuiIds;
import com.ipsis.mackit.tileentity.TileMachineSqueezer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerMachineSqueezer extends ContainerPowered {

	private TileMachineSqueezer squeezerTe;
	
	public ContainerMachineSqueezer(InventoryPlayer invPlayer, TileMachineSqueezer te) {
		
		super(invPlayer, te, 6, 153, 6, 95);
		this.squeezerTe = te;
		
		this.addSlotToContainer(new Slot(te, TileMachineSqueezer.SLOT_INPUT, 76, 35));	
	}
	
	public TileMachineSqueezer getTileEntity() {
		
		return this.squeezerTe;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {

		return this.squeezerTe.isUseableByPlayer(entityplayer);
	}
	
	/*
	 * Gui Updating
	 */

	private void sendFluidStack(ICrafting iCrafting, FluidStack f) {
		
		if (f != null) {
			iCrafting.sendProgressBarUpdate(this, GuiIds.GUI_UPD_TANK_FLUID_ID, f.fluidID);
			iCrafting.sendProgressBarUpdate(this, GuiIds.GUI_UPD_TANK_FLUID_AMOUNT, f.amount);
		} else {
			iCrafting.sendProgressBarUpdate(this, GuiIds.GUI_UPD_TANK_FLUID_ID, 0);
			iCrafting.sendProgressBarUpdate(this, GuiIds.GUI_UPD_TANK_FLUID_AMOUNT, 0);
		}
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting iCrafting) {
		
		super.addCraftingToCrafters(iCrafting);		
		sendFluidStack(iCrafting, squeezerTe.tank.getFluid());
	}
	
	private int lastFluidID;
	private int lastFluidAmount;
	
	@Override
	public void detectAndSendChanges() {
		
		super.detectAndSendChanges();
		
        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;
            
            int fluidID;
            int fluidAmount;
            
            FluidStack f = squeezerTe.tank.getFluid();
            if (f != null && (f.fluidID != lastFluidID || f.amount != lastFluidAmount))
            	sendFluidStack(icrafting, f);
            else if (f == null && (lastFluidID != 0 || lastFluidAmount != 0))
            	sendFluidStack(icrafting, f); 
        }
        
        FluidStack f = squeezerTe.tank.getFluid();
        lastFluidID = f != null ? f.fluidID : 0;
        lastFluidAmount = f != null ? f.amount : 0;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
			
		super.updateProgressBar(id, data);	
		if (id == GuiIds.GUI_UPD_TANK_FLUID_ID)
			squeezerTe.setTankFluidID(data);
		else if (id == GuiIds.GUI_UPD_TANK_FLUID_AMOUNT)
			squeezerTe.setTankFluidAmount(data);
	}
	
	 @Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		 
		 /* Machine -> Player */
		 if (slotIndex >= 36)
			 return super.transferStackInSlot(player, slotIndex);
		 
		 Slot slot = getSlot(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			ItemStack result = stack.copy();
			
			/* lets anything in for now */
			if (!mergeItemStack(stack, 36, 36 + squeezerTe.getSizeInventory(), false)) 
				return null;
			
			if (stack.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();
			
			slot.onPickupFromSlot(player, stack);
			
			return result;			
		}
		 
		 return null;
	}
}
