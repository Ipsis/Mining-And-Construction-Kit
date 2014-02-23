package com.ipsis.mackit.manager;

import java.util.HashMap;
import java.util.Map;

import com.ipsis.mackit.fluid.ModFluids;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/*
 * OreDict Dye -> Liquid Dye Components
 */
public class SqueezerRecipe {

	private ItemStack source;	/* OriDict dye item */
	private FluidStack red;
	private FluidStack yellow;
	private FluidStack blue;
	private FluidStack white;

	
	public SqueezerRecipe(ItemStack source, int red, int yellow, int blue, int white) {
		
		this.source = source;
		this.red = new FluidStack(ModFluids.redDye, red);
		this.yellow = new FluidStack(ModFluids.yellowDye, yellow);
		this.blue = new FluidStack(ModFluids.blueDye, blue);
		this.white = new FluidStack(ModFluids.whiteDye, white);

			
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
	
	public int getAmount(FluidStack f) {
		
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
	
	public FluidStack getRandomOutput() {
		
		return new FluidStack(ModFluids.blueDye, 100);
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
	

	
}
