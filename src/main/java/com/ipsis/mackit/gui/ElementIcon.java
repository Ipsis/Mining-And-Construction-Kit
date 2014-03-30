package com.ipsis.mackit.gui;

import net.minecraft.item.Item;
import net.minecraft.util.Icon;

public class ElementIcon extends ElementBase {
	
	protected Icon icon;
	
	public ElementIcon(GuiBase gui, int posX, int posY) {
		
		super(gui, posX, posY);
	}
	
	public ElementIcon setIcon(Icon icon) {
		
		this.icon = icon;
		return this;
	}
	
	@Override
	public void draw() {
		
		if (icon != null)
			gui.drawIcon(icon, posX, posY, 1);
	}
}
