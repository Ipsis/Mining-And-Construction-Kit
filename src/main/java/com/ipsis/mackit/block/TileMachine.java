package com.ipsis.mackit.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.tileentity.IEnergyInfo;

public class TileMachine extends TileInventory implements IEnergyHandler, IEnergyInfo  {

	private EnergyStorage energyStorage;
	private final int ENERGY_PER_TICK = 40;
	
	public TileMachine(int rfStorage) {
				
		energyStorage = new EnergyStorage(rfStorage);
	}
	
	public EnergyStorage getEnergyStorage() {
		
		return energyStorage;
	}
	
	public void updateRunning(boolean running) {
		
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		super.readFromNBT(nbt);
		energyStorage.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		super.writeToNBT(nbt);
		energyStorage.writeToNBT(nbt);
	}
		
	/****************
	 * IEnergyHandler
	 ****************/
	public boolean canConnectEnergy(ForgeDirection from) {

		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {

		return energyStorage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {

		return energyStorage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {

		return energyStorage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {

		return energyStorage.getMaxEnergyStored();
	}
	
	/*************
	 * IEnergyInfo
	 *************/

	@Override
	public int getInfoEnergyPerTick() {

		return ENERGY_PER_TICK;
	}

	@Override
	public int getInfoMaxEnergyPerTick() {

		return ENERGY_PER_TICK;
	}

	@Override
	public int getInfoEnergy() {

		return energyStorage.getEnergyStored();
	}

	@Override
	public int getInfoMaxEnergy() {

		return energyStorage.getMaxEnergyStored();
	}
}
