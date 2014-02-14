package com.ipsis.mackit.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.ipsis.mackit.client.gui.inventory.GuiEnchanter;
import com.ipsis.mackit.client.gui.inventory.GuiMachineBBBuilder;
import com.ipsis.mackit.client.gui.inventory.GuiMachineSqueezer;
import com.ipsis.mackit.inventory.ContainerEnchanter;
import com.ipsis.mackit.inventory.ContainerMachineBBBuilder;
import com.ipsis.mackit.inventory.ContainerMachineSqueezer;
import com.ipsis.mackit.lib.GuiIds;
import com.ipsis.mackit.tileentity.TileEnchanter;
import com.ipsis.mackit.tileentity.TileMachineBBBuilder;
import com.ipsis.mackit.tileentity.TileMachineSqueezer;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if (ID == GuiIds.ENCHANTER) {
			TileEnchanter te = (TileEnchanter)world.getBlockTileEntity(x, y,  z);
			return new ContainerEnchanter(player.inventory, te);	
		} else if (ID == GuiIds.BBBUILDER) {
			TileMachineBBBuilder te = (TileMachineBBBuilder)world.getBlockTileEntity(x, y,  z);
			return new ContainerMachineBBBuilder(player.inventory, te);	
		} else if (ID == GuiIds.SQUEEZER) {
			TileMachineSqueezer te = (TileMachineSqueezer)world.getBlockTileEntity(x, y, z);
			return new ContainerMachineSqueezer(player.inventory, te);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		if (ID == GuiIds.ENCHANTER) {
			TileEnchanter te = (TileEnchanter)world.getBlockTileEntity(x, y,  z);
			return new GuiEnchanter(player.inventory, te);						
		} else if (ID == GuiIds.BBBUILDER) {
			TileMachineBBBuilder te = (TileMachineBBBuilder)world.getBlockTileEntity(x, y,  z);
			return new GuiMachineBBBuilder(player.inventory, te);						
		} else if (ID == GuiIds.SQUEEZER) {
			TileMachineSqueezer te = (TileMachineSqueezer)world.getBlockTileEntity(x, y,  z);
			return new GuiMachineSqueezer(player.inventory, te);						
		}
		
		return null;
	}

}
