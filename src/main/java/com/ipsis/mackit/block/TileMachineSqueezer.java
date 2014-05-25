package com.ipsis.mackit.block;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.ipsis.mackit.block.machinesm.FactorySM;
import com.ipsis.mackit.block.machinesm.IFactorySM;
import com.ipsis.mackit.block.machinesm.IMachineRecipe;
import com.ipsis.mackit.block.machinesm.IRecipeManager;
import com.ipsis.mackit.fluid.MKFluids;
import com.ipsis.mackit.manager.DyeRecipe;
import com.ipsis.mackit.manager.MKManagers;
import com.ipsis.mackit.manager.SqueezerRecipe;
import com.ipsis.mackit.manager.TankManager;

public class TileMachineSqueezer extends TileMachine implements IFactorySM, IFacing, IRecipeManager, ISidedInventory, IFluidHandler {
	
	private FactorySM sm;
	private ForgeDirection facing;
	private int consumedEnergy;
	public TankManager tankMgr;
	
	private static final int TANK_SIZE = 5000;
	private static final int PURE_FLUID_AMOUNT = 100;
	private static final int ENERGY_STORAGE_SIZE = 32000;
	
	public static final int INPUT_SLOT = 0;
	
	public static final String RED_TANK = "red";
	public static final String YELLOW_TANK = "yellow";
	public static final String BLUE_TANK = "blue";
	public static final String WHITE_TANK = "white";
	public static final String PURE_TANK = "pure";
	
	private static FluidStack PURE = new FluidStack(MKFluids.fluidDyePure, PURE_FLUID_AMOUNT);
	
	public TileMachineSqueezer() {
		
		super(ENERGY_STORAGE_SIZE);
		sm = new FactorySM(this);
		inventory = new ItemStack[1];
		facing = ForgeDirection.EAST;
		consumedEnergy = 0;
		
		tankMgr = new TankManager();
		tankMgr.addTank(RED_TANK, TANK_SIZE);
		tankMgr.addTank(YELLOW_TANK, TANK_SIZE);
		tankMgr.addTank(BLUE_TANK, TANK_SIZE);
		tankMgr.addTank(WHITE_TANK, TANK_SIZE);
		tankMgr.addTank(PURE_TANK, TANK_SIZE);
	}
	
	@Override
	public void setFacing(ForgeDirection facing) {
		
		this.facing = facing;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	@Override
	public ForgeDirection getFacing() {
		
		return facing;
	}
	
	@Override
	public void updateEntity() {

		if (worldObj.isRemote)
			return;
		
		sm.run();
	}
	
	public int getScaledProgress(int scale) {
	
		return sm.getScaledProgress(scale);
	}

	/************
	 * IFactorySM
	 ************/	
	@Override
	public boolean isOutputValid(IMachineRecipe recipe) {

		SqueezerRecipe r = (SqueezerRecipe)recipe;
		DyeRecipe d = r.getDyeRecipe();
		
		/* Output is valid if at least 1 of the dyes that will be produced will fit into an internal tank.
		 * Anything else will be lost.
		 */
		
		int amount;
		amount = d.getRed().amount;
		if (amount != 0 && tankMgr.getTank(RED_TANK).fill(d.getRed(), false) == amount)
			return true;
		
		amount = d.getYellow().amount;
		if (amount != 0 && tankMgr.getTank(YELLOW_TANK).fill(d.getYellow(), false) == amount)
			return true;
		
		amount = d.getBlue().amount;
		if (amount != 0 && tankMgr.getTank(BLUE_TANK).fill(d.getBlue(), false) == amount)
			return true;
		
		amount = d.getWhite().amount;
		if (amount != 0 && tankMgr.getTank(WHITE_TANK).fill(d.getWhite(), false) == amount)
			return true;
		
		return false;
	}

	@Override
	public boolean isEnergyAvailable(int amount) {

		return amount == getEnergyStorage().extractEnergy(amount, true);
	}

	@Override
	public void consumeInputs(IMachineRecipe recipe) {

		decrStackSize(INPUT_SLOT, 1);
	}
	
	private boolean checkAmountForPure() {
		
		int level = 30;
		
		if (tankMgr.getTank(RED_TANK).getFluidAmount() < level)
			return false;
		if (tankMgr.getTank(YELLOW_TANK).getFluidAmount() < level)
			return false;
		if (tankMgr.getTank(BLUE_TANK).getFluidAmount() < level)
			return false;
		if (tankMgr.getTank(WHITE_TANK).getFluidAmount() < level)
			return false;
		
		return true;
		
	}
	
	private void tryCreatePure() {
		
		while (checkAmountForPure()) {
		
			if (tankMgr.getTank(PURE_TANK).fill(PURE, false) != 100)
				return;
							
			tankMgr.getTank(RED_TANK).drain(30, true);
			tankMgr.getTank(YELLOW_TANK).drain(30, true);
			tankMgr.getTank(BLUE_TANK).drain(30, true);
			tankMgr.getTank(WHITE_TANK).drain(30, true);
			tankMgr.getTank(PURE_TANK).fill(PURE, true);
		}
		
	}

	@Override
	public void createOutputs(IMachineRecipe recipe) {
		
		SqueezerRecipe r = (SqueezerRecipe)recipe;
		DyeRecipe d = r.getDyeRecipe();
		
		tankMgr.getTank(RED_TANK).fill(d.getRed(), true);
		tankMgr.getTank(YELLOW_TANK).fill(d.getYellow(), true);
		tankMgr.getTank(BLUE_TANK).fill(d.getBlue(), true);
		tankMgr.getTank(WHITE_TANK).fill(d.getWhite(), true);
		
		tryCreatePure();
	}

	@Override
	public void consumeEnergy(int amount) {

		consumedEnergy += getEnergyStorage().extractEnergy(amount, false);
	}

	@Override
	public int getConsumedEnergy() {

		return consumedEnergy;
	}

	@Override
	public void resetConsumedEnergy() {

		consumedEnergy = 0;
	}
	
	@Override
	public int getEnergyTick() {

		return super.getInfoEnergyPerTick();
	}
	
	/* GUI updating only */
	public void setConsumedEnergy(int amount) {
		
		consumedEnergy = amount;
	}
	
	public void setRecipeEnergy(int amount) {
		
		sm.setRecipeEnergy(amount);
	}
	
	public int getRecipeEnergy() {
		
		return sm.getRecipeEnergy();		
	}
	
	/****************
	 * IRecipeManager
	 ****************/
	
	@Override
	public IMachineRecipe getRecipe() {

		return MKManagers.squeezerMgr.getRecipe(inventory[INPUT_SLOT]);
	}
	
	/*****
	 * NBT
	 *****/
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {

		super.writeToNBT(nbttagcompound);
		nbttagcompound.setByte("Facing", (byte)facing.ordinal());
		nbttagcompound.setInteger("ConsumedEnergy", consumedEnergy);
		tankMgr.writeToNBT(nbttagcompound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		facing = ForgeDirection.getOrientation((int)nbttagcompound.getByte("Facing"));
		consumedEnergy = nbttagcompound.getInteger("ConsumedEnergy");
		tankMgr.readFromNBT(nbttagcompound);
	}
	
	/************
	 * Packets
	 ************/
	@Override
	public Packet getDescriptionPacket() {

		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setByte("Facing", (byte)facing.ordinal());
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
	
		facing = ForgeDirection.getOrientation((int)pkt.func_148857_g().getByte("Facing"));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	/*****************
	 * ISidedInventory
	 *****************/

	private static final int[] accessSlots = new int[]{ INPUT_SLOT };
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {

		return accessSlots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
		
		if (slot != INPUT_SLOT)
			return false;
		
		return MKManagers.squeezerMgr.isSqueezable(itemStack);		
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {

		return false;
	}
	
	/***************
	 * IFluidHandler
	 * 
	 * Can only drain the pure tank
	 */
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {

		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

		return tankMgr.getTank(PURE_TANK).drain(maxDrain, doDrain);
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
		
		return tankMgr.getTankInfo(from);
	}

}
