package com.ipsis.mackit.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import com.ipsis.mackit.lib.BlockIds;
import com.ipsis.mackit.lib.ItemIds;
import com.ipsis.mackit.lib.Strings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/*
 * Two modes of operation - horizontal and vertical
 * Uses "ammo" which is Fixer Foam Pellets
 */
public class ItemFixerFoamGun extends ItemMK {
	
	public ItemFixerFoamGun(int id) {
		
		super(id);
		this.setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.FIXER_FOAM_GUN_NAME);
	}

	/* Swap a sand or gravel block for the fixed version */
    private void changeBlock(World world, int x, int y, int z) {
            int id = world.getBlockId(x, y, z);
            
            if (id == Block.sand.blockID || id == Block.gravel.blockID) {
                    world.setBlock(x, y, z, BlockIds.FIXED_EARTH, (id == Block.sand.blockID ? 0 : 1), 3);
            }
    }
    
    private ForgeDirection getFacing(EntityPlayer player) {
    	
    	/* Not sure about 0,2,3,X */
    	int facing = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
    	if (facing == 0)
    		return ForgeDirection.SOUTH;
    	else if (facing == 2)
    		return ForgeDirection.NORTH;
    	else if (facing == 3)
    		return ForgeDirection.EAST;
    	else
    		return ForgeDirection.WEST;
    }
    
    private void applyFoamVertical(EntityPlayer player, World world, int x, int y, int z) {
    	    	
        int xDiff = 1;
        int zDiff = 1;
        ForgeDirection facing = getFacing(player);
        
        if (facing == ForgeDirection.SOUTH || facing == ForgeDirection.NORTH)
                zDiff = 0;
        else
                xDiff = 0;
        
        int xPos, yPos, zPos;
              
        for (xPos = x - xDiff; xPos <= x + xDiff; xPos++) {
                for (yPos = y; yPos <= y + 2; yPos++) {
                        for (zPos = z - zDiff; zPos <= z + zDiff; zPos++) {
                                changeBlock(world, xPos, yPos, zPos);
                        }
                }
        }    	
    }
    
    private void applyFoamHorizontal(EntityPlayer player, World world, int x, int y, int z) {
    	
    	ForgeDirection facing = getFacing(player);
    	
    	int xPos, zPos;
    	if (facing == ForgeDirection.NORTH) {
    		for (zPos = z; zPos >= z - 2; zPos--) {
    			for (xPos = x - 1; xPos <= x + 1; xPos++) {
    				changeBlock(world, xPos, y, zPos);
    			}
    		}
    	} else if (facing == ForgeDirection.SOUTH) {
    		for (zPos = z; zPos <= z + 2; zPos++) {
    			for (xPos = x - 1; xPos <= x + 1; xPos++) {
    				changeBlock(world, xPos, y, zPos);
    			}
    		}
    	} else if (facing == ForgeDirection.WEST) {
    		for (xPos = x; xPos >= x - 2; xPos--) {
    			for (zPos = z - 1; zPos <= z + 1; zPos++) {
    				changeBlock(world, xPos, y, zPos);
    			}
    		}
    	} else if (facing == ForgeDirection.EAST) {
    		for (xPos = x; xPos <= x + 2; xPos++) {
    			for (zPos = z - 1; zPos <= z + 1; zPos++) {
    				changeBlock(world, xPos, y, zPos);
    			}
    		}
    	}
    }
	
	@Override
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		
		if (!world.isRemote) {
			int id = world.getBlockId(x, y, z);
			if (id == Block.sand.blockID || id == Block.gravel.blockID) {
			
				if (player.inventory.hasItem(ItemIds.FIXER_FOAM_PELLET_DEFAULT) || player.capabilities.isCreativeMode) {
					player.inventory.consumeInventoryItem(ItemIds.FIXER_FOAM_PELLET_DEFAULT);
					
					if (isHorizontalMode(itemStack))
						applyFoamHorizontal(player, world, x, y, z);
					else
						applyFoamVertical(player, world, x, y, z);
				}
			}
			
			return true;
		} else {
			return false;
		}
	}	
	
	private static final int HORIZONTAL_MODE = 0;
	private static final int VERTICAL_MODE = 1;
	
	private boolean isHorizontalMode(ItemStack itemStack) {

		return itemStack.getItemDamage() == HORIZONTAL_MODE ? true : false;
	}
	
	private boolean isVerticalMode(ItemStack itemStack) {

		return itemStack.getItemDamage() == VERTICAL_MODE ? true : false;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		
		if (player.isSneaking()) {
		
			/* flip between horizontal and vertical modes */
			itemStack.setItemDamage(isHorizontalMode(itemStack) ? VERTICAL_MODE : HORIZONTAL_MODE);
			
			if (!world.isRemote) {
				player.addChatMessage(isHorizontalMode(itemStack) ? "Horizontal Mode" : "Vertical Mode");
			}
		}
		
		return itemStack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean useExtraInformation) {

		list.add(isHorizontalMode(itemStack) ? "Horizontal Mode" : "Vertical Mode");
	}

}
