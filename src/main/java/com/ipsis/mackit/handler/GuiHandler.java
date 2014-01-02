package com.ipsis.mackit.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.ipsis.mackit.client.gui.inventory.GuiEnchanter;
import com.ipsis.mackit.inventory.ContainerEnchanter;
import com.ipsis.mackit.lib.GuiIds;
import com.ipsis.mackit.tileentity.TileEnchanter;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if (ID == GuiIds.ENCHANTER)
		{
			TileEnchanter tileEnchanter = (TileEnchanter)world.getBlockTileEntity(x, y,  z);
			return new ContainerEnchanter(player.inventory, tileEnchanter);	
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if (ID == GuiIds.ENCHANTER)
		{
			TileEnchanter tileEnchanter = (TileEnchanter)world.getBlockTileEntity(x, y,  z);
			return new GuiEnchanter(player.inventory, tileEnchanter);						
		}
		
		return null;
	}

}
