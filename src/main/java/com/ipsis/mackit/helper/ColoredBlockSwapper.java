package com.ipsis.mackit.helper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ipsis.mackit.manager.PainterManager;
import com.ipsis.mackit.manager.PainterManager.PainterRecipe;

/**
 * Swap a block for the same block of a different color
 *
 */
public class ColoredBlockSwapper {
	
	private static void swapBlock(EntityPlayer entityPlayer, World world, int x, int y, int z, Block block, int blockMetadata, ItemStack stack) { 
		
		/* Remove the old block */
		world.setBlockToAir(x, y, z);
		
		/**
		 * This tries to replicate the ItemBlock placeBlockAt code
		 */		
		if (world.setBlock(x, y, z, block, blockMetadata, 3))
		{
			if (world.getBlock(x, y, z) == block)
			{
				block.onBlockPlacedBy(world, x, y, z, entityPlayer, stack);
				block.onPostBlockPlaced(world, x, y, z, blockMetadata);
			}
		}
	}

	public static boolean swap(EntityPlayer entityPlayer, World world, int x, int y, int z, DyeHelper.DyeColor color, boolean wipeClean) {

		Block b = world.getBlock(x, y, z);
		if (b == null || b instanceof BlockContainer)
			return false;
		
		/* stack is an ItemStack of the current block */
		int metadata = world.getBlockMetadata(x, y, z);
		ItemStack stack = new ItemStack(b, 1, metadata);
		
		/* Fail if there is no original uncolored block */
		if (DyedOriginHelper.hasOrigin(stack) == false)
			return false;
		
		/* origin is an ItemStack of the origin block */
		ItemStack origin = DyedOriginHelper.getOrigin(stack);
		
		if (wipeClean) {
			
			Block originBlock = Block.getBlockFromItem(origin.getItem());
			int nmeta = origin.getItemDamage();
			swapBlock(entityPlayer, world, x, y, z, originBlock, nmeta, origin);
			return true;
		} else {
			
			PainterRecipe r = PainterManager.getRecipe(origin, color.getItemStack());
			if (r != null) {
				
				Block replaceBlock = Block.getBlockFromItem(r.getOutput().getItem());
				ItemStack replaceStack = r.getOutput();
				int nmeta = replaceStack.getItemDamage();
				swapBlock(entityPlayer, world, x, y, z, replaceBlock, nmeta, replaceStack);
				return true;
			}
			
		}
		
		return false;
	}
}
