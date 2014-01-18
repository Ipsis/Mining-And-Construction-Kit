package com.ipsis.mackit.helper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;

public class Helper {

	/**
	 * Return the direction that the entity is facing (F value)
	 * @param entity the entity
	 * @return direction (ForgeDirection WEST/NORTH/EAST/SOUTH ony)
	 */
	public static ForgeDirection getFacing(EntityLivingBase entity) {
		int v = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		if (v == 0)
			return ForgeDirection.SOUTH;
		else if (v == 1)
			return ForgeDirection.WEST;
		else if (v == 2)
			return ForgeDirection.NORTH;
		else
			return ForgeDirection.EAST;
	}
}
