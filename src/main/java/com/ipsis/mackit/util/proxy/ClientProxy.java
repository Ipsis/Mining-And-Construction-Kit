package com.ipsis.mackit.util.proxy;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;

import com.ipsis.mackit.block.MKBlocks;
import com.ipsis.mackit.block.TileDyeLeech;
import com.ipsis.mackit.block.TileDyeLeechRenderer;
import com.ipsis.mackit.block.TileMachineDyeFiller;
import com.ipsis.mackit.block.TileMachinePainter;
import com.ipsis.mackit.block.TileMachineSqueezer;
import com.ipsis.mackit.block.TileMachineStamper;
import com.ipsis.mackit.block.TilePortaChant;
import com.ipsis.mackit.block.TileVegas;
import com.ipsis.mackit.container.GuiMachineDyeFiller;
import com.ipsis.mackit.container.GuiMachinePainter;
import com.ipsis.mackit.container.GuiMachineSqueezer;
import com.ipsis.mackit.container.GuiMachineStamper;
import com.ipsis.mackit.container.GuiPortaChant;
import com.ipsis.mackit.container.GuiTorchPouch;
import com.ipsis.mackit.container.GuiVegas;
import com.ipsis.mackit.container.InventoryTorchPouch;
import com.ipsis.mackit.fluid.MKFluids;
import com.ipsis.mackit.reference.Gui;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

	
	public void initRenderingAndTexture() {
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileDyeLeech.class, new TileDyeLeechRenderer());		
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {

		if (ID == Gui.TORCH_POUCH) {
			
			return new GuiTorchPouch(player, new InventoryTorchPouch(player.getHeldItem()));
		} else {
		
			TileEntity te = world.getTileEntity(x, y, z);
			if (te != null) {
			
				if (te instanceof TilePortaChant) {
					return new GuiPortaChant(player.inventory, (TilePortaChant)te);
				} else if (te instanceof TileMachineSqueezer) {				
					return new GuiMachineSqueezer(player.inventory, (TileMachineSqueezer)te);				
				} else if (te instanceof TileMachineStamper) {				
					return new GuiMachineStamper(player.inventory, (TileMachineStamper)te);		
				} else if (te instanceof TileMachinePainter) {				
					return new GuiMachinePainter(player.inventory, (TileMachinePainter)te);	
				} else if (te instanceof TileMachineDyeFiller) {				
					return new GuiMachineDyeFiller(player.inventory, (TileMachineDyeFiller)te);	
				} else if (te instanceof TileVegas) {
					return new GuiVegas(player.inventory, (TileVegas)te);
				}
			}
		}
			
		return null;
	}
	
	@Override
	@SubscribeEvent
	public void registerIcons(TextureStitchEvent.Pre event) {

		if (event.map.getTextureType() == 0) {
			
			/* Nothing ???? */
			//MKBlocks.blockFluidDyeRed.registerBlockIcons((IIconRegister)event.map);
		}
	}

	@Override
	@SubscribeEvent
	public void initializeIcons(TextureStitchEvent.Post event) {

		if (event.map.getTextureType() == 0) {
			
			setFluidIcons(MKFluids.fluidDyeRed, MKBlocks.blockFluidDyeRed);
			setFluidIcons(MKFluids.fluidDyeYellow, MKBlocks.blockFluidDyeYellow);
			setFluidIcons(MKFluids.fluidDyeBlue, MKBlocks.blockFluidDyeBlue);
			setFluidIcons(MKFluids.fluidDyeWhite, MKBlocks.blockFluidDyeWhite);
			setFluidIcons(MKFluids.fluidDyePure, MKBlocks.blockFluidDyePure);
		}
	}
	
	private void setFluidIcons(Fluid f, Block b) {
		
		f.setIcons(b.getIcon(0, 0), b.getIcon(2, 0));
	}
}
