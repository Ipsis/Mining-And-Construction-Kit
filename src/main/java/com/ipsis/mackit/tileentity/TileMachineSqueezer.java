package com.ipsis.mackit.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import com.ipsis.mackit.fluid.ModFluids;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.lib.GuiIds;
import com.ipsis.mackit.manager.MKRegistry;
import com.ipsis.mackit.manager.SqueezerRecipe;
import com.ipsis.mackit.manager.DyeRecipe;

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
	private SqueezerRecipe recipe;
	
	
	/* Slots */
	public static final int SLOT_INPUT = 0;
	
	public TileMachineSqueezer() {
		
		super(RF_CAPACITY);
		tank = new FluidTank(TANK_CAPACITY);
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
		
		if (getStackInSlot(SLOT_INPUT) == null)
			return false;
		
		SqueezerRecipe r = MKRegistry.getSqueezableManager().getRecipe(getStackInSlot(SLOT_INPUT));
		if (r == null)
			return false;
		
		/* empty tank is always fillable */
		if (tank.getFluid() == null)
			return true;
		
		DyeRecipe sr = MKRegistry.getSqueezerManager().getRecipe(r.getDye());
		if (sr == null || !sr.isOutputFluid(tank.getFluid()))
			return false;
		
		return true;
	}
	
	@Override
	public void clearRecipe() {
		
		recipe = null;
	}
	
	@Override
	public void setRecipe() {
		
		LogHelper.severe("setRecipe");
		recipe = MKRegistry.getSqueezableManager().getRecipe(getStackInSlot(SLOT_INPUT));
	}
	
	@Override
	public void produceOutput() {
		
		decrStackSize(SLOT_INPUT, 1);
		
		if (recipe == null)
			return;
		
		DyeRecipe sr = MKRegistry.getSqueezerManager().getRecipe(recipe.getDye());
		LogHelper.severe("produceOutput: " + sr);
		return;
		
		/*
		for (int i = 0; i < recipe.getDye().stackSize; i++) {
			if (tank.getFluid() == null) {
				tank.fill(sr.getRandomOutput(), true);
				
			} else {
				tank.fill(new FluidStack(tank.getFluid(), sr.getAmount(tank.getFluid())), true);
			}			
		}
		
		tank.fill(new FluidStack(ModFluids.blueDye, 1000), true); */
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
