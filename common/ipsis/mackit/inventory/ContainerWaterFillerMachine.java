package ipsis.mackit.inventory;

import ipsis.mackit.tileentity.TileWaterFillerMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

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
		return null;
	}
}
