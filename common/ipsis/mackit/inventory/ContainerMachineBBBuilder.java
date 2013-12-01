package ipsis.mackit.inventory;

import ipsis.mackit.tileentity.TileMachineBBBuilder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cofh.gui.slot.SlotOutput;

public class ContainerMachineBBBuilder extends Container {

	final static int HOTBAR_X = 6;
	final static int HOTBAR_Y = 153;
	
	final static int USER_X = 6;
	final static int USER_Y = 95;
	
	private TileMachineBBBuilder machine;
	
	public ContainerMachineBBBuilder(InventoryPlayer invPlayer, TileMachineBBBuilder machine) {
		
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
		
		/* Custom slots */
		for (int x = 0; x < 3; x++)
			addSlotToContainer(new Slot(machine, x, 31 + 18 * x, 17));
		
		for (int x = 0; x < 3; x++)
			addSlotToContainer(new Slot(machine, 3 + x, 31 + 18 * x, 35));
		
		addSlotToContainer(new Slot(machine, 6, 49, 56));
		addSlotToContainer(new SlotOutput(machine, 7, 114, 35));
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
