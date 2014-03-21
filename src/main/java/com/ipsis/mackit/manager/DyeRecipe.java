package com.ipsis.mackit.manager;

import java.util.HashMap;
import java.util.Map;

import com.ipsis.mackit.fluid.ModFluids;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/*
 * OreDict Dye -> Liquid Dye Components
 */
public class DyeRecipe {
	
	public static final int RED = 0;
	public static final int YELLOW = 1;
	public static final int BLUE = 2;
	public static final int WHITE = 3;

	private ItemStack source;	/* OriDict dye item */
	private FluidStack red;
	private FluidStack yellow;
	private FluidStack blue;
	private FluidStack white;
	private int defaultOutput;

	
	public DyeRecipe(ItemStack source, int red, int yellow, int blue, int white, int defaultOutput) {
		
		this.source = source;
		this.red = new FluidStack(ModFluids.redDye, red);
		this.yellow = new FluidStack(ModFluids.yellowDye, yellow);
		this.blue = new FluidStack(ModFluids.blueDye, blue);
		this.white = new FluidStack(ModFluids.whiteDye, white);
		this.defaultOutput = defaultOutput;		
	}
		
	public int getRed() {

		return red.amount;
	}
	
	public int getYellow() {

		return yellow.amount;
	}
	
	public int getBlue() {

		return blue.amount;
	}
	
	public int getWhite() {

		return white.amount;
	}
	
	public FluidStack getRedFluid() {
		
		return red;
	}
	
	public FluidStack getYellowFluid() {
		
		return yellow;
	}
	
	public FluidStack getBlueFluid() {
		
		return blue;
	}
	
	public FluidStack getWhiteFluid() {
		
		return white;
	}
	
	public int getOutputAmount(FluidStack f) {
		
		if (f == null)
			return 0;
		
		if (f.isFluidEqual(red))
			return getRed();
		
		if (f.isFluidEqual(yellow))
			return getYellow();
		
		if (f.isFluidEqual(blue))
			return getBlue();
		
		if (f.isFluidEqual(white))
			return getWhite();
		
		return 0;
	}
	
	public FluidStack getOutputDefault() {
		
		if (defaultOutput == RED)
			return red;
		else if (defaultOutput == YELLOW)
			return yellow;
		else if (defaultOutput == BLUE)
			return blue;
		else
			return white;		
	}
	
	public FluidStack getOutput(FluidStack f) {
		
		if (f == null)
			return null;
		
		if (f.isFluidEqual(red))
			return red;
		
		if (f.isFluidEqual(yellow))
			return yellow;
		
		if (f.isFluidEqual(blue))
			return blue;
		
		if (f.isFluidEqual(white))
			return white;
		
		return null;
	}
	
	public boolean isOutputFluid(FluidStack f) {
						
		if (f.isFluidEqual(red))
			return true;
		
		if (f.isFluidEqual(yellow))
			return true;
		
		if (f.isFluidEqual(blue))
			return true;
		
		if (f.isFluidEqual(white))
			return true;
		
		return false;
	}
	
	@Override
	public String toString() {

		return String.format("DyeRecipe: red=%s yellow=%s blue=%s white=%s", red.amount, yellow.amount, blue.amount, white.amount);
	}
	
}
