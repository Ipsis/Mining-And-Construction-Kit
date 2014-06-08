package com.ipsis.mackit.manager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

import com.ipsis.mackit.block.machinesm.IMachineRecipe;
import com.ipsis.mackit.fluid.MKFluids;
import com.ipsis.mackit.helper.ConfigHelper;
import com.ipsis.mackit.helper.DyeHelper;
import com.ipsis.mackit.helper.DyeHelper.DyeColor;

public class StamperManager {
	
	private static List<DyeHelper.DyeColor> outputList;
	public static StamperRecipe RECIPE = new StamperRecipe();
	
	public StamperManager() {
		
		outputList = new LinkedList<DyeHelper.DyeColor>();
		
		if (ConfigHelper.disableStamperInkSac == false) outputList.add(DyeColor.BLACK);
		outputList.add(DyeColor.RED);
		outputList.add(DyeColor.GREEN);
		if (ConfigHelper.disableStamperCocoaBeans == false) outputList.add(DyeColor.BROWN);		
		if (ConfigHelper.disableStamperLapis == false) outputList.add(DyeColor.BLUE);
		outputList.add(DyeColor.PURPLE);
		outputList.add(DyeColor.CYAN);
		outputList.add(DyeColor.LIGHTGRAY);
		outputList.add(DyeColor.GRAY);
		outputList.add(DyeColor.PINK);
		outputList.add(DyeColor.LIME);
		outputList.add(DyeColor.YELLOW);
		outputList.add(DyeColor.LIGHTBLUE);
		outputList.add(DyeColor.MAGENTA);
		outputList.add(DyeColor.ORANGE);
		if (ConfigHelper.disableStamperBonemeal == false) outputList.add(DyeColor.WHITE);
	}
	
	public ItemStack getOutput(int idx) {
		
		if (idx < 0 || idx >= outputList.size())
			return null;
		
		return outputList.get(idx).getItemStack();
	}
	
	public IIcon getIcon(int idx) {
	
		if (idx < 0 || idx >= outputList.size())
			return null;
		
		return outputList.get(idx).getIcon();
	}
	
	public int getFirstIdx() {
		
		if (outputList.isEmpty())
			return -1;
		
		return 0;
	}
	
	public int getLastIdx() {
		
		if (outputList.isEmpty())
			return -1;
		
		return outputList.size() - 1;
	}
	
	public int getNextIdx(int idx) {
		
		if (outputList.isEmpty())
			return -1;
		
		int next = idx + 1;
		if (next > outputList.size() - 1)
			return getFirstIdx();

		return next;
	}
	
	public int getPrevIdx(int idx) {
		
		if (outputList.isEmpty())
			return -1;
		
		int prev = idx - 1;
		if (prev < 0)
			return outputList.size() - 1;
		
		return prev;		
	}
	
	public static class StamperRecipe implements IMachineRecipe {

		private static final int PURE_FLUID_AMOUNT = DyeHelper.DYE_BASE_AMOUNT;
		private static final int RECIPE_ENERGY = 40;
		
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
}
