package ipsis.mackit.tileentity;

import ipsis.mackit.core.util.LogHelper;
import ipsis.mackit.fluid.ModFluids;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileMachineStamper extends TileMachine implements IInventory, IFluidHandler {

	private FluidTank tank;
	private ItemStack[] items;
	
	final static int OUTPUT_SLOT = 0;
	
	public TileMachineStamper() {
		super();
		
		tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 10);		
		items = new ItemStack[1];
	}
	
	public FluidTank getTank() {
		return this.tank;
	}
	
	/* IInventory */
	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i < items.length)
			return items[i];
		else
			return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int count) {
		ItemStack itemstack = getStackInSlot(i);
		
		if (itemstack != null) {
			if (itemstack.stackSize <= count) {
				setInventorySlotContents(i, null);
			} else {
				itemstack = itemstack.splitStack(count);
				onInventoryChanged();
			}
		}
		
		return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack itemstack = getStackInSlot(i);
		setInventorySlotContents(i, null);
		return itemstack;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if (i < items.length) {
			items[i] = itemstack;
			onInventoryChanged();
		}
	}

	@Override
	public String getInvName() {
		return "machineStamper";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5)  < 64;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	
	/* TileEntityMachine */
	@Override
	public boolean isMachineReady() {
		if (tank.getFluid() != null && tank.getFluid().fluidID == ModFluids.fluidWhiteDye.getID() && tank.getFluidAmount() >= FluidContainerRegistry.BUCKET_VOLUME)
			return true;
		
		return false;
	}

	@Override
	public int getRecipeEnergy() {
		return 200;
	}

	@Override
	public void clearSavedRecipeSource() {
		LogHelper.severe("Clear Recipe");
	}

	@Override
	public void createOutput() {
		if (items[OUTPUT_SLOT] == null) {
			items[OUTPUT_SLOT] = new ItemStack(Item.bone, 1);
		} else {
			items[OUTPUT_SLOT].stackSize++;
		}
	}

	@Override
	public void setRecipeSource() {
		LogHelper.severe("Calculate Recipe");
	}

	@Override
	public boolean hasSourceChanged() {
		return false;
	}

	/**
	 *  IFluidHandler
	 */
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { tank.getInfo() };
	}
	
}
