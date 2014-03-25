package com.ipsis.mackit.tileentity;

import com.ipsis.mackit.lib.GuiIds;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/*
 * Inventory of
 * 1 intput slot (slot 0)
 * 1 output slot (slot 1)
 */

public class TileMachineStamper extends TileMachinePowered implements IPoweredSM, IFluidHandler {

	private static final int RF_CAPACITY = 32000;
	private static final int TANK_CAPACITY = 10000;
	
	public FluidTank pureTank;
	
	/* Recipe */
	private static final int RECIPE_RF_ENERGY = 1000;
	private static final int DYE_MB = 192;
	
	/* Slots */
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_OUTPUT = 1;
	
	public TileMachineStamper() {
		
		super(RF_CAPACITY);
		pureTank = new FluidTank(TANK_CAPACITY);
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

		return false;
	}
	
	@Override
	public void clearRecipe() {

	}
	
	@Override
	public void setRecipe() {
	}
	
	@Override
	public void produceOutput() {
		
	}
	
	@Override
	public int getRecipeEnergy() {
		
		return RECIPE_RF_ENERGY;
	}
	
	
	
}
