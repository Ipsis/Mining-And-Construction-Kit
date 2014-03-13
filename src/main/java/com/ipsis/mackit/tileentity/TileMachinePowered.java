package com.ipsis.mackit.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

import com.ipsis.mackit.helper.Helper;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.network.PacketTypeHandler;
import com.ipsis.mackit.network.packet.PacketTileUpdate;

public abstract class TileMachinePowered extends TileMachineInventory implements IEnergyHandler {
	
	private static final int DEF_RF_PER_TICK = 10;
	
	public EnergyStorage storage;
	private State currState;	
	private int rfPerTick;
	private int energyConsumed;	
	private boolean inventoryChanged;
	private ForgeDirection facing;
	private boolean isActive;
	
	public TileMachinePowered(int capacity) {
		
		this(capacity, DEF_RF_PER_TICK);		
	}
	
	public TileMachinePowered(int capacity, int rfPerTick) {
		
		super();
		this.rfPerTick = rfPerTick;
		storage = new EnergyStorage(capacity);
		currState = State.INIT;
		inventoryChanged = true;
		facing = ForgeDirection.SOUTH;
		
		/* TODO fake fill for now */
		storage.setEnergyStored(capacity);
	}
	
	public int getEnergyConsumed() {
		
		return energyConsumed;
	}
	
	public void setFacing(ForgeDirection dir) {
		
		if (worldObj != null)
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		
		facing = dir;
	}
	
	public ForgeDirection getFacing() {
		
		return facing;
	}
	
	/*
	 * TileEntity
	 */
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		
		super.readFromNBT(compound);
		storage.readFromNBT(compound);
		
		energyConsumed = compound.getInteger("Consumed");
		currState = State.values()[compound.getByte("CurrState")];
		isActive = compound.getBoolean("IsActive");
		facing = Helper.readFromNBTForgeDirection("Facing", compound);
		inventoryChanged = true;
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		
		super.writeToNBT(compound);
		storage.writeToNBT(compound);
		
		compound.setInteger("Consumed", energyConsumed);
		compound.setByte("CurrState", (byte)currState.ordinal());
		compound.setBoolean("IsActive", isActive);
		Helper.writeToNBTForgeDirection("Facing", facing, compound);		
	}
	
	@Override
	public Packet getDescriptionPacket() {

		return PacketTypeHandler.populatePacket(new PacketTileUpdate(xCoord, yCoord, zCoord, facing, isActive, ""));
	}
	
	@Override
	public void onInventoryChanged() {
		
		super.onInventoryChanged();
		inventoryChanged = true;
	}
	
	@Override
	public void updateEntity() {
						
		if (!worldObj.isRemote)
			runSM();
	}
	
	/*
	 * IEnergyHandler
	 */
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {

		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {

		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public boolean canInterface(ForgeDirection from) {

		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {

		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {

		return storage.getMaxEnergyStored();
	}

	/*
	 * State machine
	 */
	private static enum State { INIT, STOPPED, READY, RUNNING, CONSUME, PRODUCE };
	
	/*
	 * Returns true if the value changed
	 */
	private boolean setIsActive(boolean isActive) {
		
		boolean changed = false;
		if (this.isActive != isActive) {
			changed = true;
			this.isActive = isActive;
		}
		
		return changed;
	}
	
	private void runSM() {
		
		State lastState = currState;
		boolean changedIsActive = false;
		
		switch (currState) {
		case INIT:
			currState = State.STOPPED;
			break;
		case STOPPED:
			if (!isRsDisabled() && inventoryChanged && isMachineReady())
				currState = State.READY;
			inventoryChanged = false;
			break;
		case RUNNING:
			if (inventoryChanged) {
				if (!isMachineReady()) {
					currState = State.STOPPED;
					changedIsActive = setIsActive(false);
				}
				
				inventoryChanged = false;
			} else if (energyConsumed >= getRecipeEnergy()) {
				currState = State.PRODUCE;
			} else if (energyConsumed < getRecipeEnergy() && storage.extractEnergy(rfPerTick, true) == rfPerTick) {
				currState = State.CONSUME;
			}
			break;
		case READY:
		case CONSUME:
		case PRODUCE:
			/* Non-conditional transfer */
			break;
		default:
			break;
		}
		
		if (lastState != currState) {
			switch (currState) {
			case INIT:
				break;
			case STOPPED:
				energyConsumed = 0;
				clearRecipe();
				break;
			case READY:
				setRecipe();
				currState = State.RUNNING;
				changedIsActive = setIsActive(true);
				break;
			case RUNNING:				
				break;
			case CONSUME:
				storage.extractEnergy(rfPerTick, false);
				energyConsumed += rfPerTick;
				currState = State.RUNNING;
				break;
			case PRODUCE:
				if (isMachineReady())
					produceOutput();
				currState = State.STOPPED;
				energyConsumed = 0;
				changedIsActive = setIsActive(false);
				break;
			default:
				break;
			}
		}
	}
	
	/*
	 * return the GUI id of the tileentity (-1 if no GUI)
	 */
	public abstract int getGuiID();
	
	
	public boolean isRsDisabled() {
		
		return false;
	}
	
	/*
	 * server->client update information
	 * Used ONLY for updating the client.
	 * The state machine will call setRecipe which will set the 
	 * recipe energy on the server
	 * The client needs this to work out the progress bar value.
	 */	
	public void setEnergyConsumed(int consumed) {
		
		energyConsumed = consumed;
	}
	
	public abstract void setRecipeEnergy(int energy);
	
	/*
	 * Implemented by the subclasses.
	 * The per-machine actions that are driven from the state machine
	 */
	public boolean isMachineReady() {
		
		return false;
	}
	
	public void clearRecipe() {
		
	}
	
	public void setRecipe() {
		
	}
	
	public void produceOutput() {
		
	}
	
	public int getRecipeEnergy() {
		
		return 1;
	}
	
	
		
}
