package com.ipsis.mackit.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

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
		
		this.addSlotToContainer(new Slot(te, TileMachineSqueezer.SLOT_INPUT, 33, 34));	
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
	private static int RED_TANK_ID = GuiIds.GUI_UPD_TANK_0_FLUID_AMOUNT;
	private static int YELLOW_TANK_ID = GuiIds.GUI_UPD_TANK_1_FLUID_AMOUNT;
	private static int BLUE_TANK_ID = GuiIds.GUI_UPD_TANK_2_FLUID_AMOUNT;
	private static int WHITE_TANK_ID = GuiIds.GUI_UPD_TANK_3_FLUID_AMOUNT;
	private static int PURE_TANK_ID = GuiIds.GUI_UPD_TANK_4_FLUID_AMOUNT;
	
	private void sendFluidStack(ICrafting iCrafting, FluidStack f, int id) {
		
		if (f != null)
			iCrafting.sendProgressBarUpdate(this, id, f.amount);
		else
			iCrafting.sendProgressBarUpdate(this, id, 0);
	}
	
	private boolean checkTank(ICrafting iCrafting, FluidTank t, int lastAmount, int id) {
		
		boolean sent = false;
		FluidStack f = t.getFluid();
		
		if (f != null && f.amount != lastAmount) {
			sendFluidStack(iCrafting, f, id);
			sent = true;
		} else if (f == null && lastAmount != 0) {
			sendFluidStack(iCrafting, f, id);
			sent = true;
		}
		
		return true;
	}
	
	
	@Override
	public void addCraftingToCrafters(ICrafting iCrafting) {
		
		super.addCraftingToCrafters(iCrafting);		
		sendFluidStack(iCrafting, squeezerTe.redTank.getFluid(), RED_TANK_ID);
		sendFluidStack(iCrafting, squeezerTe.yellowTank.getFluid(), YELLOW_TANK_ID);
		sendFluidStack(iCrafting, squeezerTe.blueTank.getFluid(), BLUE_TANK_ID);
		sendFluidStack(iCrafting, squeezerTe.whiteTank.getFluid(), WHITE_TANK_ID);
		sendFluidStack(iCrafting, squeezerTe.pureTank.getFluid(), PURE_TANK_ID);		
	}
	
	
	private int lastRedFluidAmount;
	private int lastYellowFluidAmount;
	private int lastBlueFluidAmount;
	private int lastWhiteFluidAmount;
	private int lastPureFluidAmount;
	
	@Override
	public void detectAndSendChanges() {
		
		super.detectAndSendChanges();		
		
		boolean sent = false;
        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;
            
            if (checkTank(icrafting, squeezerTe.redTank, lastRedFluidAmount, RED_TANK_ID))
            	sent = true;
            if (checkTank(icrafting, squeezerTe.yellowTank, lastYellowFluidAmount, YELLOW_TANK_ID))
            	sent = true;
            if (checkTank(icrafting, squeezerTe.blueTank, lastBlueFluidAmount, BLUE_TANK_ID))
            	sent = true;
            if (checkTank(icrafting, squeezerTe.whiteTank, lastWhiteFluidAmount, WHITE_TANK_ID))
            	sent = true;
            if (checkTank(icrafting, squeezerTe.pureTank, lastPureFluidAmount, PURE_TANK_ID))
            	sent = true;
        }
        
        if (sent == true) {
        	
        	FluidStack f;
        	f = squeezerTe.redTank.getFluid();
        	lastRedFluidAmount = f != null ? f.amount : 0;
        	f = squeezerTe.yellowTank.getFluid();
        	lastYellowFluidAmount = f != null ? f.amount : 0;
        	f = squeezerTe.blueTank.getFluid();
        	lastBlueFluidAmount = f != null ? f.amount : 0;
        	f = squeezerTe.whiteTank.getFluid();
        	lastWhiteFluidAmount = f != null ? f.amount : 0;
        	f = squeezerTe.pureTank.getFluid();
        	lastPureFluidAmount = f != null ? f.amount : 0;
        }

	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		
		super.updateProgressBar(id, data);	
		if (id == RED_TANK_ID)
			squeezerTe.setRedTankAmount(data);
		else if (id == YELLOW_TANK_ID)
			squeezerTe.setYellowTankAmount(data);
		else if (id == BLUE_TANK_ID)
			squeezerTe.setBlueTankAmount(data);
		else if (id == WHITE_TANK_ID)
			squeezerTe.setWhiteTankAmount(data);
		else if (id == PURE_TANK_ID)
			squeezerTe.setPureTankAmount(data);
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
