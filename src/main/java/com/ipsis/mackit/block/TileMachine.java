package com.ipsis.mackit.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;

public class TileMachine extends TileInventory implements IEnergyHandler  {

	private EnergyStorage energyStorage;
	
	public TileMachine(int rfStorage) {
				
		energyStorage = new EnergyStorage(rfStorage);
	}
	
	public EnergyStorage getEnergyStorage() {
		
		return energyStorage;
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
}
