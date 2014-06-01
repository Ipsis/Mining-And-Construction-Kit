package com.ipsis.mackit.block;

import java.awt.Color;
import java.util.Arrays;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.BiomeGenBase;

import com.ipsis.cofhlib.util.ColorHelper;
import com.ipsis.mackit.helper.LogHelper;

public class TileDyeLeech extends TileEntity {

	private int currTicks;
	private static final int UPDATE_FREQ = 20;
	
	private static final int[] DYE_COLORS_SORTED;
	static {
		DYE_COLORS_SORTED = ColorHelper.DYE_COLORS.clone();
	    Arrays.sort(DYE_COLORS_SORTED);
	}
	
	public TileDyeLeech() {
		
	}
	
	private int rgbToDye(int rgb) {
		
		int color = DYE_COLORS_SORTED[0];
		
		for (int c : DYE_COLORS_SORTED) {

			if (c > rgb)
				break;
			else
				color = c;
		}
		
		LogHelper.error("rgbToDye: " + Integer.toHexString(rgb) + "->" + Integer.toHexString(color));
		
		return color;
	}
	
	@Override
	public void updateEntity() {

		currTicks++;
		if (currTicks % UPDATE_FREQ != 0)
			return;
		
		if (worldObj.isRemote)
			return;
		
		/* store biome on placement */
		BiomeGenBase g = worldObj.getBiomeGenForCoords(xCoord, zCoord);
		LogHelper.error("updateEntity " + g.biomeName + " " + Integer.toHexString(g.color));
		/* convert the rgb biome colour to a dye colour ???? */
		rgbToDye(g.color & 0xFFFFFF);
		
				
		
	}
}
