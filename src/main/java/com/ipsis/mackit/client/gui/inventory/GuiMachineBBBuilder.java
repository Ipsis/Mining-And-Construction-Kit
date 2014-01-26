package com.ipsis.mackit.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import com.ipsis.mackit.inventory.ContainerMachineBBBuilder;
import com.ipsis.mackit.lib.Textures;
import com.ipsis.mackit.tileentity.TileMachineBBBuilder;

public class GuiMachineBBBuilder extends GuiContainer {
	
	private TileMachineBBBuilder tileMachineBBBuilder;
	private InventoryPlayer invPlayer;
	
	public GuiMachineBBBuilder(InventoryPlayer inventoryPlayer, TileMachineBBBuilder tileMachineBBBuilder) {
		
		super(new ContainerMachineBBBuilder(inventoryPlayer, tileMachineBBBuilder));
		this.tileMachineBBBuilder = tileMachineBBBuilder;
		this.invPlayer = inventoryPlayer;
		
		xSize = 174;
		ySize = 177;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(Textures.GUI_MACHINE_BBBUILDER);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

}
