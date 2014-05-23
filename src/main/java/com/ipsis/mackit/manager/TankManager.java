package com.ipsis.mackit.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TankManager {
	
	private HashMap<String, FluidTank> tanks;
	
	public TankManager() {
		
		tanks = new HashMap<String, FluidTank>();		
	}
	
	public void addTank(String name, int capacity) {
			
		if (getTank(name) == null)
			tanks.put(name, new FluidTank(capacity));
	}
	
	public FluidTank getTank(String name) {
		
		return tanks.get(name);
	}
	
	/* Server->Client sync only */
	public void setTank(String name, Fluid f, int amount) {
		
		FluidTank t = getTank(name);
		if (t == null)
			return;
		
		t.setFluid(new FluidStack(f, amount));
	}
	
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		
		NBTTagList nbttaglist = nbttagcompound.getTagList("Tanks", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			
			String name = nbttagcompound1.getString("Name");
			FluidTank f = getTank(name);
			if (f != null)
				f.readFromNBT(nbttagcompound1);
		}
	}
	
	public void writeToNBT(NBTTagCompound nbttagcompound) {

		NBTTagList nbttaglist = new NBTTagList();
		
		Iterator it = tanks.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
			
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setString("Name", (String)pairs.getKey());
			((FluidTank)pairs.getValue()).writeToNBT(nbttagcompound1);
			nbttaglist.appendTag(nbttagcompound1);
		}
		
		nbttagcompound.setTag("Tanks", nbttaglist);
	}

}
