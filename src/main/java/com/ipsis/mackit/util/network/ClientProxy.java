package com.ipsis.mackit.util.network;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;

import com.ipsis.mackit.block.MKBlocks;
import com.ipsis.mackit.block.TileMachineSqueezer;
import com.ipsis.mackit.block.TilePortaChant;
import com.ipsis.mackit.container.GuiMachineSqueezer;
import com.ipsis.mackit.container.GuiPortaChant;
import com.ipsis.mackit.fluid.MKFluids;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {

		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null) {
		
			if (te instanceof TilePortaChant) {
				return new GuiPortaChant(player.inventory, (TilePortaChant)te);
			} else if (te instanceof TileMachineSqueezer) {				
				return new GuiMachineSqueezer(player.inventory, (TileMachineSqueezer)te);				
			} else {
				return null;
			}
		} else {
			return null;
		}
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
