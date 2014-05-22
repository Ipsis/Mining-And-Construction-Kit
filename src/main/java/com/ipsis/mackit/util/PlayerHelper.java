package com.ipsis.mackit.util;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerHelper {

	public static boolean canInteractWithPlayer(EntityPlayer entityPlayer, int xCoord, int yCoord, int zCoord) {
		
		return entityPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5)  < 64;
	}
}
