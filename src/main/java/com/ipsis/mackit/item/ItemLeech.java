package com.ipsis.mackit.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ipsis.mackit.helper.ColoredBlockSwapper;
import com.ipsis.mackit.helper.DyeHelper;
import com.ipsis.mackit.helper.DyedOriginHelper;
import com.ipsis.mackit.helper.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Reverts blocks to their un-dyed state. eg. dyed wool to plain wool
 * @author Chris
 *
 */
public class ItemLeech extends ItemMK {

	public ItemLeech() {
		
		super("Replace dyed blocks with the un-dyed version");
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack itemStack,
			EntityPlayer entityPlayer, World world, int x, int y,
			int z, int hitSide, float hitX, float hitY, float hitZ) {
		
			if (world.isRemote)
				return false;
			
			return ColoredBlockSwapper.swap(entityPlayer, world, x, y, z, DyeHelper.DyeColor.GREEN, true);
	}
}
