package com.ipsis.mackit.manager;

import net.minecraftforge.fluids.FluidStack;

import com.ipsis.mackit.block.machinesm.IMachineRecipe;
import com.ipsis.mackit.fluid.MKFluids;

public class StamperRecipe implements IMachineRecipe {

	private static final int PURE_FLUID_AMOUNT = 100;
	private static final int RECIPE_ENERGY = 40;
	
	public static final StamperRecipe RECIPE = new StamperRecipe();
	private FluidStack pureDye;
	
	public StamperRecipe() {
		
		pureDye = new FluidStack(MKFluids.fluidDyePure, PURE_FLUID_AMOUNT);
	}
	
	public FluidStack getPureDye() {
		
		return pureDye;
	}
	
	public int getPureDyeAmount() {
		
		return pureDye.amount;
	}
	
	@Override
	public int getEnergy() {

		return RECIPE_ENERGY;
	}
	
}
