package com.ipsis.mackit.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import cofh.api.energy.IEnergyStorage;

import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.lib.Textures;

public class ElementEnergyStored extends ElementBase {
	
	protected IEnergyStorage storage;
	public static final int DEFAULT_SCALE = 42;
	
	public ElementEnergyStored(GuiBase gui, int posX, int posY, IEnergyStorage storage) {
		
		super(gui, posX, posY);
		this.storage = storage;
		this.texture = Textures.GUI_ENERGY;
		
		this.sizeX = 16;
		this.sizeY = DEFAULT_SCALE;
		
		this.texW = 32;
		this.texH = 64;
	}
	
	@Override
	public void draw() {
		
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
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
		
		if (storage.getMaxEnergyStored() < 0)
			return sizeY;
		
		return storage.getEnergyStored() * sizeY / storage.getMaxEnergyStored();
		
		//return MathHelper.round(storage.getEnergyStored() * sizeY / storage.getMaxEnergyStored());
	}
	
	@Override
	public String toString() {
		
		return String.format("ElementEnergyStored: posX=%d, posY=%d", posX, posY);
	}
}
