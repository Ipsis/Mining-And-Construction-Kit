package com.ipsis.mackit.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import com.ipsis.mackit.fluid.ModFluids;
import com.ipsis.mackit.lib.GuiIds;

/*
 * Inventory of
 * 1 input slot (slot 0)
 * 1 output tank
 */

public class TileMachineSqueezer extends TileMachinePowered implements IPoweredSM {

	private static final int RF_CAPACITY = 32000;
	private static final int TANK_CAPACITY = 10000;
	
	public FluidTank tank;
	
	/* Recipe */
	private static final int RECIPE_RF_ENERGY = 1000;
	
	
	/* Slots */
	public static final int SLOT_INPUT = 0;
	
	public TileMachineSqueezer() {
		
		super(RF_CAPACITY);
		tank = new FluidTank(TANK_CAPACITY);
		
		FluidStack t = new FluidStack(ModFluids.blueDye, 0);
		tank.fill(t, true);
	}

	@Override
	public int getGuiID() {

		return GuiIds.SQUEEZER;
	}

	@Override
	public void setRecipeEnergy(int energy) {
		
		/* constant */		
	}
	
	@Override
	public boolean isMachineReady() {
		
		/* simple version just now */
		if (getStackInSlot(SLOT_INPUT) == null)
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
		
		decrStackSize(SLOT_INPUT, 1);
		tank.fill(new FluidStack(ModFluids.blueDye, 1000), true);
	}
	
	@Override
	public int getRecipeEnergy() {
		
		return RECIPE_RF_ENERGY;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {

		super.readFromNBT(compound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {

		super.writeToNBT(compound);
	}
	
	/* Gui Update */
	public void setTankFluidID(int id) {
		
		FluidStack f = tank.getFluid();
		if (f == null)
			f = new FluidStack(id, 0);
		else
			f.fluidID = id;
	}
	
	public void setTankFluidAmount(int amount) {
		
		FluidStack f = tank.getFluid();
		if (f == null)
			f = new FluidStack(ModFluids.blueDye, amount);
		else
			f.amount = amount;
	}
	
}
