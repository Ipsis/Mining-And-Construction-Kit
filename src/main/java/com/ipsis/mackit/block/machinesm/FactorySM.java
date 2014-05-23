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
		
		//System.out.println("resetSM");
		this.runningRecipe = null;
		this.recipeEnergy = 0;
		machine.resetConsumedEnergy();
		setRunning(false);
	}
	
	private void setRunning(boolean running) {
		
		if (this.running != running) {
			this.running = running;
			/* update the client */
		}
	}
	
	public boolean isFinished() {
		
		if (runningRecipe == null)
			return false;
		
		//System.out.println("isFinished? " + machine.getConsumedEnergy() + " - " + runningRecipe.getEnergy());
		if (machine.getConsumedEnergy() >= runningRecipe.getEnergy())
				return true;
		
		return false;
	}
	
	public void run() {
		
		//System.out.println("Run: " + runningRecipe + " " + this.running);

		IMachineRecipe currRecipe = machine.getRecipe();
		
		if (currRecipe == null) {
			resetSM();
			//System.out.println("No recipe");
			return;
		}
		
		//System.out.println(currRecipe + " " + runningRecipe);
		if (runningRecipe != null && currRecipe != runningRecipe) {
		//if (currRecipe.equals(runningRecipe)) {
			resetSM();
			return;
		}
		
		runningRecipe = currRecipe;
		recipeEnergy = runningRecipe.getEnergy();
		
		if (!machine.isOutputValid(runningRecipe)) {
			resetSM();
			return;
		}
		
		if (!machine.isEnergyAvailable(runningRecipe.getEnergyTick())) {
			setRunning(false);		
			return;
		}
		
		setRunning(true);
		machine.consumeEnergy(runningRecipe.getEnergyTick());
		
		if (!isFinished())
			return;
		
		/* Processing is finished, create the output */
		machine.consumeInputs(runningRecipe);
		machine.createOutputs(runningRecipe);
		
		/* reset progress but dont update the running state */
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
