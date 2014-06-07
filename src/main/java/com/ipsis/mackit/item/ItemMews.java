package com.ipsis.mackit.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemMews extends ItemMK {

	public ItemMews() {
		
		super("Mob Early Warning System (Purr)");
		this.setMaxStackSize(1);
		this.setMaxDamage(FREQUENCY);
	}
	
	private static final int SCAN_RANGE = 9;
	private static final int FREQUENCY = 20;
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean isHeld) {
		
		boolean checkArea = false;
		
		if (this.getDamage(itemStack) == FREQUENCY) {
			this.setDamage(itemStack, 0);
			checkArea = true;
		} else {
			this.setDamage(itemStack, this.getDamage(itemStack) + 1);
		}
		
		if (checkArea && !world.isRemote) {
			
			double x = entity.posX;
			double y = entity.posY;
			double z = entity.posZ;
					
			List<EntityMob> e = world.getEntitiesWithinAABB(EntityMob.class, 
								AxisAlignedBB.getBoundingBox(x - SCAN_RANGE, y - SCAN_RANGE, z - SCAN_RANGE, x + SCAN_RANGE, y + SCAN_RANGE, z + SCAN_RANGE));
			
			if (!e.isEmpty())	
				world.playSoundAtEntity(entity, "dig.grass", 100, 10);									
		}
	}
}
