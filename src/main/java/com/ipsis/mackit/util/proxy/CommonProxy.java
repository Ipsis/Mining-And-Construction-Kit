package com.ipsis.mackit.util.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;

import com.ipsis.mackit.block.TileDyeLeech;
import com.ipsis.mackit.block.TileDyeLeechRenderer;
import com.ipsis.mackit.block.TileMachineDyeFiller;
import com.ipsis.mackit.block.TileMachinePainter;
import com.ipsis.mackit.block.TileMachineSqueezer;
import com.ipsis.mackit.block.TileMachineStamper;
import com.ipsis.mackit.block.TilePortaChant;
import com.ipsis.mackit.block.TileVegas;
import com.ipsis.mackit.container.ContainerMachineDyeFiller;
import com.ipsis.mackit.container.ContainerMachinePainter;
import com.ipsis.mackit.container.ContainerMachineSqueezer;
import com.ipsis.mackit.container.ContainerMachineStamper;
import com.ipsis.mackit.container.ContainerPortaChant;
import com.ipsis.mackit.container.ContainerTorchPouch;
import com.ipsis.mackit.container.ContainerVegas;
import com.ipsis.mackit.container.InventoryTorchPouch;
import com.ipsis.mackit.reference.Gui;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CommonProxy implements IGuiHandler {

	public void initRenderingAndTexture() {
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileDyeLeech.class, new TileDyeLeechRenderer());		
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		
		if (ID == Gui.TORCH_POUCH) {
			
			return new ContainerTorchPouch(player, new InventoryTorchPouch(player.getHeldItem()));
		} else {
		
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
				} else if (te instanceof TileVegas) {
					return new ContainerVegas(player.inventory, (TileVegas)te);
				}			
			}
		}
		
		return null;
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
