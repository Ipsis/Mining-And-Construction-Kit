package com.ipsis.mackit.item;

import com.ipsis.mackit.block.MKBlocks;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.manager.MKManagers;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemBeaverBlock extends ItemBlock {
	
	public ItemBeaverBlock(Block id){
		super(id);
		setHasSubtypes(false);
	}
	
	@Override
	public int getMetadata(int dmg) {
		return dmg;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {

		/* TODO this is a slightly modified copy of the vanilla LilyPad onItemRightClick code - not sure about using it! */
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);
		
		if (movingobjectposition == null)
			return itemStack;
		
		if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;
            
            if (!world.canMineBlock(player, i, j, k))
            {
                return itemStack;
            }

            if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, itemStack))
            {
                return itemStack;
            }

            Block b = world.getBlock(i, j, k);
            if (MKManagers.beaverBlockMgr.isValid(b) && world.isAirBlock(i, j + 1, k))
            {
            	world.setBlock(i, j + 1, k, MKBlocks.beaverBlock);

                if (!player.capabilities.isCreativeMode)
                {
                    --itemStack.stackSize;
                }
            }
        }

        return itemStack;
	}
	
}
