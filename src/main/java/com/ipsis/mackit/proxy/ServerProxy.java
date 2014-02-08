package com.ipsis.mackit.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.common.ForgeDirection;

import com.ipsis.mackit.inventory.ContainerEnchanter;
import com.ipsis.mackit.lib.GuiIds;
import com.ipsis.mackit.tileentity.TileEnchanter;

import cpw.mods.fml.common.network.Player;


/*
 * Client -> server messages
 */
public class ServerProxy extends CommonProxy {

	public void handlePacketTileEntity(Player player, int x, int y, int z, ForgeDirection orientation, boolean active, String customName) {
		
		/* Nothing */
	}

	@Override
	public void handlePacketGui(Player player, int guiId, int ctrlType, int ctrlId, int ctrlData) {

		EntityPlayer entityPlayer = (EntityPlayer)player;
		Container container = entityPlayer.openContainer;
		
		if (container != null) {
			if (guiId == GuiIds.ENCHANTER && container instanceof ContainerEnchanter) {
				TileEnchanter te = ((ContainerEnchanter)container).getTileEntity();
				/*if (te != null)
					te.handleInterfacePacket(ctrlType, data, player); */
			}
		}	
	}	
}
