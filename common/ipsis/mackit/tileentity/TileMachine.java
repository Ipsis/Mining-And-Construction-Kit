package ipsis.mackit.tileentity;

import ipsis.mackit.core.util.LogHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;

public abstract class TileMachine extends TileEntity implements IPowerReceptor {

	private static enum RunStates { INIT, STOPPED, READY, RUNNING, CONSUME, PRODUCE };
	private static boolean isActive[] = new boolean[]{ false, false, false, true, true, true }; 
	
	private static final int DEF_MIN_RX_ENERGY = 1;
	private static final int DEF_MAX_RX_ENERGY = 500;
	private static final int DEF_DOWORK_ENERGY = 1600; /* dont use ?? */
	private static final int DEF_STORED_ENERGY = 1500;
	private static final int DEF_TICK_ENERGY = 2;
	
	private PowerHandler powerHandler;
	private int consumedEnergy;
	private int recipeEnergy;
	private int tickEnergy;
	private RunStates currState;
	private boolean isInventoryOk;
	
	public TileMachine(int tickEnergy) {
		powerHandler = new PowerHandler(this, Type.MACHINE);
		powerHandler.configure(DEF_MIN_RX_ENERGY, DEF_MAX_RX_ENERGY, DEF_DOWORK_ENERGY, DEF_STORED_ENERGY);
		powerHandler.configurePowerPerdition(1, 200);
		currState = RunStates.INIT;
		isInventoryOk = false;
		
		this.tickEnergy = tickEnergy;
	}

	public TileMachine() {
		this(DEF_TICK_ENERGY);
	}
	
	
	@Override
	public void doWork(PowerHandler workProvider) {
		/* work done in updateEntity */
		return;
	}
	
	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return powerHandler.getPowerReceiver();
	}

	@Override
	public World getWorld() {
		return worldObj;
	}
	
	public void setIsInventoryOk(boolean v) {
		isInventoryOk = v;
	}
	
	/*
	 * Ticks
	 */
	@Override
	public void updateEntity() {
		if (!worldObj.isRemote)
			runStateMachine();
	}
	
	/**
	 * Is the machine disabled by a redstone signal
	 * @return
	 */
	public boolean isRsDisabled() {
		return false;
	}
	
	/**
	 * Are the source items available
	 * Is there space in the output
	 * @return
	 */
	public abstract boolean canMachineStart();
	

	/**
	 * Clear the current recipe
	 */
	public abstract void clearSavedRecipe();
	
	/**
	 * Convert the source items into the output
	 */
	public abstract void createOutput();
	
	/**
	 * Calculate the recipe for the source items
	 */
	public abstract void setRecipe();
	
	/**
	 * Get the energy for the recipe
	 */
	public abstract int getRecipeEnergy();

	
	private void runStateMachine() {
		
		RunStates lastState = currState;
		
		/* Can we switch state */
		switch (currState) {
		case INIT:
			currState = RunStates.STOPPED;
			break;
		case STOPPED:
			if (!isRsDisabled() && canMachineStart()) {
				currState = RunStates.READY;
				isInventoryOk = true;
			}
			break;
		case RUNNING:
			if (!isInventoryOk) {
				currState = RunStates.INIT;
				LogHelper.severe("RUNNING->INIT");
			} else	if (consumedEnergy >= recipeEnergy) {
				currState = RunStates.PRODUCE;
			} else if (consumedEnergy < recipeEnergy && powerHandler.getEnergyStored() > tickEnergy) {
				currState = RunStates.CONSUME;
			}
			break;
		case CONSUME:
		case READY:
		case PRODUCE:
			/* Transition state */
			break;
		default:
			break;
		}
		
		if (currState != lastState) {
			
			switch (currState) {
			case STOPPED:
				consumedEnergy = 0;
				clearSavedRecipe();
				break;
			case READY:
				setRecipe();
				recipeEnergy = getRecipeEnergy();
				consumedEnergy = 0;
				currState = RunStates.RUNNING;
				break;
			case CONSUME:
				powerHandler.useEnergy(tickEnergy, tickEnergy, true);
				consumedEnergy += tickEnergy;
				currState = RunStates.RUNNING;
				break;
			case PRODUCE:
				createOutput();
				currState = RunStates.INIT;
				break;
			case RUNNING:
				break;
			default:
				break;			
			}
			
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if (isActive[currState.ordinal()])
				meta |= 0x8;
			else
				meta &= ~0x8;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 3);
			
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		powerHandler.writeToNBT(compound);
		
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("currentState", (byte)currState.ordinal());
		nbt.setInteger("consumed", consumedEnergy);
		nbt.setInteger("recipeEnergy", recipeEnergy);
		nbt.setInteger("tickEnergy", tickEnergy);
		compound.setCompoundTag("baseMachine", nbt);			
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		powerHandler.readFromNBT(compound);
		NBTTagCompound nbt = compound.getCompoundTag("baseMachine");
		
		byte t = nbt.getByte("currentState");
		if (t >= 0 && t < RunStates.values().length)
			currState = RunStates.values()[t];
		else
			currState = RunStates.INIT;

		consumedEnergy = nbt.getInteger("consumedEnergy");
		recipeEnergy = nbt.getInteger("recipeEnergy");
		tickEnergy = nbt.getInteger("tickEnergy");
		
	}
	
	private float lastEnergy;
	
	/* Gui */
	public int getScaledEnergy(int scale) {
		return (Math.round((powerHandler.getEnergyStored() / powerHandler.getMaxEnergyStored()) * scale));			
	}
	
	public int getScaledProgress(int scale) {
		if (recipeEnergy == 0)
			return 0;
		
		return (Math.round(((float)consumedEnergy / recipeEnergy) * scale));
	}
	
	public void sendGUINetworkData(Container container, ICrafting iCrafting) {
		
		/* energy has to be split into 2 * 16bit values - like Buildcraft does */
		float currEnergy = powerHandler.getEnergyStored();
		
		if (lastEnergy != currEnergy) {
			iCrafting.sendProgressBarUpdate(container, 0, Math.round(currEnergy * 10) & 0xffff);
			iCrafting.sendProgressBarUpdate(container, 1, (Math.round(currEnergy * 10) & 0xffff0000) >> 16);
			
			lastEnergy = currEnergy;
		}
	}
	
	/* update from the received info */
	public void getGUINetworkData(int id, int data) {
		
		switch (id) {
		case 0:
			int iEnergy = Math.round(powerHandler.getEnergyStored() * 10);
			iEnergy = (iEnergy & 0xffff0000) | (data & 0xffff);
			powerHandler.setEnergy(iEnergy / 10);
			break;
		case 1:
			iEnergy = Math.round(powerHandler.getEnergyStored() * 10);
			iEnergy = (iEnergy & 0xffff) | ((data & 0xffff) << 16);
			powerHandler.setEnergy(iEnergy / 10);
			break;
		}
	}

}
