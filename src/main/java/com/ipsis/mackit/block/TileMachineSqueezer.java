package com.ipsis.mackit.block;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import com.ipsis.mackit.block.machinesm.FactorySM;
import com.ipsis.mackit.block.machinesm.IFactorySM;
import com.ipsis.mackit.block.machinesm.IMachineRecipe;
import com.ipsis.mackit.block.machinesm.IRecipeManager;
import com.ipsis.mackit.fluid.MKFluids;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.manager.DyeRecipe;
import com.ipsis.mackit.manager.MKManagers;
import com.ipsis.mackit.manager.SqueezerRecipe;
import com.ipsis.mackit.manager.TankManager;

public class TileMachineSqueezer extends TileMachine implements IFactorySM, IFacing, IRecipeManager {
	
	private FactorySM sm;
	private ForgeDirection facing;
	private int consumedEnergy;
	public TankManager tankMgr;
	
	private static FluidStack PURE = new FluidStack(MKFluids.fluidDyePure, 100);
	
	public static final int INPUT_SLOT = 0;
	
	public static final String RED_TANK = "red";
	public static final String YELLOW_TANK = "yellow";
	public static final String BLUE_TANK = "blue";
	public static final String WHITE_TANK = "white";
	public static final String PURE_TANK = "pure";
	
	public TileMachineSqueezer() {
		
		super(32000);
		sm = new FactorySM(this);
		inventory = new ItemStack[1];
		facing = ForgeDirection.EAST;
		consumedEnergy = 0;
		
		tankMgr = new TankManager();
		tankMgr.addTank(RED_TANK, 5000);
		tankMgr.addTank(YELLOW_TANK, 5000);
		tankMgr.addTank(BLUE_TANK, 5000);
		tankMgr.addTank(WHITE_TANK, 5000);
		tankMgr.addTank(PURE_TANK, 5000);
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
		
		return true;
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
		
		if (!checkAmountForPure())
			return;
		
		if (tankMgr.getTank(PURE_TANK).fill(PURE, false) != 100)
			return;
						
		tankMgr.getTank(RED_TANK).drain(30, true);
		tankMgr.getTank(YELLOW_TANK).drain(30, true);
		tankMgr.getTank(BLUE_TANK).drain(30, true);
		tankMgr.getTank(WHITE_TANK).drain(30, true);
		tankMgr.getTank(PURE_TANK).fill(PURE, true);
		
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
		nbttagcompound.setInteger("Energy", consumedEnergy);
		tankMgr.writeToNBT(nbttagcompound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		facing = ForgeDirection.getOrientation((int)nbttagcompound.getByte("Facing"));
		consumedEnergy = nbttagcompound.getInteger("Energy");
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

}
