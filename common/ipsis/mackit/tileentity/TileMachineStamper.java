package ipsis.mackit.tileentity;

import ipsis.mackit.core.util.LogHelper;
import ipsis.mackit.fluid.ModFluids;
import ipsis.mackit.item.crafting.StamperManager;
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
	private final static int OUTPUT_SLOT = 0;
	
	static final int RECIPE_PURE_DYE_STACKSIZE = FluidContainerRegistry.BUCKET_VOLUME;
	static final int RECIPE_ENERGY = 100;
	static final int RECIPE_OUTPUT_STACKSIZE = 1;
	
	private final static FluidStack PURE_DYE_STACK = new FluidStack(ModFluids.fluidPureDye, 1);
	
	private StamperManager.EnumStamperColours selectedColour;
	
	public TileMachineStamper() {
		super();
		
		tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 10);		
		items = new ItemStack[1];
		selectedColour = StamperManager.EnumStamperColours.BLACK;
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
	
	private boolean isInputReady() {
		if (!PURE_DYE_STACK.isFluidEqual(tank.getFluid()))
			return false;
		
		FluidStack t = tank.drain(RECIPE_PURE_DYE_STACKSIZE, false);
		if (t == null)
			return false;
		
		if (t.amount != RECIPE_PURE_DYE_STACKSIZE)
			return false;
		
		return true;
	}

	
	/* TileEntityMachine */
	@Override
	public boolean canMachineStart() {
		
		if (!isInputReady())
			return false;
		
		if (items[OUTPUT_SLOT] == null)
			return true;
		
		if (!items[OUTPUT_SLOT].isItemEqual(StamperManager.getInstance().getOutput(selectedColour)))
			return false;
		
		if (items[OUTPUT_SLOT].stackSize + RECIPE_OUTPUT_STACKSIZE > items[OUTPUT_SLOT].getMaxStackSize())
			return false;
		
		return true;
	}

	@Override
	public void clearSavedRecipe() {
		LogHelper.severe("Clear Recipe");
	}

	@Override
	public void createOutput() {
		if (items[OUTPUT_SLOT] == null) {
			items[OUTPUT_SLOT] = StamperManager.getInstance().getOutput(selectedColour).copy();			
			items[OUTPUT_SLOT].stackSize = RECIPE_OUTPUT_STACKSIZE;
		} else if (items[OUTPUT_SLOT].isItemEqual(StamperManager.getInstance().getOutput(selectedColour))) {
			if (items[OUTPUT_SLOT].stackSize + RECIPE_OUTPUT_STACKSIZE <= items[OUTPUT_SLOT].getMaxStackSize())
				items[OUTPUT_SLOT].stackSize += RECIPE_OUTPUT_STACKSIZE;
		}
		
		tank.drain(RECIPE_PURE_DYE_STACKSIZE, true);
	}

	@Override
	public void setRecipe() {	}
	
	@Override
	public int getRecipeEnergy() {
		return RECIPE_ENERGY;
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
