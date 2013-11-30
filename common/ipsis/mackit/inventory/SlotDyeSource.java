package ipsis.mackit.inventory;

import ipsis.mackit.item.crafting.DyeCraftingManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDyeSource extends Slot {
	
	public SlotDyeSource(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}	
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return DyeCraftingManager.getInstance().isDyeSource(stack);
	}
	
}
