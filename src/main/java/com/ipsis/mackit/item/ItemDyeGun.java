package com.ipsis.mackit.item;

import com.ipsis.mackit.helper.LogHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDyeGun extends ItemMK {

	private static final int TANK_CAPACITY = 1000; // mB
	
	private static final int MASK_FLUID = 0x0000FFFF; /* max 65535 */
	private static final int MASK_COLOR = 0xFFFF0000;
	private static final int SHIFT_FLUID = 0;
	private static final int SHIFT_COLOR = 16;
	
	/* TODO
	 * Needs to store the tank capacity AND the current color being used
	 */
	
	public ItemDyeGun() {
		
		super("Replace blocks with a colored version");
		this.setMaxStackSize(1);
		this.setMaxDamage(TANK_CAPACITY);
	}
	
	private void nextColor(ItemStack itemStack) {
		
		int dmg = itemStack.getItemDamage();
		int color = (dmg & MASK_COLOR) >> SHIFT_COLOR;		
		color = (color + 1) % 16;
		dmg &= ~MASK_COLOR;
		dmg |= (color << SHIFT_COLOR); 

		itemStack.setItemDamage(dmg);
	}
	
	/* TODO need to ensure that display damage hides the color field */
	
	@Override
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer entityPlayer,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {

		if (world.isRemote)
			return false;
		
		int capacity = (itemStack.getItemDamage() & MASK_FLUID) >> SHIFT_FLUID;
		int color = (itemStack.getItemDamage() & MASK_COLOR) >> SHIFT_COLOR;
		
		if (entityPlayer.isSneaking()) {
			
			/* next color */
			nextColor(itemStack);
		} else {
			
			/* change block */
		}
		
		return true;
	}
}
