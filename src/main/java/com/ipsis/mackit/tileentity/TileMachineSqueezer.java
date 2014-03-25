package com.ipsis.mackit.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.ipsis.mackit.fluid.ModFluids;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.lib.GuiIds;
import com.ipsis.mackit.manager.DyeRecipe;
import com.ipsis.mackit.manager.MKRegistry;
import com.ipsis.mackit.manager.SqueezerRecipe;

/*
 * Inventory of
 * 1 input slot (slot 0)
 * 1 output tank
 */

public class TileMachineSqueezer extends TileMachinePowered implements IPoweredSM, IFluidHandler {

	private static final int RF_CAPACITY = 32000;
	private static final int TANK_CAPACITY = 2500;
	private static final int PURE_TANK_CAPACITY = 10000;
		
	public FluidTank pureTank;
	public FluidTank redTank;
	public FluidTank yellowTank;
	public FluidTank blueTank;
	public FluidTank whiteTank;
	
	
	/* Recipe */
	private static final int RECIPE_RF_ENERGY = 1000;
	private static final int DYE_MB = 192;
	private static final int PURE_MB = 1000;
	private SqueezerRecipe recipe;
	
	
	/* Slots */
	public static final int SLOT_INPUT = 0;
	
	public TileMachineSqueezer() {
		
		super(RF_CAPACITY);
		pureTank = new FluidTank(PURE_TANK_CAPACITY);
		redTank = new FluidTank(TANK_CAPACITY);
		yellowTank = new FluidTank(TANK_CAPACITY);
		blueTank = new FluidTank(TANK_CAPACITY);
		whiteTank = new FluidTank(TANK_CAPACITY);	
	}
	
	@Override
	public int getSizeInventory() {
		
		return 1;
	}
	
	private Fluid getTankDefaultFluid(FluidTank t) {
		
		if (t == redTank)
			return ModFluids.redDye;
		else if (t == yellowTank)
			return ModFluids.yellowDye;
		else if (t == blueTank)
			return ModFluids.blueDye;
		else if (t == whiteTank)
			return ModFluids.whiteDye;
		else
			return ModFluids.pureDye;
		
	}
	
	private boolean canFillBufferTank(DyeRecipe r) {
		
		if (r.getRedFluid() != null && redTank.fill(r.getRedFluid(), false) != r.getRed())
			return false;
		
		if (r.getYellowFluid() != null && yellowTank.fill(r.getYellowFluid(), false) != r.getYellow())
			return false;
			
		if (r.getBlueFluid() != null && blueTank.fill(r.getBlueFluid(), false) != r.getBlue())
			return false;
		
		if (r.getWhiteFluid() != null && whiteTank.fill(r.getWhiteFluid(), false) != r.getWhite())
			return false;
				
		return true;
	}
	
	private void fillBufferTanks(DyeRecipe r) {
		
		if (r.getRed() > 0)
			redTank.fill(r.getRedFluid(), true);
		
		if (r.getYellow() > 0)
			yellowTank.fill(r.getYellowFluid(), true);
		
		if (r.getBlue() > 0)
			blueTank.fill(r.getBlueFluid(), true);
		
		if (r.getWhite() > 0)
			whiteTank.fill(r.getWhiteFluid(), true);
	}
	
	private boolean canFillOutput() {
		
		return redTank.getFluidAmount() >= DYE_MB
				&& yellowTank.getFluidAmount() >= DYE_MB
				&& blueTank.getFluidAmount() >= DYE_MB
				&& whiteTank.getFluidAmount() >= DYE_MB
				&& pureTank.fill(new FluidStack(ModFluids.pureDye, DYE_MB * 4), false) == DYE_MB * 4;
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
		
		SqueezerRecipe r = MKRegistry.getSqueezerManager().getRecipe(getStackInSlot(SLOT_INPUT));
		if (r == null)
			return false;
		
		DyeRecipe sr = MKRegistry.getDyeManager().getRecipe(r.getDye());
		if (sr == null)
			return false;
		
		if (!canFillBufferTank(sr))
			return false;
		
		return true;
	}
	
	@Override
	public void clearRecipe() {
		
		recipe = null;
	}
	
	@Override
	public void setRecipe() {
		
		recipe = MKRegistry.getSqueezerManager().getRecipe(getStackInSlot(SLOT_INPUT));
	}
	
	@Override
	public void produceOutput() {
		
		decrStackSize(SLOT_INPUT, 1);
		
		if (recipe == null)
			return;
		
		DyeRecipe sr = MKRegistry.getDyeManager().getRecipe(recipe.getDye());
		
		LogHelper.severe("produceOutput: " + sr);		
		for (int i = 0; i < recipe.getDye().stackSize; i++)
			fillBufferTanks(sr);
		
		LogHelper.severe("produceOutput: red=" + redTank.getFluidAmount() + " yellow=" + yellowTank.getFluidAmount() + " blue=" + blueTank.getFluidAmount() + " white=" + whiteTank.getFluidAmount());
	}
	
	@Override
	public void postSM() {
		
		/* need to check for energy */
		if (!canFillOutput())
			return;
		
		redTank.drain(DYE_MB, true);
		yellowTank.drain(DYE_MB, true);
		blueTank.drain(DYE_MB, true);
		whiteTank.drain(DYE_MB, true);
		pureTank.fill(new FluidStack(ModFluids.pureDye, DYE_MB * 4), true);
		LogHelper.severe("postSM: pure=" + pureTank.getFluidAmount());
	}
	
	@Override
	public int getRecipeEnergy() {
		
		return RECIPE_RF_ENERGY;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {

		super.readFromNBT(compound);
		
		redTank.readFromNBT(compound);
		yellowTank.readFromNBT(compound);
		blueTank.readFromNBT(compound);
		whiteTank.readFromNBT(compound);
		pureTank.readFromNBT(compound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {

		super.writeToNBT(compound);
		
		redTank.writeToNBT(compound);
		yellowTank.writeToNBT(compound);
		blueTank.writeToNBT(compound);
		whiteTank.writeToNBT(compound);
		pureTank.writeToNBT(compound);
	}
	
	/* Gui Update */
	private void setTankFluidAmount(FluidTank t, int amount) {
		
		if (amount == 0) {
			t.setFluid(null);
			return;
		}
		
		FluidStack f = t.getFluid();
		if (f == null)
			f = new FluidStack(getTankDefaultFluid(t), amount);
		else
			f.amount = amount;
		
		t.setFluid(f);
	}
	
	public void setRedTankAmount(int amount) {
		
		setTankFluidAmount(redTank, amount);
	}
	
	
	public void setYellowTankAmount(int amount) {
		
		setTankFluidAmount(yellowTank, amount);
	}
	
	
	public void setBlueTankAmount(int amount) {
		
		setTankFluidAmount(blueTank, amount);
	}
	
	public void setWhiteTankAmount(int amount) {
		
		setTankFluidAmount(whiteTank, amount);
	}
	
	public void setPureTankAmount(int amount) {
		
		setTankFluidAmount(pureTank, amount);
	}

	/* IFluidHandler */
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

		return 0;
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
		
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {

		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {

		return new FluidTankInfo[] {pureTank.getInfo()};
	}
}
