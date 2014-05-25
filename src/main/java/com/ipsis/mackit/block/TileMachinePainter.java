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
import com.ipsis.mackit.manager.TankManager;
import com.ipsis.mackit.util.network.packet.IPacketGuiHandler;

public class TileMachinePainter extends TileMachine implements IFacing, IFluidHandler, ISidedInventory {

	private FactorySM sm;
	private ForgeDirection facing;
	private int consumedEnergy;
	public TankManager tankMgr;
	
	private static final int TANK_SIZE = 5000;
	private static final int ENERGY_STORAGE_SIZE = 32000;
		
	public static final int INPUT_SLOT = 0;
	public static final int OUTPUT_SLOT = 1;
	
	public static final String PURE_TANK = "pure";
	
	public TileMachinePainter() {
		
		super(ENERGY_STORAGE_SIZE);
		//sm = new FactorySM(this);
		inventory = new ItemStack[2];
		facing = ForgeDirection.EAST;
		consumedEnergy = 0;
		
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
	
	/*****
	 * NBT
	 *****/
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {

		super.writeToNBT(nbttagcompound);
		nbttagcompound.setByte("Facing", (byte)facing.ordinal());
		nbttagcompound.setInteger("ConsumedEnergy", consumedEnergy);
		tankMgr.writeToNBT(nbttagcompound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		facing = ForgeDirection.getOrientation((int)nbttagcompound.getByte("Facing"));
		consumedEnergy = nbttagcompound.getInteger("ConsumedEnergy");
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
		
		return true;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {

		if (slot != OUTPUT_SLOT)
			return false;
		
		return true;
	}	
}
