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
import com.ipsis.mackit.helper.DyeHelper;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.manager.TankManager;

public class TileMachineDyeFiller extends TileMachine implements IFactorySM, IFacingMachine, IRecipeManager, IFluidHandler, ISidedInventory {

	private FactorySM sm;
	private ForgeDirection facing;
	private int consumedEnergy;
	public TankManager tankMgr;
	private int selected;
	
	private static final int TANK_SIZE = DyeHelper.DYE_BASE_AMOUNT * 100;
	private static final int ENERGY_STORAGE_SIZE = 32000;
		
	public static final int CHARGE_SLOT = 0;
	
	public static final String PURE_TANK = "pure";
	
	public TileMachineDyeFiller() {
		
		super(ENERGY_STORAGE_SIZE);
		sm = new FactorySM(this);
		inventory = new ItemStack[1];
		facing = ForgeDirection.EAST;
		consumedEnergy = 0;
		selected = 0;
		
		tankMgr = new TankManager();
		tankMgr.addTank(PURE_TANK, TANK_SIZE);
	}
	
	public boolean getRunning() {
		
		return sm.getRunning();
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

		return false;
	}

	@Override
	public boolean isEnergyAvailable(int amount) {

		return amount == getEnergyStorage().extractEnergy(amount, true);
	}

	@Override
	public void consumeInputs(IMachineRecipe recipe) {

	}

	@Override
	public void createOutputs(IMachineRecipe recipe) {

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

		return null;
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
	
	/***************
	 * IFluidHandler
	 * 
	 * Can only fill the pure tank
	 ***************/

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

		return tankMgr.getTank(PURE_TANK).fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {

		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {

		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {

		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {

		return tankMgr.getTankInfo(from);
	}
	
	/*****************
	 * ISidedInventory
	 *****************/
	
	private static final int[] accessSlots = new int[]{ CHARGE_SLOT };

	@Override
	public int[] getAccessibleSlotsFromSide(int slot) {

		return accessSlots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int side) {

		if (slot != CHARGE_SLOT)
			return false;
		
		return true;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {

		if (slot != CHARGE_SLOT)
			return false;
		
		return true;
	}	
}
