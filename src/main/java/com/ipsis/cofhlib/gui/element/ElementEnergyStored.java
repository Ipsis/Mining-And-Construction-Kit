package com.ipsis.cofhlib.gui.element;

import java.util.List;

import net.minecraft.util.ResourceLocation;
import cofh.api.energy.IEnergyStorage;

import com.ipsis.cofhlib.gui.GuiBase;
import com.ipsis.cofhlib.gui.GuiProps;
import com.ipsis.cofhlib.render.RenderHelper;
import com.ipsis.cofhlib.util.MathHelper;

public class ElementEnergyStored extends ElementBase {

	public static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(GuiProps.PATH_ELEMENTS + "Energy.png");
	public static final int DEFAULT_SCALE = 42;

	protected IEnergyStorage storage;

	public ElementEnergyStored(GuiBase gui, int posX, int posY, IEnergyStorage storage) {

		super(gui, posX, posY);
		this.storage = storage;

		this.texture = DEFAULT_TEXTURE;
		this.sizeX = 16;
		this.sizeY = DEFAULT_SCALE;

		this.texW = 32;
		this.texH = 64;
	}

	@Override
	public void draw() {

		if (!visible) {
			return;
		}
		RenderHelper.bindTexture(texture);
		drawTexturedModalRect(posX, posY, 0, 0, sizeX, sizeY);
		int qty = getScaled();
		drawTexturedModalRect(posX, posY + DEFAULT_SCALE - qty, 16, DEFAULT_SCALE - qty, sizeX, qty);
	}

	@Override
	public void addTooltip(List<String> list) {

		if (storage.getMaxEnergyStored() < 0) {
			list.add("Infinite RF");
		} else {
			list.add("" + storage.getEnergyStored() + " / " + storage.getMaxEnergyStored() + " RF");
		}
	}

	int getScaled() {

		if (storage.getMaxEnergyStored() < 0) {
			return sizeY;
		}
		return MathHelper.round(storage.getEnergyStored() * sizeY / storage.getMaxEnergyStored());
	}

}
