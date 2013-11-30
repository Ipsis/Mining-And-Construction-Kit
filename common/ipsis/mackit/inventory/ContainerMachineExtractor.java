package ipsis.mackit.inventory;

import ipsis.mackit.tileentity.TileMachineExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerMachineExtractor extends Container {
	
	final static int HOTBAR_X = 6;
	final static int HOTBAR_Y = 153;
	
	final static int USER_X = 6;
	final static int USER_Y = 95;
	
	private TileMachineExtractor machine;
	
	public ContainerMachineExtractor(InventoryPlayer invPlayer, TileMachineExtractor machine) {
		
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
		addSlotToContainer(new Slot(machine, 0, 76, 35));
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return machine.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		return null;
	}


	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < crafters.size(); i++) {
			machine.sendGUINetworkData(this, (ICrafting) crafters.get(i));
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {		
		machine.getGUINetworkData(id, data);
	}
	
}
