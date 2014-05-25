package com.ipsis.mackit.gui.element;

import net.minecraft.util.IIcon;

import com.ipsis.cofhlib.gui.GuiBase;
import com.ipsis.cofhlib.gui.element.ElementBase;

public class ElementIcon extends ElementBase {

	protected IIcon icon;

	public ElementIcon(GuiBase gui, int posX, int posY) {

		super(gui, posX, posY);
	}

	public ElementIcon setIcon(IIcon icon) {

		this.icon = icon;
		return this;
	}

	@Override
	public void draw() {

		if (icon != null)
			gui.drawIcon(icon, posX, posY, 1);
	}
	
}
