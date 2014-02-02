package com.ipsis.mackit.proxy;

import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.Player;

public interface IProxy {

	 public abstract void handleTileEntityPacket(Player player, int x, int y, int z, ForgeDirection orientation, byte state, String customName);
}
