package com.ipsis.mackit.client.gui.inventory;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import com.ipsis.mackit.inventory.ContainerEnchanter;
import com.ipsis.mackit.lib.Textures;
import com.ipsis.mackit.tileentity.TileEnchanter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEnchanter extends GuiContainer {

	private TileEnchanter tileEnchanter;
	
	public GuiEnchanter(InventoryPlayer inventoryPlayer, TileEnchanter tileEnchanter) {
		
		super(new ContainerEnchanter(inventoryPlayer, tileEnchanter));
		this.tileEnchanter = tileEnchanter;
		
		xSize = 174;
		ySize = 177;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(Textures.GUI_ENCHANTER);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
				
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		
		
		GuiButton enchantButton = new GuiButton(0, guiLeft + 80, guiTop + 14, 48, 20, "Enchant");
		enchantButton.enabled = true;
		buttonList.add(enchantButton);		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		
	}
	
}
