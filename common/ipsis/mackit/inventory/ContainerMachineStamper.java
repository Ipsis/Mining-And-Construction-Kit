package ipsis.mackit.inventory;

import ipsis.mackit.tileentity.TileMachineStamper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import cofh.gui.slot.SlotOutput;

public class ContainerMachineStamper extends Container {

	final static int HOTBAR_X = 6;
	final static int HOTBAR_Y = 153;
	
	final static int USER_X = 6;
	final static int USER_Y = 95;
	
	private TileMachineStamper machine;
	
	public ContainerMachineStamper(InventoryPlayer invPlayer, TileMachineStamper machine) {
		
		this.machine = machine;
		
		/* hotbar */
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(invPlayer, x, HOTBAR_X + 18 * x, HOTBAR_Y));
		}
		
		/* User Inventory */
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, USER_X + 18 * x, USER_Y + 18 * y));
			}
		}
		
		/* Custom Slots slots */
		addSlotToContainer(new SlotOutput(machine, 0, 76, 35));
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return machine.isUseableByPlayer(entityplayer);
	}
}
