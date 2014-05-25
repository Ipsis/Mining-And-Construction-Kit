package com.ipsis.mackit.gui.element;

import org.lwjgl.opengl.GL11;

import cofh.api.tileentity.IEnergyInfo;

import com.ipsis.cofhlib.gui.GuiBase;
import com.ipsis.cofhlib.gui.element.TabBase;
import com.ipsis.cofhlib.util.StringHelper;

/**
 * 
 * Based off the CoFHCore energy tab information layout
 * Uses the CoFHCore energy consumer background colour
 *
 */

public class TabEnergy extends TabBase {
	
	IEnergyInfo te;
	
	public TabEnergy(GuiBase gui, IEnergyInfo te) {
	
		super(gui);
		this.te = te;
		maxHeight = 92;
		maxWidth = 100;
		backgroundColor = 0x0a76d0;
	}
	
	@Override
	public void draw() {
	
		drawBackground();
		/* TODO icon draw */
		
		if (!isFullyOpened()) {
			return;
		}
		
		/* Information */
		GuiBase.guiFontRenderer.drawString(te.getInfoEnergyPerTick() + " RF/t", posX + 16, posY + 30, 0x000000);
		GuiBase.guiFontRenderer.drawString(te.getInfoMaxEnergyPerTick() + " RF/t", posX + 16, posY + 54, 0x000000);
		GuiBase.guiFontRenderer.drawString(te.getInfoEnergy() + " RF", posX + 16, posY + 78, 0x000000);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
