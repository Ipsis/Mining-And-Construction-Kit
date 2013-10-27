package ipsis.mackit.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ipsis.mackit.lib.BlockIds;
import ipsis.mackit.lib.ItemIds;
import ipsis.mackit.lib.Strings;

public class ItemFixerFoamGun extends ItemMK {
	
	public ItemFixerFoamGun(int id) {
		
		super(id);
		this.setUnlocalizedName(Strings.FIXER_FOAM_GUN_NAME);
		this.setMaxStackSize(1);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {			
				
				if (player.capabilities.isCreativeMode || player.inventory.hasItem(ItemIds.FIXER_FOAM_PELLET)) {			
					int id = world.getBlockId(x, y, z);
					if (id == Block.sand.blockID || id == Block.gravel.blockID) {		
						player.inventory.consumeInventoryItem(ItemIds.FIXER_FOAM_PELLET);	
						if (!world.isRemote) {
							world.setBlock(x, y, z, BlockIds.FIXED_EARTH, (id == Block.sand.blockID ? 0 : 1) , 3);
						}
					}
				}

		
		return false;
						
	}

}
