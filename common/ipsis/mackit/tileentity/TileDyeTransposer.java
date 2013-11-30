package ipsis.mackit.tileentity;

import ipsis.mackit.fluid.ModFluids;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;

public class TileDyeTransposer extends TileEntity implements IPowerReceptor, IInventory, IFluidHandler {

	private PowerHandler powerHandler;
	private FluidTank tank;
	
	private static final int DYE_ITEM_SLOT = 0;
	private static final int CREATE_TICKS = 100;
	private static final int MJ_PER_TICK = 1;
	private static final int MJ_STORED_MAX = 500;
	
	private int createTickCount;

	private ItemStack[] validDyeItems = {
			new ItemStack(Block.plantRed, 1),
			new ItemStack(Block.plantYellow, 1),
			new ItemStack(Block.cactus, 1),
			new ItemStack(Item.dyePowder, 1, 4),	/* lapiz */
			new ItemStack(Item.bone, 1),
			new ItemStack(Item.dyePowder, 1), 		/* squid ink  */
			new ItemStack(Block.cocoaPlant, 1)			
	};
	
	private int[] outputFluidId = {
			ModFluids.fluidRedDye.getID(), ModFluids.fluidYellowDye.getID(),  ModFluids.fluidBlueDye.getID(),
	};
	
	private boolean isValidDyeItem(ItemStack item) {
		
		for (int i = 0; i < validDyeItems.length; i++) {
			if (validDyeItems[i].itemID == item.itemID)
				return true;
		}
		
		return false;
	}
	
	private int getProduces(ItemStack item) {
		for (int i = 0; i < validDyeItems.length; i++) {
			if (validDyeItems[i].itemID == item.itemID)
				return outputFluidId[i];
		}
		
		return -1;
	}
	
	private boolean canCraft() {
		
		/* No items, no crafty! */
		if (items[DYE_ITEM_SLOT] == null || items[DYE_ITEM_SLOT].stackSize <= 0)
			return false;
		
		/* No space in tank */
		if (tank.getFluidAmount() >= tank.getCapacity())
			return false;
		
		/* Tank not empty then new liquid must be the same */
		if (tank.getFluidAmount() > 0) {
			if (tank.getFluid().fluidID != getProduces(items[DYE_ITEM_SLOT]))
					return false;
		}
		
		return true;		
	}
	
	private  void createOutput() {
	
		if (canCraft()) {
			int fluidid = getProduces(items[DYE_ITEM_SLOT]);
			
			if (tank.fill(new FluidStack(fluidid, FluidContainerRegistry.BUCKET_VOLUME), true) == FluidContainerRegistry.BUCKET_VOLUME) {				
				decrStackSize(DYE_ITEM_SLOT, 1);
			}
		}
	}
	
	
	public TileDyeTransposer() {
		powerHandler = new PowerHandler(this, Type.MACHINE);
		initPowerProvider();
		
		tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 10);		
		items = new ItemStack[1];
		createTickCount = -1;
	}
	
	public int getEnergyStored() {
		return (int)powerHandler.getEnergyStored();
	}
	
	public void setEnergyStored(int v) {
		powerHandler.setEnergy(v);
	}
	
	public int getMaxEnergyStored() {
		return (int)powerHandler.getMaxEnergyStored();
	}
	
	public int getCreateTickCount() {
		return createTickCount;
	}
	
	public void setCreateTickCount(int v) {
		createTickCount = v;
	}
	
	public int getCreateTicksMax() {
		return CREATE_TICKS;
	}
	
	public int getLiquidStored() {
		return tank.getFluidAmount();
	}
	
	public int getLiquidCapacity() {
		return tank.getCapacity();
	}
	
	public void setLiquidStored(int v) {
		/* Doesn't work on save/load !!! */
		FluidStack f = tank.getInfo().fluid;
		if (f != null) {
			f.amount = v;
			tank.setFluid(f);
		}
	}
	

	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
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
		compound.setByte("CreateTick", (byte)createTickCount);
		powerHandler.writeToNBT(compound);
		
		tank.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		NBTTagList items = compound.getTagList("Items");
		for (int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound)items.tagAt(i);
			int slot = item.getByte("Slot");
			
			
			if (slot >= 0 && slot < getSizeInventory()) {
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
		
		createTickCount = compound.getByte("CreateTick");
		powerHandler.readFromNBT(compound);
		
		tank.readFromNBT(compound);
	}
	
	private boolean isCrafting() {
		return createTickCount >= 0;
	}
	
	@Override
	public void updateEntity() {
		boolean updated = false;
		
		if (!isCrafting() && canCraft()) {
			/* not doing anything so start */
			createTickCount = 0;
		}
		
		if (canCraft()) {
			if (powerHandler.useEnergy(MJ_PER_TICK, MJ_PER_TICK, true) == MJ_PER_TICK) {
				createTickCount++;
			}
			
			if (createTickCount == CREATE_TICKS) {
				createTickCount = -1;
				createOutput();
				updated = true;
			}
		} else {
			createTickCount = -1;
		}
		
		if (updated)
			this.onInventoryChanged();
	}
	
	/* IPowerReceptor */
	private void initPowerProvider() {
		powerHandler.configure(1, 15, MJ_PER_TICK, MJ_STORED_MAX);
		powerHandler.configurePowerPerdition(1, 100);
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return powerHandler.getPowerReceiver();
	}

	@Override
	public void doWork(PowerHandler workProvider) {
	}

	@Override
	public World getWorld() {
		return worldObj;
	}

	
	/* IInventory */
	private ItemStack[] items;
	
	@Override
	public int getSizeInventory() {
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return items[i];
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
		items[i] = itemstack;
		onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "dyeTransposer";
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
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		
		return (i == DYE_ITEM_SLOT ? isValidDyeItem(itemstack) : false);
	}
	
	/* IFluidHandler */

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
		return new FluidTankInfo[] {tank.getInfo()};
	}
}
