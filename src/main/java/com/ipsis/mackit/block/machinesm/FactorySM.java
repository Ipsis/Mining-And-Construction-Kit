package com.ipsis.mackit.block.machinesm;

import com.ipsis.mackit.helper.LogHelper;

public class FactorySM {

	private boolean running;
	protected IMachineRecipe runningRecipe;
	private int recipeEnergy;
	private IFactorySM machine;
		
	public FactorySM(IFactorySM m) {
		
		this.machine = m;
		resetSM();	
	}
	
	private void resetSM() {
		
		this.runningRecipe = null;
		this.recipeEnergy = 0;
		machine.resetConsumedEnergy();
		setRunning(false);
	}
	
	public void setRunning(boolean running) {
		
		if (this.running != running) {
			this.running = running;
			this.machine.updateRunning(this.running);
		}
	}
	
	public boolean getRunning() {
		
		return this.running;
	}
	
	public boolean isFinished() {
		
		if (runningRecipe == null)
			return false;
		
		if (machine.getConsumedEnergy() >= runningRecipe.getEnergy())
				return true;
		
		return false;
	}
	
	public void run() {
		
		IMachineRecipe currRecipe = machine.getRecipe();
		
		if (currRecipe == null) {
			resetSM();
			return;
		}
		
		if (runningRecipe != null && currRecipe != runningRecipe) {
			resetSM();
			return;
		}
		
		runningRecipe = currRecipe;
		recipeEnergy = runningRecipe.getEnergy();
		
		if (!machine.isOutputValid(runningRecipe)) {
			resetSM();
			return;
		}
		
		if (!machine.isEnergyAvailable(machine.getEnergyTick())) {
			setRunning(false);		
			return;
		}
		
		setRunning(true);
		machine.consumeEnergy(machine.getEnergyTick());
		
		if (!isFinished())
			return;
		
		/* Processing is finished, create the output */
		machine.consumeInputs(runningRecipe);
		machine.createOutputs(runningRecipe);
		
		/**
		 *  Reset progress but dont update the running state
		 *  This should stop the flickering on/off effect
		 */
		currRecipe = null;
		runningRecipe = null;
		recipeEnergy = 0;
		machine.resetConsumedEnergy();
		
		runningRecipe = machine.getRecipe();
		if (runningRecipe == null)
			resetSM();
	}	
	
	/* GUI update only */
	public void setRecipeEnergy(int amount) {
		
		recipeEnergy = amount;
	}
	
	public int getRecipeEnergy() {
		
		return recipeEnergy;
	}
	
	public int getScaledProgress(int scale) {
		
		/*
		if (isRsDisabled() || getRecipeEnergy() <= 0)
			return 0; */
		
		if (recipeEnergy <= 0)
			return 0;
		
		return (int)(scale * ((float)machine.getConsumedEnergy() / recipeEnergy));
	}
}
