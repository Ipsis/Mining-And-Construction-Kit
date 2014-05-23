package com.ipsis.mackit.block.machinesm;

public interface IFactorySM {

	public boolean isOutputValid(IMachineRecipe recipe);
	public boolean isEnergyAvailable(int amount);

	public void consumeInputs(IMachineRecipe recipe);
	public void createOutputs(IMachineRecipe recipe);
	
	public void consumeEnergy(int amount);
	public int getConsumedEnergy();
	public void resetConsumedEnergy();
	
	public IMachineRecipe getRecipe();
}
