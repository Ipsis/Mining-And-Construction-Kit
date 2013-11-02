package ipsis.mackit.inventory;

import ipsis.mackit.tileentity.TileWaterFillerMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerWaterFillerMachine extends Container {

	private TileWaterFillerMachine machine;
	
	public ContainerWaterFillerMachine(InventoryPlayer invPlayer, TileWaterFillerMachine machine) {
		this.machine = machine;
		
		/* hotbar */
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, 6 + 18 * x, 153));
		}
		
		/* User Inventory */
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 6 + 18 * x, 95 + 18 * y));
			}
		}
	
		/* Our Slots */
		for (int x = 0; x < 4; x++) {
			addSlotToContainer(new SlotDirt(machine, x, 6 + 18 * x, 29));
		}
		
		addSlotToContainer(new SlotRedstone(machine, 4, 33, 58));
		addSlotToContainer(new Slot(machine, 5, 132, 45));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return machine.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
	
		Slot slot = getSlot(i);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			ItemStack result = stack.copy();
			
			/* need to write custom mergeItemStack if each slot takes something different */
			
			if (i >= 36) {
				/* machine slots -> player */
				if (!mergeItemStack(stack, 0, 36, false)) {
					return null;
				}				
			} else if (!mergeItemStack(stack, 36, 36 + machine.getSizeInventory(), false)) {
				return null;
			}
			
			if (stack.stackSize == 0) {
				slot.putStack(null);				
			} else {
				slot.onSlotChanged();
			}
			
			slot.onPickupFromSlot(player, stack);
			return result;
		}		
	
		return null;		
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting player) {
		super.addCraftingToCrafters(player);
		
		player.sendProgressBarUpdate(this,  0,  (int)machine.getEnergyStored());
		player.sendProgressBarUpdate(this,  1, machine.getCreateTickCount() );
	}
	
	private int oldStoredEnergy;
	private int oldCreateTickCount;
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (Object player : crafters) {
			if (oldStoredEnergy != (int)machine.getEnergyStored()) {
				oldStoredEnergy = (int)machine.getEnergyStored();
				((ICrafting)player).sendProgressBarUpdate(this,  0,  oldStoredEnergy);
			}
			
			if (oldCreateTickCount != machine.getCreateTickCount()) {
				oldCreateTickCount = machine.getCreateTickCount();
				((ICrafting)player).sendProgressBarUpdate(this,  1,  oldCreateTickCount);
			}
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		if (id == 0)
			machine.setEnergyStored(data);
		if (id == 1)
			machine.setCreateTickCount(data);
	}
	
}