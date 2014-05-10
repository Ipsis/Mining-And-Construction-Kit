package com.ipsis.mackit.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.ipsis.mackit.client.gui.inventory.GuiEnchanter;
import com.ipsis.mackit.client.gui.inventory.GuiMachineBBBuilder;
import com.ipsis.mackit.client.gui.inventory.GuiMachineSqueezer;
import com.ipsis.mackit.client.gui.inventory.GuiMachineStamper;
import com.ipsis.mackit.inventory.ContainerEnchanter;
import com.ipsis.mackit.inventory.ContainerMachineBBBuilder;
import com.ipsis.mackit.inventory.ContainerMachineSqueezer;
import com.ipsis.mackit.inventory.ContainerMachineStamper;
import com.ipsis.mackit.lib.GuiIds;
import com.ipsis.mackit.tileentity.TileEnchanter;
import com.ipsis.mackit.tileentity.TileMachineBBBuilder;
import com.ipsis.mackit.tileentity.TileMachinePainter;
import com.ipsis.mackit.tileentity.TileMachineSqueezer;
import com.ipsis.mackit.tileentity.TileMachineStamper;

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
		} else if (ID == GuiIds.STAMPER) {
			TileMachineStamper te = (TileMachineStamper)world.getBlockTileEntity(x, y, z);
			return new ContainerMachineStamper(player.inventory, te);
		} else if (ID == GuiIds.PAINTER) {
			TileMachinePainter te = (TileMachinePainter)world.getBlockTileEntity(x, y, z);
			return new ContainerMachinePainter(player.inventory, te);
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
		} else if (ID == GuiIds.STAMPER) {
			TileMachineStamper te = (TileMachineStamper)world.getBlockTileEntity(x, y, z);
			return new GuiMachineStamper(player.inventory, te);
		} else if (ID == GuiIds.PAINTER) {
			TileMachinePainter te = (TileMachinePainter)world.getBlockTileEneity(x, t, z);
			return new GuiMachinePainter(player.inventory, te);
		}
		
		return null;
	}

}
