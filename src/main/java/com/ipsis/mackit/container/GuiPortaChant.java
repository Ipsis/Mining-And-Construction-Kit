package com.ipsis.mackit.container;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ipsis.mackit.block.TilePortaChant;
import com.ipsis.mackit.reference.Reference;

public class GuiPortaChant extends GuiContainer {

	private static final ResourceLocation GUI_ENCHANTER = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/" + "enchanter.png");
	
	private TilePortaChant te;
	private InventoryPlayer invPlayer;

	public GuiPortaChant(InventoryPlayer invPlayer, TilePortaChant te) {

		super(new ContainerPortaChant(invPlayer, te));
		this.te = te;
		this.invPlayer = invPlayer;
		
		xSize = 174;
		ySize = 177;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_ENCHANTER);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

}
