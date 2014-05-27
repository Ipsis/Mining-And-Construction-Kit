package com.ipsis.mackit.util.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;

import com.ipsis.mackit.block.TileMachineDyeFiller;
import com.ipsis.mackit.block.TileMachinePainter;
import com.ipsis.mackit.block.TileMachineSqueezer;
import com.ipsis.mackit.block.TileMachineStamper;
import com.ipsis.mackit.block.TilePortaChant;
import com.ipsis.mackit.container.ContainerMachineDyeFiller;
import com.ipsis.mackit.container.ContainerMachinePainter;
import com.ipsis.mackit.container.ContainerMachineSqueezer;
import com.ipsis.mackit.container.ContainerMachineStamper;
import com.ipsis.mackit.container.ContainerPortaChant;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CommonProxy implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null) {
		
			if (te instanceof TilePortaChant) {
				return new ContainerPortaChant(player.inventory, (TilePortaChant)te);
			} else if (te instanceof TileMachineSqueezer) {
				return new ContainerMachineSqueezer(player.inventory, (TileMachineSqueezer)te);
			} else if (te instanceof TileMachineStamper) {
				return new ContainerMachineStamper(player.inventory, (TileMachineStamper)te);
			} else if (te instanceof TileMachinePainter) {
				return new ContainerMachinePainter(player.inventory, (TileMachinePainter)te);
			} else if (te instanceof TileMachineDyeFiller) {
				return new ContainerMachineDyeFiller(player.inventory, (TileMachineDyeFiller)te);
			} else {
				return null;
			}			
		} else {
			return null;
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerIcons(TextureStitchEvent.Pre event) {

	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void initializeIcons(TextureStitchEvent.Post event) {

	}

	
}
