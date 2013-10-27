package ipsis.mackit.item;

import ipsis.mackit.lib.Strings;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockFixedEarth extends ItemBlock {
	
	public ItemBlockFixedEarth(int id) {
		super(id);
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {		
		return Strings.BLOCK_FIXED_NAMES[itemStack.getItemDamage()];
	}
	
	@Override
	public int getMetadata(int dmg) {
		return dmg;
	}
	

}
