package com.ipsis.mackit.core.handlers;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

import com.ipsis.mackit.block.ModBlocks;
import com.ipsis.mackit.fluid.ModFluids;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TextureStitchHandler {

	@ForgeSubscribe
	@SideOnly(Side.CLIENT)
    public void postStitch(TextureStitchEvent.Post event) {
		
		ModFluids.blueDye.setIcons(ModBlocks.blueDye.getIcon(0, 0), ModBlocks.blueDye.getIcon(2, 0));
		ModFluids.pureDye.setIcons(ModBlocks.pureDye.getIcon(0, 0), ModBlocks.pureDye.getIcon(2, 0));
		ModFluids.redDye.setIcons(ModBlocks.redDye.getIcon(0, 0), ModBlocks.redDye.getIcon(2, 0));
		ModFluids.whiteDye.setIcons(ModBlocks.whiteDye.getIcon(0, 0), ModBlocks.whiteDye.getIcon(2, 0));
		ModFluids.yellowDye.setIcons(ModBlocks.yellowDye.getIcon(0, 0), ModBlocks.yellowDye.getIcon(2, 0));
    }
}
