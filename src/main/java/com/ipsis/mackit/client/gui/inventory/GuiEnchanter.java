package com.ipsis.mackit.client.gui.inventory;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import scala.Int;

import com.ipsis.mackit.inventory.ContainerEnchanter;
import com.ipsis.mackit.lib.GuiIds;
import com.ipsis.mackit.lib.Textures;
import com.ipsis.mackit.network.PacketHandler;
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
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(Textures.GUI_ENCHANTER);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
				
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

		fontRenderer.drawString("Enchant-o-mat", 8, 6, 0x404040);
		
		String str = Byte.toString(tileEnchanter.getEnchantLevel());
		fontRenderer.drawString(str, 70, 40, 0x404040);
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		
		incrButton.enabled = tileEnchanter.getEnchantLevel() < TileEnchanter.MAX_ENCHANT_LEVEL;
		decrButton.enabled = tileEnchanter.getEnchantLevel() > TileEnchanter.MIN_ENCHANT_LEVEL;
		enchantButton.enabled = tileEnchanter.getCanEnchant();
	}
	
	public static final int GUI_BUTTON_INCR = 0;
	public static final int GUI_BUTTON_DESR = 1;
	public static final int GUI_BUTTON_ENCHANT = 2;
	
	private GuiButton incrButton;
	private GuiButton decrButton;
	private GuiButton enchantButton;
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		
		incrButton = new GuiButton(GUI_BUTTON_INCR, guiLeft + 68, guiTop + 20, 18, 18, "+");
		incrButton.enabled = tileEnchanter.getEnchantLevel() == 30 ? false : true;
		buttonList.add(incrButton);
		decrButton = new GuiButton(GUI_BUTTON_DESR, guiLeft + 68, guiTop + 50, 18, 18, "-");
		decrButton.enabled = tileEnchanter.getEnchantLevel() == 30 ? false : true;
		buttonList.add(decrButton);
				
		enchantButton = new GuiButton(GUI_BUTTON_ENCHANT, guiLeft + 55, guiTop + 72, 48, 18, "Enchant");
		enchantButton.enabled = tileEnchanter.getCanEnchant();
		buttonList.add(enchantButton);		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		PacketHandler.sendInterfacePacket((byte)GuiIds.ENCHANTER, (byte)PacketHandler.INTERFACE_PKT_BUTTON, (byte)button.id);
	}
	
}
