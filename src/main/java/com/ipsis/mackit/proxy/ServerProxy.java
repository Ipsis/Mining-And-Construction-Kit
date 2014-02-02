package com.ipsis.mackit.proxy;

import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.Player;


/*
 * Client -> server messages
 */
public class ServerProxy extends CommonProxy {

	public void handleTileEntityPacket(Player player, int x, int y, int z, ForgeDirection orientation, byte active, String customName) {
		
		/* Nothing */
	}
}
