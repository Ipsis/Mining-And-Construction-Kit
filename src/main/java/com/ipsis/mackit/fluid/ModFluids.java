package com.ipsis.mackit.fluid;

import com.ipsis.mackit.lib.Strings;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {

	public static Fluid redDye;
	public static Fluid yellowDye;
	public static Fluid blueDye;
	public static Fluid whiteDye;
	public static Fluid pureDye;
	
	public static void init() {
		
		redDye = new FluidDye(Strings.FL_RED_DYE_NAME);
		yellowDye = new FluidDye(Strings.FL_YELLOW_DYE_NAME);
		blueDye = new FluidDye(Strings.FL_BLUE_DYE_NAME);
		whiteDye = new FluidDye(Strings.FL_WHITE_DYE_NAME);
		pureDye = new FluidDye(Strings.FL_PURE_DYE_NAME);
		
		FluidRegistry.registerFluid(redDye);
		FluidRegistry.registerFluid(yellowDye);
		FluidRegistry.registerFluid(blueDye);
		FluidRegistry.registerFluid(whiteDye);
		FluidRegistry.registerFluid(pureDye);
	}
}
