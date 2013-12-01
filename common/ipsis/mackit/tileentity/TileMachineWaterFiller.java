package ipsis.mackit.tileentity;

import ipsis.mackit.block.ModBlocks;
import ipsis.mackit.lib.BlockIds;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;

public class TileMachineWaterFiller extends TileEntity implements IInventory, ISidedInventory, IPowerReceptor {

	private static final int[] accessSlots = new int[] {0, 1, 2, 3, 4, 5};
	
	private static final int REDSTONE_SLOT = 4;
	private static final int OUTPUT_SLOT = 5;
	private static final int CREATE_TICKS = 100;
	private static final int MJ_PER_TICK = 1;
	private static final int MJ_STORED_MAX = 500;
	
	private int createTickCount;
	
	private PowerHandler powerHandler;
	
	private ItemStack[] items;
	
	public TileMachineWaterFiller() {
		items = new ItemStack[6];
		createTickCount = -1;
		powerHandler = new PowerHandler(this, Type.MACHINE);
		initPowerProvider();
	}
	
	private void initPowerProvider() {
		powerHandler.configure(1, 15, MJ_PER_TICK, MJ_STORED_MAX);
		powerHandler.configurePowerPerdition(1, 100);
	}
	
	public float getEnergyStored() {
		return powerHandler.getEnergyStored();
	}
	
	public void setEnergyStored(int v) {
		powerHandler.setEnergy(v);
	}
	
	public float getMaxEnergyStored() {
		return powerHandler.getMaxEnergyStored();
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
	
	/* IInventory */
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
		ItemStack item = getStackInSlot(i);
		setInventorySlotContents(i, null);
		return item;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {		
		items[i] = itemstack;		
		
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "waterFillerMachine";
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
		switch (i) {
		case 0:
		case 1:
		case 2:
		case 3:
			if (itemstack.itemID == Block.dirt.blockID)
				return true;
			break;
		case 4:
			if (itemstack.itemID == Item.redstone.itemID)
				return true;
		default:
			break;			
		}
		
		return false;
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
	}
	
	private boolean isCrafting() {
		return createTickCount >= 0;
	}
	
	/* need 128 dirt and 1 redstone, output slot must not be full */
	private boolean canCraft() {
		
		if (items[REDSTONE_SLOT] == null || items[REDSTONE_SLOT].stackSize < 1)
			return false;
				
		if (items[OUTPUT_SLOT] != null && items[OUTPUT_SLOT].stackSize >= getInventoryStackLimit())
			return false;
		
		int dirtCount = 0;
		for (int i = 0; i < 4; i++) {
			if (items[i] != null) {
				dirtCount += items[i].stackSize;
			}
		}
		
		if (dirtCount < 128)
			return false;
		
		return true;
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
	

	private void createOutput() {
		
		if (canCraft()) {
			ItemStack outputItem = new ItemStack(ModBlocks.waterFiller);
			
			if (items[OUTPUT_SLOT] == null) {
				items[OUTPUT_SLOT] = outputItem.copy();
			} else if (items[OUTPUT_SLOT].isItemEqual(outputItem)) {
				items[OUTPUT_SLOT].stackSize++;
			}
			
			int remove = 128;
			for (int i = 0; i < 4; i++) {
				if (remove <= 0)
					break;
				
				ItemStack items = decrStackSize(i, remove);
				if (items != null) {
					remove -= items.stackSize;
				}
			}
			
			decrStackSize(REDSTONE_SLOT, 1);
			
			if (items[OUTPUT_SLOT].stackSize <= 0)
				items[OUTPUT_SLOT] = null;
		}
	}
	


	/* ISidedInventory */
	
	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return accessSlots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		return isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {

		if (itemstack.itemID != BlockIds.WATER_FILLER)
			return false;
		
		if (i != OUTPUT_SLOT)
			return false;
		
		return true;
	}

	
	/* IPowerReceptor */	
	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return powerHandler.getPowerReceiver();
	}

	@Override
	public void doWork(PowerHandler workProvider) {	}

	@Override
	public World getWorld() {
		return this.worldObj;
	}

}
