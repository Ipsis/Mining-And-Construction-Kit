package com.ipsis.mackit.gui.element;

import org.lwjgl.opengl.GL11;

import com.ipsis.cofhlib.gui.GuiBase;
import com.ipsis.cofhlib.gui.element.TabBase;

/*
 * Uses the CoFHCore information background colour
 *
 */

public class TabInfo extends TabBase {
	
	private String info;

	public TabInfo(GuiBase gui, String info) {
		
		super(gui);
		maxHeight = 92;
		maxWidth = 100;
		this.info = info;
		backgroundColor = 0x555555;
	}
	
	@Override
	public void draw() {
	
		drawBackground();
		/* TODO icon draw */
		
		if (!isFullyOpened()) {
			return;
		}
		
		/* Information */
		GuiBase.guiFontRenderer.drawString(info, posX + 16, posY + 30, 0x000000);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
