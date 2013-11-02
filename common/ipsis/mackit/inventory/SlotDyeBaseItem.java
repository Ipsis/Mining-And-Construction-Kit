package ipsis.mackit.inventory;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotDyeBaseItem extends Slot {

	private ItemStack[] validDyeItems = {
			new ItemStack(Block.plantRed, 1),
			new ItemStack(Block.plantYellow, 1),
			new ItemStack(Block.cactus, 1),
			new ItemStack(Item.dyePowder, 1, 4),	/* lapiz */
			new ItemStack(Item.bone, 1),
			new ItemStack(Item.dyePowder, 1), 		/* squid ink  */
			new ItemStack(Block.cocoaPlant, 1)			
	};
	
	public SlotDyeBaseItem(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}
	
	private boolean isValidDyeItem(ItemStack item) {
		
		for (int i = 0; i < validDyeItems.length; i++) {
			if (validDyeItems[i].itemID == item.itemID)
				return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return isValidDyeItem(stack);
	}
	
}
