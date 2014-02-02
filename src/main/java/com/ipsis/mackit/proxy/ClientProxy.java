package com.ipsis.mackit.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import com.ipsis.mackit.tileentity.TileMachinePowered;

import cpw.mods.fml.common.network.Player;

/*
 * Server -> client messages
 */
public class ClientProxy extends CommonProxy {


	public void handleTileEntityPacket(Player player, int x, int y, int z, ForgeDirection orientation, byte active, String customName) {
		
		TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z);
		
		if (te != null && te instanceof TileMachinePowered) {
			//((TileMachinePowered)te).setFacing(orientation);
			
		}
	}
	
}
