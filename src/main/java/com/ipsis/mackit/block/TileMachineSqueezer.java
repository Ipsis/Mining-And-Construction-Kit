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
import com.ipsis.mackit.helper.DyeHelper;
import com.ipsis.mackit.manager.MKManagers;
import com.ipsis.mackit.manager.SqueezerManager;
import com.ipsis.mackit.manager.TankManager;

public class TileMachineSqueezer extends TileMachine implements IFactorySM, IFacingMachine, IRecipeManager, ISidedInventory, IFluidHandler {
	
	private FactorySM sm;
	private ForgeDirection facing;
	private int consumedEnergy;
	public TankManager tankMgr;
	
	private static final int TANK_SIZE = DyeHelper.DYE_BASE_AMOUNT * 100;
	private static final int PURE_FLUID_AMOUNT = DyeHelper.DYE_BASE_AMOUNT;
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
	
	public boolean getRunning() {
		
		return sm.getRunning();
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

		SqueezerManager.SqueezerRecipe r = (SqueezerManager.SqueezerRecipe)recipe;
		
		/* Output is valid if at least 1 of the dyes that will be produced will fit into an internal tank.
		 * Anything else will be lost.
		 */
		
		int amount;
		amount = r.getRed().amount;
		if (amount != 0 && tankMgr.getTank(RED_TANK).fill(r.getRed(), false) == amount)
			return true;
		
		amount = r.getYellow().amount;
		if (amount != 0 && tankMgr.getTank(YELLOW_TANK).fill(r.getYellow(), false) == amount)
			return true;
		
		amount = r.getBlue().amount;
		if (amount != 0 && tankMgr.getTank(BLUE_TANK).fill(r.getBlue(), false) == amount)
			return true;
		
		amount = r.getWhite().amount;
		if (amount != 0 && tankMgr.getTank(WHITE_TANK).fill(r.getWhite(), false) == amount)
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
		
		/* Need equal amounts of each dye */
		int level = DyeHelper.DYE_BASE_AMOUNT / 4;
		
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
		
			if (tankMgr.getTank(PURE_TANK).fill(PURE, false) != DyeHelper.DYE_BASE_AMOUNT)
				return;
							
			int amount = DyeHelper.DYE_BASE_AMOUNT / 4;
			tankMgr.getTank(RED_TANK).drain(amount, true);
			tankMgr.getTank(YELLOW_TANK).drain(amount, true);
			tankMgr.getTank(BLUE_TANK).drain(amount, true);
			tankMgr.getTank(WHITE_TANK).drain(amount, true);
			tankMgr.getTank(PURE_TANK).fill(PURE, true);
		}
		
	}

	@Override
	public void createOutputs(IMachineRecipe recipe) {
		
		SqueezerManager.SqueezerRecipe r = (SqueezerManager.SqueezerRecipe)recipe;
	
		tankMgr.getTank(RED_TANK).fill(r.getRed(), true);
		tankMgr.getTank(YELLOW_TANK).fill(r.getYellow(), true);
		tankMgr.getTank(BLUE_TANK).fill(r.getBlue(), true);
		tankMgr.getTank(WHITE_TANK).fill(r.getWhite(), true);
		
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
		sm.writeToNBT(nbttagcompound);
		tankMgr.writeToNBT(nbttagcompound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		facing = ForgeDirection.getOrientation((int)nbttagcompound.getByte("Facing"));
		consumedEnergy = nbttagcompound.getInteger("ConsumedEnergy");
		sm.readFromNBT(nbttagcompound);
		tankMgr.readFromNBT(nbttagcompound);
	}
	
	/************
	 * Packets
	 ************/
	@Override
	public Packet getDescriptionPacket() {

		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setByte("Facing", (byte)facing.ordinal());
		sm.writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
	
		NBTTagCompound nbttagcompound = pkt.func_148857_g();
		facing = ForgeDirection.getOrientation((int)nbttagcompound.getByte("Facing"));
		sm.readFromNBT(nbttagcompound);
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
