package com.ipsis.mackit.tileentity;

public interface IPoweredSM {

	public abstract boolean isMachineReady();
	public abstract void clearRecipe();
	public abstract void setRecipe();
	public abstract void produceOutput();
	public abstract int getRecipeEnergy();
}
