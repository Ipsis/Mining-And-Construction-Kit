package com.ipsis.mackit.manager;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.ipsis.mackit.fluid.MKFluids;

public class DyeRecipe {
	
	public static final int RED = 0;
	public static final int YELLOW = 1;
	public static final int BLUE = 2;
	public static final int WHITE = 3;

	private ItemStack src;
	private FluidStack red;
	private FluidStack yellow;
	private FluidStack blue;
	private FluidStack white;
	
	public DyeRecipe(ItemStack src, int red, int yellow, int blue, int white) {
		
		this.src = src;
		this.red = new FluidStack(MKFluids.fluidDyeRed, red);
		this.yellow = new FluidStack(MKFluids.fluidDyeYellow, yellow);
		this.blue = new FluidStack(MKFluids.fluidDyeBlue, blue);
		this.white = new FluidStack(MKFluids.fluidDyeWhite, white);
	}
	
	public FluidStack getRed() {
		
		return red;
	}
	
	public FluidStack getYellow() {
		
		return yellow;
	}
	
	public FluidStack getBlue() {
		
		return blue;
	}
	
	public FluidStack getWhite() {
		
		return white;
	}
	
}
