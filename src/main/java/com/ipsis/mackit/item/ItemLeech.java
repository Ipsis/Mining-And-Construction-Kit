package com.ipsis.mackit.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ipsis.mackit.helper.DyedOriginHelper;
import com.ipsis.mackit.helper.LogHelper;

/**
 * Reverts blocks to their un-dyed state. eg. dyed wool to plain wool
 * @author Chris
 *
 */
public class ItemLeech extends ItemMK {

	public ItemLeech() {
		
		super();
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack itemStack,
			EntityPlayer entityPlayer, World world, int x, int y,
			int z, int hitSide, float hitX, float hitY, float hitZ) {
		
			if (world.isRemote)
				return false;
			
			Block b = world.getBlock(x, y, z);
			if (b == null || b instanceof BlockContainer)
				return false;
			
			int metadata = world.getBlockMetadata(x, y, z);
			
			ItemStack stack = new ItemStack(b, 1, metadata);
			if (DyedOriginHelper.hasOrigin(stack)) {
							
				ItemStack replace = DyedOriginHelper.getOrigin(new ItemStack(b));
				if (replace != null) {

					/**
					 * TODO Not too sure about this
					 */
					
					Block nb = Block.getBlockFromItem(replace.getItem());
					int nmeta = replace.getItemDamage();
					
					/* Remove the old block */
					world.setBlockToAir(x, y, z);
					
					/**
					 * This tries to replicate the ItemBlock placeBlockAt code
					 */
					
					if (!world.setBlock(x, y, z, nb, nmeta, 3))
					{
						return false;
					}

					if (world.getBlock(x, y, z) == nb)
					{
						nb.onBlockPlacedBy(world, x, y, z, entityPlayer, stack);
						nb.onPostBlockPlaced(world, x, y, z, metadata);
					}
				}				
			}
			
			return false;
	}

}
