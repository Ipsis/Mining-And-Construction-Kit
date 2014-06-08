package com.ipsis.mackit.item;

import java.util.List;

import com.ipsis.mackit.helper.ColoredBlockSwapper;
import com.ipsis.mackit.helper.DyeHelper;
import com.ipsis.mackit.helper.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDyeGun extends ItemMK {

	private static final int TANK_CAPACITY = DyeHelper.DYE_BASE_AMOUNT * 20;
	
	private static final int MASK_FLUID = 0x0000FFFF; /* max 65535 */
	private static final int MASK_COLOR = 0xFFFF0000;
	private static final int SHIFT_FLUID = 0;
	private static final int SHIFT_COLOR = 16;
	
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
	
	private void useDye(ItemStack itemStack) {
		
		int dmg = itemStack.getItemDamage();
		int fluid = (itemStack.getItemDamage() & MASK_FLUID) >> SHIFT_FLUID;
		fluid -= DyeHelper.DYE_BASE_AMOUNT;
		if (fluid < 0)
			fluid = 0;
		
		dmg &= ~MASK_FLUID;
		dmg |= (fluid  << SHIFT_FLUID);
		
		itemStack.setItemDamage(dmg);
	}
	
	/* TODO need to ensure that display damage hides the color field */
	
	@Override
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer entityPlayer,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {

		if (world.isRemote)
			return false;
		
		int fluid = (itemStack.getItemDamage() & MASK_FLUID) >> SHIFT_FLUID;
		int color = (itemStack.getItemDamage() & MASK_COLOR) >> SHIFT_COLOR;
		
		if (entityPlayer.isSneaking()) {
			
			/* next color */
			nextColor(itemStack);
		} else {
			
			/* check for creative mode and enough dye available */
			
			/* change block */
			if (ColoredBlockSwapper.swap(entityPlayer, world, x, y, z, DyeHelper.DyeColor.getFromDmg(color), false) == true) {
				
				if (!entityPlayer.capabilities.isCreativeMode)
					useDye(itemStack);
			}
		}
		
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack,	EntityPlayer entityPlayer, List info, boolean useExtraInformation) {
		
		int fluid = (itemStack.getItemDamage() & MASK_FLUID) >> SHIFT_FLUID;
		int color = (itemStack.getItemDamage() & MASK_COLOR) >> SHIFT_COLOR;
		
		info.add(DyeHelper.DyeColor.getFromDmg(color).getName());
		info.add(fluid + "/" + TANK_CAPACITY);
	}
}
