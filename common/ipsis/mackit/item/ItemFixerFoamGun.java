package ipsis.mackit.item;

import ipsis.mackit.core.util.LogHelper;
import ipsis.mackit.lib.BlockIds;
import ipsis.mackit.lib.ItemIds;
import ipsis.mackit.lib.Strings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFixerFoamGun extends ItemMK {
	
	public ItemFixerFoamGun(int id) {
		
		super(id);
		this.setUnlocalizedName(Strings.FIXER_FOAM_GUN_NAME);
		this.setMaxStackSize(1);
	}

	private void changeBlock(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		
		if (id == Block.sand.blockID || id == Block.gravel.blockID) {
			world.setBlock(x, y, z, BlockIds.FIXED_EARTH, (id == Block.sand.blockID ? 0 : 1), 3);
		}
	}
	
	private void verticalPlane(World world, EntityPlayer player, int x, int y, int z) {
		
		int facing = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		/* 3 by 3 */
		int xDiff = 1;
		int yDiff = 1;
		int zDiff = 1;
		
		
		if (facing == 0 || facing == 2) {
			zDiff = 0;
		} else {
			xDiff = 0;
		}
		
		int xPos, yPos, zPos;
		
		for (xPos = x - xDiff; xPos <= x + xDiff; xPos++) {
			for (yPos = y - yDiff; yPos <= y + yDiff; yPos++) {
				for (zPos = z - zDiff; zPos <= z + zDiff; zPos++) {
					changeBlock(world, xPos, yPos, zPos);
				}
			}
		}
				
	}
	
	private void horizontalPlane(World world, EntityPlayer player, int x, int y, int z) {
		
		int facing = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		/* y is fixed in this instance */
		/* 3 by 3 */
		
		int xPos, zPos;
		
		if (facing == 0) {
			/* south */
			for (xPos = x - 1; xPos <= x + 1; xPos++) {
				for (zPos = z ; zPos <= z + 2; zPos++) {
					changeBlock(world, xPos, y, zPos);
				}
			}
		} else if (facing == 2) {
			/* north */
			for (xPos = x - 1; xPos <= x + 1; xPos++) {
				for (zPos = z; zPos >= z - 2; zPos--) {
					changeBlock(world, xPos, y, zPos);
				}
			}
		} else if (facing == 3) {
			/* east */ 
			for (zPos = z - 1; zPos <= z + 1; zPos++) {
				for (xPos = x ; xPos <= x + 2; xPos++) {
					changeBlock(world, xPos, y, zPos);
				}
			}

		} else {
			/* west */
			for (zPos = z - 1; zPos <= z + 1; zPos++) {
				for (xPos = x ; xPos >= x - 2; xPos--) {
					changeBlock(world, xPos, y, zPos);
				}
			}
		}
			
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {			
			
		if (side != 0 && side != 1) {
		
			if (player.capabilities.isCreativeMode || player.inventory.hasItem(ItemIds.FIXER_FOAM_PELLET)) {	
				int id = world.getBlockId(x, y, z);
				if (id == Block.sand.blockID || id == Block.gravel.blockID) {		
					player.inventory.consumeInventoryItem(ItemIds.FIXER_FOAM_PELLET);	
					if (!world.isRemote) {
						
						/* 9 blocks to change */
						if (stack.getItemDamage() == 0) {
							horizontalPlane(world, player, x, y, z);																
						} else {
							verticalPlane(world, player, x, y, z);	
						}
					}
				}
			}
		}
		
		return false;
						
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,	EntityPlayer player) {
		
		if (player.isSneaking()) {
			
			/* change the mode */
			int meta = stack.getItemDamage();
			stack.setItemDamage(meta == 0 ? 1 : 0);
			
			return stack;
		} else {
			return super.onItemRightClick(stack, world, player);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack,	EntityPlayer player, List info, boolean useExtraInformation) {
		int dmg = stack.getItemDamage();
		info.add(dmg == 0 ? "Horizontal mode" : "Vertical mode");
	}

}
