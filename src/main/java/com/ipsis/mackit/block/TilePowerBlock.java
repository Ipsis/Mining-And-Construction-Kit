package com.ipsis.mackit.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

import com.ipsis.cofhlib.util.EnergyHelper;

public class TilePowerBlock extends TileEntity implements IFacing, IEnergyHandler {

	private EnergyStorage energy = new EnergyStorage(5000000);
	
	public TilePowerBlock() {
		
		energy.setEnergyStored(5000000);		
	}
	
	/*********
	 * IFacing
	 *********/
	@Override
	public ForgeDirection getFacing() {
		
		return ForgeDirection.EAST;
	}
	
	@Override
	public void setFacing(ForgeDirection d) {
		
	}
	
	@Override
	public void updateEntity() {

		/* For each direction check for a tile next-door and have it receive the energy */
	     for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
	    	 
	    	 EnergyHelper.insertEnergyIntoAdjacentEnergyHandler((TileEntity)this, side.ordinal(), 100, false);
	    	 
	    	 /*
	    	 TileEntity te = BlockHelper.getAdjacentTileEntity(this.worldObj, xCoord, yCoord, zCoord, side);
	    	 

	         if ((te instanceof IEnergyHandler)) {
	        	 IEnergyHandler teh = (IEnergyHandler)te;
	        	 energy.extractEnergy(teh.receiveEnergy(side.getOpposite(), energy.extractEnergy(100, true), false), false);	           
	         } */
	     }
	}


	/****************
	 * IEnergyHandler
	 ****************/

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {

		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		
		/* Only transmits energy */
		return 0;
	}


	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {

		/* We dont lose the energy */
		return maxExtract;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {

		return energy.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		
		return energy.getMaxEnergyStored();
	}
}
