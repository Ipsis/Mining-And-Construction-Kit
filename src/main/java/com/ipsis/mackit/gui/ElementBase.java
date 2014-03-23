package com.ipsis.mackit.gui;

import java.util.List;

import net.minecraft.util.ResourceLocation;

public abstract class ElementBase {

	protected GuiBase gui;
	protected ResourceLocation texture;
	
	protected int posX;
	protected int posY;
	
	protected int sizeX;
	protected int sizeY;
	
	public int texW = 256;
	public int texH = 256;
	
	public ElementBase(GuiBase gui, int posX, int posY) {
		
		this.gui = gui;
		this.posX = gui.getGuiLeft() + posX;
		this.posY = gui.getGuiTop() + posY;
	}
	
	public ElementBase setTexture(String texture, int texW, int texH) {

		this.texture = new ResourceLocation(texture);
		this.texW = texW;
		this.texH = texH;
		return this;
	}
	
	public ElementBase setPosition(int posX, int posY) {
		
		this.posX = gui.getGuiLeft() + posX;
		this.posY = gui.getGuiTop() + posY;
		return this;
	}
	
	public ElementBase setSize(int sizeX, int sizeY) {

		this.sizeX = sizeX;
		this.sizeY = sizeY;
		return this;
	}
	
	public void addTooltip(List<String> list) {

	}
	
	public boolean intersectsWith(int mouseX, int mouseY) {
		
		mouseX += gui.getGuiLeft();
		mouseY += gui.getGuiTop();
		
		if (mouseX >= this.posX && mouseX <= this.posX + this.sizeX
				&& 	mouseY >= this.posY	&& mouseY <= this.posY + this.sizeY)
			return true;
		
		return false;
	}
	
	public abstract void draw();
	
	public void draw(int x, int y) {
		
		this.posX = x;
		this.posY = y;
		draw();
	}
	
	/* Draw at x,y. Texture is at u,v on the file and we want to draw w,h */
	public void drawTexturedModalRect(int x, int y, int u, int v, int width, int height) {
		
		gui.drawSizedTexturedModalRect(x, y, u, v, width, height, texW, texH);
	}
}
