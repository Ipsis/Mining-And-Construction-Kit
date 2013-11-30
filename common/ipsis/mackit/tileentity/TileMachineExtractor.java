package ipsis.mackit.tileentity;

import ipsis.mackit.item.crafting.ExtractorManager;
import ipsis.mackit.item.crafting.ExtractorRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;

public class TileMachineExtractor extends TileEntity implements IPowerReceptor, IFluidHandler, IInventory {
	
	private static final int DYE_ITEM_SLOT = 0;
	private static final int CREATE_ENERGY = 50;
	private static final int MJ_PER_TICK = 1;
	private static final int MJ_STORED_MAX = 500;
	
	private PowerHandler powerHandler;
	private FluidTank tank;
	private ItemStack[] items;
	
	private int consumedEnergy;
	private int recipeEnergy;
	private boolean isRunning;
	
	ExtractorRecipe currentRecipe;
	FluidStack outputDye;
	
	
	public TileMachineExtractor() {
		powerHandler = new PowerHandler(this, Type.MACHINE);
		initPowerProvider();
		
		tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 10);		
		items = new ItemStack[1];
		
		consumedEnergy = 0;
		recipeEnergy = 0;
		isRunning = false;
	}
	
	public FluidTank getTank() {
		return this.tank;
	}
	
	/* update from the received info */
	public void getGUINetworkData(int id, int data) {

		switch (id) {
		case 0:
			consumedEnergy = data;
			break;
		case 1:
			recipeEnergy = data;
			break;
		case 2:
			if (tank.getFluid() == null || tank.getFluid().fluidID != data) {
				tank.setFluid(new FluidStack(data, 0));
			}
			break;
		case 3:
			if (tank.getFluid() != null)
				tank.setFluid(new FluidStack(tank.getFluid().fluidID, data));
			break;
		case 4:
			int iEnergy = Math.round(powerHandler.getEnergyStored() * 10);
			iEnergy = (iEnergy & 0xffff0000) | (data & 0xffff);
			powerHandler.setEnergy(iEnergy / 10);
			break;
		case 5:
			iEnergy = Math.round(powerHandler.getEnergyStored() * 10);
			iEnergy = (iEnergy & 0xffff) | ((data & 0xffff) << 16);
			powerHandler.setEnergy(iEnergy / 10);
			break;
		}

	}
	
	public void sendGUINetworkData(Container container, ICrafting iCrafting) {
	
		iCrafting.sendProgressBarUpdate(container, 0, consumedEnergy);
		iCrafting.sendProgressBarUpdate(container, 1, currentRecipe != null ? currentRecipe.getRecipePower() : 0);
		iCrafting.sendProgressBarUpdate(container, 2, tank.getFluid() != null ? tank.getFluid().fluidID : FluidRegistry.WATER.getID());
		iCrafting.sendProgressBarUpdate(container, 3, tank.getFluid() != null ? tank.getFluid().amount : 0);
		
		/* energy has to be split into 2 * 16bit values - like Buildcraft does */
		float currEnergy = powerHandler.getEnergyStored();
		iCrafting.sendProgressBarUpdate(container, 4, Math.round(currEnergy * 10) & 0xffff);
		iCrafting.sendProgressBarUpdate(container, 5, (Math.round(currEnergy * 10) & 0xffff0000) >> 16);
		
	}
	

	public int getScaledProgress(int scale) {
		if (recipeEnergy == 0)
			return 0;
		
		return (Math.round(((float)consumedEnergy / recipeEnergy) * scale));
	}
	
	public int getScaledEnergy(int scale) {
		return (Math.round((powerHandler.getEnergyStored() / powerHandler.getMaxEnergyStored()) * scale));			
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		powerHandler.writeToNBT(compound);
		tank.writeToNBT(compound);
		
		NBTTagList items = new NBTTagList();		
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack stack = getStackInSlot(i);
			
			if (stack != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte)i);
				stack.writeToNBT(item);
				items.appendTag(item);
				
			}
		}		
		

		compound.setTag("Items", items);		
		compound.setInteger("consumedEnergy", consumedEnergy);
		
		if (currentRecipe != null)
			compound.setInteger("source", currentRecipe.hashCode());
		if (outputDye != null) {
			outputDye.writeToNBT(compound);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		powerHandler.readFromNBT(compound);
		tank.readFromNBT(compound);
		
		NBTTagList items = compound.getTagList("Items");
		for (int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound)items.tagAt(i);
			int slot = item.getByte("Slot");
			
			
			if (slot >= 0 && slot < getSizeInventory()) {
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
		
		consumedEnergy = compound.getInteger("consumedEnergy");
		
		
		outputDye = FluidStack.loadFluidStackFromNBT(compound);
		
		
	}
	
	
	private boolean canStart() {
		
		if (isRunning)
			return false;
		
		if (items[DYE_ITEM_SLOT] == null || items[DYE_ITEM_SLOT].stackSize <= 0)
			return false;
		
		if (ExtractorManager.getInstance().isSource(items[DYE_ITEM_SLOT]) == false)
			return false;
		
		ExtractorRecipe r = ExtractorManager.getInstance().getRecipe(items[DYE_ITEM_SLOT]);
		if (r == null) {
			return false;
		}
				
		if (powerHandler.useEnergy(r.getRecipePower(),  r.getRecipePower(), false) != r.getRecipePower())
			return false;
				
		
		if (tank.getFluidAmount() != 0) {
			if (r.isOutput(tank.getFluid()) == false)
				return false;
			
			int amount = r.getOutput(tank.getFluid()).amount;
			if (amount == 0)
				return false;
			
			if (tank.fill(r.getOutput(tank.getFluid()), false) != amount)
				return false;		
		}
		
		return true;
	}
	
	private boolean startRunning() {
		currentRecipe = ExtractorManager.getInstance().getRecipe(items[DYE_ITEM_SLOT]);
		outputDye = currentRecipe.getRandomOutput();
		
		if (currentRecipe == null || outputDye == null) {
			currentRecipe = null;
			outputDye = null;
			return false;
		}
		
		recipeEnergy = currentRecipe.getRecipePower();
		decrStackSize(DYE_ITEM_SLOT, 1);
		
		return true;
	}
	
	private void createOutput() {	
		if (outputDye != null)
			tank.fill(outputDye, true);		
	}
	
	@Override
	public void updateEntity() {
		
		if (worldObj.isRemote)
			return;
		
		boolean oldRunning = isRunning;
		
		if (isRunning) {
			if (consumedEnergy < currentRecipe.getRecipePower()) {
				if (powerHandler.useEnergy(MJ_PER_TICK,  MJ_PER_TICK, false) == MJ_PER_TICK) {
					powerHandler.useEnergy(MJ_PER_TICK, MJ_PER_TICK, true);
					consumedEnergy += MJ_PER_TICK;
				}
			}
			
			if (consumedEnergy >= currentRecipe.getRecipePower()) {
				/* finished processing */
				createOutput();
				isRunning = false;
				consumedEnergy = 0;
				recipeEnergy = 0;
				currentRecipe = null;
				outputDye = null;
			}
		}
		
		if (canStart()) {
			if (startRunning()) {
				isRunning = true;
			}
		}
		
		if (oldRunning != isRunning) {
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if (isRunning)
				meta |= 0x8;
			else
				meta &= ~0x8;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 3);
		}
	}
	
	/**
	 *  IInventory
	 *  */
	@Override
	public int getSizeInventory() {
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i < items.length)
			return items[i];
		
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
		return "machineExtractor";
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
	public void openChest() { }

	@Override
	public void closeChest() { }

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
		//return (i == DYE_ITEM_SLOT && DyeCraftingManager.getInstance().isDyeSource(itemstack));
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
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { tank.getInfo() };
	}

	/**
	 * IPowerReceptor
	 */
	private void initPowerProvider() {
		powerHandler.configure(1, 15, 100, MJ_STORED_MAX);
		powerHandler.configurePowerPerdition(1, 200);
	}
	
	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return powerHandler.getPowerReceiver();
	}

	@Override
	public void doWork(PowerHandler workProvider) {
		return; /* use updateEntity */
		
	}

	@Override
	public World getWorld() {
		return worldObj;
	}



}
