package com.ipsis.mackit.helper;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerHelper {

	public static boolean canInteractWithPlayer(EntityPlayer entityPlayer, int xCoord, int yCoord, int zCoord) {
		
		return entityPlayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64.0D;
	}
}
