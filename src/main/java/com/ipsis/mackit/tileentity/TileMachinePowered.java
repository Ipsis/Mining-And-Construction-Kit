package com.ipsis.mackit.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

import com.ipsis.mackit.helper.Helper;

import cpw.mods.fml.common.network.PacketDispatcher;

public abstract class TileMachinePowered extends TileMachineInventory implements IEnergyHandler {
	
	private static final int DEF_RF_PER_TICK = 1000;
	
	public EnergyStorage storage;
	private State currState;	
	private int rfPerTick;
	private int energyConsumed;	
	private boolean inventoryChanged;
	private ForgeDirection facing;
	
	public TileMachinePowered(int capacity) {
		
		this(capacity, DEF_RF_PER_TICK);		
	}
	
	public TileMachinePowered(int capacity, int rfPerTick) {
		
		super();
		this.rfPerTick = rfPerTick;
		storage = new EnergyStorage(capacity);
		currState = State.INIT;
		inventoryChanged = false;
		facing = ForgeDirection.SOUTH;
	}
	
	/*
	private Packet getDescriptionPacket() {
		Object[] toSend = {xCoord, yCoord, zCoord, facing.ordinal(), isActive};
	}*/
	
	public void setFacing(ForgeDirection dir) {
		
		facing = dir;
		if (worldObj != null)
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		//PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 50, worldObj.provider.dimensionId, getDescriptionPacket());
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
	private boolean isActive;
	
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
			if (!isRsDisabled() && isMachineReady())
				currState = State.READY;
			break;
		case RUNNING:
			if (inventoryChanged && !isMachineReady()) {
				currState = State.STOPPED;
				changedIsActive = setIsActive(false);
			} else if (energyConsumed > getRecipeEnergy()) {
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
				currState = State.RUNNING;
				break;
			case PRODUCE:
				if (isMachineReady())
					produceOutput();
				currState = State.STOPPED;
				changedIsActive = setIsActive(false);
				break;
			default:
				break;
			}
		}
	}
	
	public int getEnergyConsumed() {
		
		return energyConsumed;
	}
	
	public void setEnergyConsumed(int consumed) {
		
		energyConsumed = consumed;
	}
		
	protected abstract boolean isMachineReady();
	
	/* server->client gui update only */
	public abstract void setRecipeEnergy(int energy);
	public abstract int getRecipeEnergy();
	
	public abstract void openGui(World world, int x, int y, int z, EntityPlayer player);
	
	protected abstract void clearRecipe();
	protected abstract void setRecipe();
	protected abstract void produceOutput();
	
	public boolean isRsDisabled() {
		
		return false;
	}
	
		
}
