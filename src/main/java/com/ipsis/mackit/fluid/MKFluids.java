package com.ipsis.mackit.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class MKFluids {

	public static void preInit() {
		
		fluidDyeRed = new Fluid("dyeRed");
		fluidDyeYellow = new Fluid("dyeYellow");
		fluidDyeBlue = new Fluid("dyeBlue");
		fluidDyeWhite = new Fluid("dyeWhite");
		fluidDyePure = new Fluid("dyePure");
		
		FluidRegistry.registerFluid(fluidDyeRed);
		FluidRegistry.registerFluid(fluidDyeYellow);
		FluidRegistry.registerFluid(fluidDyeBlue);
		FluidRegistry.registerFluid(fluidDyeWhite);
		FluidRegistry.registerFluid(fluidDyePure);
	}
	
	public static void initialise() {
		
	}
	
	public static void postInit() {
			
	}
	
	public static Fluid fluidDyeRed;
	public static Fluid fluidDyeYellow;
	public static Fluid fluidDyeBlue;
	public static Fluid fluidDyeWhite;
	public static Fluid fluidDyePure;
}
