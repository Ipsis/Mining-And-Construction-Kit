package ipsis.mackit.tileentity;

import ipsis.mackit.core.util.LogHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;

public abstract class TileMachine extends TileEntity implements IPowerReceptor {

	private static enum RunStates { INIT, STOPPED, READY, RUNNING, CONSUME, PRODUCE };
	
	private PowerHandler powerHandler;
	private int consumedEnergy;
	protected int recipeEnergy;
	private RunStates currState;
	

	public TileMachine() {
		powerHandler = new PowerHandler(this, Type.MACHINE);
		powerHandler.configure(1, 15, 100, 32000);
		powerHandler.configurePowerPerdition(1, 200);
		
		currState = RunStates.INIT;
	}
	
	public int getScaledProgress(int scale) {
		if (recipeEnergy == 0)
			return 0;
		
		return (Math.round(((float)consumedEnergy / recipeEnergy) * scale));
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
	public abstract boolean isMachineReady();
	
	/**
	 * How much energy to use per tick
	 * @return
	 */
	public int getMachineTickEnergy() {
		return 1;
	}
	
	/**
	 * Clear the current recipe
	 */
	public abstract void clearSavedRecipeSource();
	
	/**
	 * Convert the source items into the output
	 */
	public abstract void createOutput();
	
	/**
	 * Calculate the recipe for the source items
	 */
	public abstract void setRecipeSource();
	
	/**
	 * Have the source items been changed
	 * @return
	 */
	public abstract boolean hasSourceChanged();

	
	private void runStateMachine() {
		
		RunStates lastState = currState;
		
		/* Can we switch state */
		switch (currState) {
		case INIT:
			currState = RunStates.STOPPED;
			LogHelper.severe("INIT->STOPPED");
			break;
		case STOPPED:
			if (!isRsDisabled() && isMachineReady()) {
				currState = RunStates.READY;
				LogHelper.severe("STOPPED->READY");
			}
			break;
		case RUNNING:
			LogHelper.severe(consumedEnergy + " " + recipeEnergy);
			if (hasSourceChanged()) {
				currState = RunStates.STOPPED;
				LogHelper.severe("RUNNING->STOPPED");
			} else	if (consumedEnergy >= recipeEnergy) {
				currState = RunStates.PRODUCE;
				LogHelper.severe("CONSUME->PRODUCE");
			} else if (consumedEnergy < recipeEnergy && powerHandler.getEnergyStored() > getMachineTickEnergy()) {
				currState = RunStates.CONSUME;
				LogHelper.severe("RUNNING->CONSUME");
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
				clearSavedRecipeSource();
				break;
			case READY:
				setRecipeSource();
				consumedEnergy = 0;
				currState = RunStates.RUNNING;
				LogHelper.severe("READY->RUNNING");
				break;
			case CONSUME:
				powerHandler.useEnergy(10, 10, true);
				consumedEnergy += 10;
				currState = RunStates.RUNNING;
				LogHelper.severe("CONSUME->RUNNING");
				break;
			case PRODUCE:
				createOutput();
				currState = RunStates.STOPPED;
				LogHelper.severe("PRODUCE->STOPPED");
				break;
			case RUNNING:
				break;
			default:
				break;
			
			}
		}
		

	}

}
