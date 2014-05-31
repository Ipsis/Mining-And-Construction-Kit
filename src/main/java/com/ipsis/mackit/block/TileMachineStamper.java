package com.ipsis.mackit.block;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.ipsis.mackit.block.machinesm.FactorySM;
import com.ipsis.mackit.block.machinesm.IFactorySM;
import com.ipsis.mackit.block.machinesm.IMachineRecipe;
import com.ipsis.mackit.block.machinesm.IRecipeManager;
import com.ipsis.mackit.item.MKItems;
import com.ipsis.mackit.manager.MKManagers;
import com.ipsis.mackit.manager.StamperManager;
import com.ipsis.mackit.manager.TankManager;
import com.ipsis.mackit.network.PacketHandler;
import com.ipsis.mackit.network.message.IMessageGuiHandler;
import com.ipsis.mackit.network.message.MessageGui;
import com.ipsis.mackit.reference.Gui;

public class TileMachineStamper extends TileMachine implements IFactorySM, IFacing, IRecipeManager, IMessageGuiHandler, IFluidHandler, ISidedInventory {

	private FactorySM sm;
	private ForgeDirection facing;
	private int consumedEnergy;
	public TankManager tankMgr;
	private int selected;
	
	private static final int TANK_SIZE = 5000;
	private static final int ENERGY_STORAGE_SIZE = 32000;
		
	public static final int INPUT_SLOT = 0;
	public static final int OUTPUT_SLOT = 1;
	
	public static final String PURE_TANK = "pure";
	
	public TileMachineStamper() {
		
		super(ENERGY_STORAGE_SIZE);
		sm = new FactorySM(this);
		inventory = new ItemStack[2];
		facing = ForgeDirection.EAST;
		consumedEnergy = 0;
		selected = 0;
		
		tankMgr = new TankManager();
		tankMgr.addTank(PURE_TANK, TANK_SIZE);
	}
	
	@Override
	public void setFacing(ForgeDirection facing) {
		
		this.facing = facing;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	@Override
	public ForgeDirection getFacing() {
		
		return facing;
	}
	
	public int getSelected() {
		
		return selected;
	}
	
	public void setSelected(int v) {
		
		selected = v;
	}
		
	public void incSelected() {

		selected = MKManagers.stamperMgr.getNextIdx(selected);
		
		if (worldObj.isRemote) {
			PacketHandler.INSTANCE.sendToServer(new MessageGui(
					xCoord, yCoord, zCoord,
					(byte)Gui.STAMPER,
					(byte)Gui.TYPE_BUTTON, 
					(byte)Gui.STAMPER_SELECTED_UP, 
					0, 0));
		}
	}

	public void decSelected() {

		selected = MKManagers.stamperMgr.getPrevIdx(selected);
		
		if (worldObj.isRemote) {
			PacketHandler.INSTANCE.sendToServer(new MessageGui(
					xCoord, yCoord, zCoord,
					(byte)Gui.STAMPER,
					(byte)Gui.TYPE_BUTTON, 
					(byte)Gui.STAMPER_SELECTED_DN, 
					0, 0));
		}
	}
	
	/*******************
	 * IPacketGuiHandler
	 *******************/
	@Override
	public void handleMessageGui(MessageGui msg) {

		if (msg.guiId != Gui.STAMPER)
			return;
		
		if (msg.ctrlType == Gui.TYPE_BUTTON) {
			if (msg.ctrlId == Gui.STAMPER_SELECTED_DN) {
				decSelected();				
			} else if (msg.ctrlId == Gui.STAMPER_SELECTED_UP) {
				incSelected();
			}
		}
	}
	
	@Override
	public void updateEntity() {

		if (worldObj.isRemote)
			return;
		
		sm.run();
	}
	
	public int getScaledProgress(int scale) {
		
		return sm.getScaledProgress(scale);
	}
	
	/************
	 * IFactorySM
	 ************/
	@Override
	public boolean isOutputValid(IMachineRecipe recipe) {

		ItemStack out = getStackInSlot(OUTPUT_SLOT);
		if (out == null)
			return true;
		
		ItemStack produces = MKManagers.stamperMgr.getOutput(selected);
		return out.isItemEqual(produces);
	}

	@Override
	public boolean isEnergyAvailable(int amount) {

		return amount == getEnergyStorage().extractEnergy(amount, true);
	}

	@Override
	public void consumeInputs(IMachineRecipe recipe) {

		decrStackSize(INPUT_SLOT, 1);
		tankMgr.getTank(PURE_TANK).drain(StamperManager.RECIPE.getPureDyeAmount(), true);
	}

	@Override
	public void createOutputs(IMachineRecipe recipe) {

		ItemStack c = getStackInSlot(OUTPUT_SLOT);
		if (c == null) {
			setInventorySlotContents(OUTPUT_SLOT, MKManagers.stamperMgr.getOutput(selected));
		} else {
			c.stackSize++;
			setInventorySlotContents(OUTPUT_SLOT, c);
		}
	}

	@Override
	public void consumeEnergy(int amount) {

		consumedEnergy += getEnergyStorage().extractEnergy(amount, false);
	}

	@Override
	public int getConsumedEnergy() {

		return consumedEnergy;
	}

	@Override
	public void resetConsumedEnergy() {

		consumedEnergy = 0;
	}
	
	@Override
	public int getEnergyTick() {

		return super.getInfoEnergyPerTick();
	}
	
	/* GUI updating only */
	public void setConsumedEnergy(int amount) {
		
		consumedEnergy = amount;
	}
	
	public void setRecipeEnergy(int amount) {
		
		sm.setRecipeEnergy(amount);
	}
	
	public int getRecipeEnergy() {
		
		return sm.getRecipeEnergy();		
	}
	
	/****************
	 * IRecipeManager
	 ****************/
	
	@Override
	public IMachineRecipe getRecipe() {

		ItemStack in = getStackInSlot(INPUT_SLOT);
		if (in == null)
			return null;
		
		if (in.getItem() != MKItems.itemDyeBlank)
			return null;
		
		FluidStack t = tankMgr.getTank(PURE_TANK).drain(StamperManager.RECIPE.getPureDyeAmount(), false);
		if (t == null || t.amount != StamperManager.RECIPE.getPureDyeAmount())
			return null;


		return StamperManager.RECIPE;
	}
	
	/*****
	 * NBT
	 *****/
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {

		super.writeToNBT(nbttagcompound);
		nbttagcompound.setByte("Facing", (byte)facing.ordinal());
		nbttagcompound.setInteger("ConsumedEnergy", consumedEnergy);
		nbttagcompound.setByte("Selected",  (byte)selected);
		tankMgr.writeToNBT(nbttagcompound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		facing = ForgeDirection.getOrientation((int)nbttagcompound.getByte("Facing"));
		consumedEnergy = nbttagcompound.getInteger("ConsumedEnergy");
		selected = nbttagcompound.getByte("Selected");
		tankMgr.readFromNBT(nbttagcompound);
	}
	
	/************
	 * Packets
	 ************/
	@Override
	public Packet getDescriptionPacket() {

		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setByte("Facing", (byte)facing.ordinal());
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
	
		facing = ForgeDirection.getOrientation((int)pkt.func_148857_g().getByte("Facing"));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	/***************
	 * IFluidHandler
	 * 
	 * Can only fill the pure tank
	 ***************/

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

		return tankMgr.getTank(PURE_TANK).fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {

		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {

		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {

		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {

		return tankMgr.getTankInfo(from);
	}
	
	/*****************
	 * ISidedInventory
	 *****************/
	
	private static final int[] accessSlots = new int[]{ INPUT_SLOT, OUTPUT_SLOT };

	@Override
	public int[] getAccessibleSlotsFromSide(int slot) {

		return accessSlots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int side) {

		if (slot != INPUT_SLOT)
			return false;
		
		return itemStack.getItem() == MKItems.itemDyeBlank;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {

		if (slot != OUTPUT_SLOT)
			return false;
		
		return true;
	}	
}
