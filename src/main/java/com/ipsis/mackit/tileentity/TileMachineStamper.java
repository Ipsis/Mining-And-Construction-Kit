package com.ipsis.mackit.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.ipsis.mackit.client.gui.inventory.GuiMachineStamper;
import com.ipsis.mackit.lib.GuiIds;
import com.ipsis.mackit.manager.MKRegistry;
import com.ipsis.mackit.network.packet.PacketGui;

import cpw.mods.fml.common.network.Player;

/*
 * Inventory of
 * 1 intput slot (slot 0)
 * 1 output slot (slot 1)
 */

public class TileMachineStamper extends TileMachinePowered implements IPoweredSM, IFluidHandler {

	private static final int RF_CAPACITY = 32000;
	private static final int TANK_CAPACITY = 10000;
	
	public FluidTank pureTank;
	private int selected;
	
	/* Recipe */
	private static final int RECIPE_RF_ENERGY = 1000;
	private static final int DYE_MB = 192;
	
	/* Slots */
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_OUTPUT = 1;
	
	public TileMachineStamper() {
		
		super(RF_CAPACITY);
		pureTank = new FluidTank(TANK_CAPACITY);
		selected = 0;
	}
	
	@Override
	public int getSizeInventory() {
		
		return 2;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

		return pureTank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {

		return pureTank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

		return pureTank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {

		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		
		return new FluidTankInfo[] {pureTank.getInfo()};
	}

	@Override
	public int getGuiID() {

		return GuiIds.STAMPER;
	}

	@Override
	public void setRecipeEnergy(int energy) {
		
		/* constant */		
	}
	
	/* IPoweredSM */
	
	@Override
	public boolean isMachineReady() {
		
		ItemStack output = MKRegistry.getStamperManager().getOutput(selected);
				
		if (output == null)
			return false;
		
		if (getStackInSlot(SLOT_INPUT) == null)
			return false;
		
		/*
		FluidStack f = pureTank.drain(DYE_MB, false);
		if (f == null || f.amount < DYE_MB)
			return false; */
		
		if (getStackInSlot(SLOT_OUTPUT) == null)
			return true;
		
		if (!output.isItemEqual(getStackInSlot(SLOT_OUTPUT)))
			return false;
		
		return true;
	}
	
	@Override
	public void clearRecipe() {

	}
	
	@Override
	public void setRecipe() {
	}
	
	@Override
	public void produceOutput() {
		
		/* consume the inputs */
		pureTank.drain(DYE_MB, true);
		decrStackSize(SLOT_INPUT, 1);
		
		ItemStack output = MKRegistry.getStamperManager().getOutput(selected);
		
		if (output == null)
			return;
				
		if (getStackInSlot(SLOT_OUTPUT) == null)
			setInventorySlotContents(SLOT_OUTPUT, output);
		else
			getStackInSlot(SLOT_OUTPUT).stackSize++;
		
	}
	
	@Override
	public int getRecipeEnergy() {
		
		return RECIPE_RF_ENERGY;
	}
	
	public void handleInterfacePacket(int ctrlType, int ctrlId, int ctrlDat, Player player) {
		
		if (ctrlType == PacketGui.CTRL_BUTTON) {
			if (ctrlId == GuiMachineStamper.GUI_BUTTON_INCR)
				nextOutput();
			else if (ctrlId == GuiMachineStamper.GUI_BUTTON_DECR)
				prevOutput();
		}
	}
	
	public void nextOutput() {
		selected = MKRegistry.getStamperManager().getNext(selected);
	}
	
	public void prevOutput() {
		selected = MKRegistry.getStamperManager().getPrev(selected);
	}
	
	/* 0 - 15 representing the colours */
	public int getSelectedIndex() {
		
		return selected;
	}
	
	public void setSelectedIndex(int idx) {
	
		selected = idx;
	}

}
