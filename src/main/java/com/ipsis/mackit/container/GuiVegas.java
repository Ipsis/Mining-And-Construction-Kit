package com.ipsis.mackit.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.ipsis.cofhlib.gui.GuiBase;
import com.ipsis.cofhlib.gui.element.ElementButton;
import com.ipsis.mackit.block.TileVegas;
import com.ipsis.mackit.network.PacketHandler;
import com.ipsis.mackit.network.message.MessageGui;
import com.ipsis.mackit.reference.Gui;
import com.ipsis.mackit.reference.Reference;

public class GuiVegas extends GuiBase {

	public static final String TEXTURE_STR = "mackit:textures/gui/vegas.png";
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/" + "vegas.png");
	
	private static final String BTN_GAMBLE_STR = "Gamble";
	
	private TileVegas te;
	private InventoryPlayer invPlayer;
	private ElementButton gamble;
	
	public GuiVegas(InventoryPlayer invPlayer, TileVegas te) {
		
		super(new ContainerVegas(invPlayer, te), TEXTURE);
		this.te = te;
		this.invPlayer = invPlayer;
		
		xSize = 174;
		ySize = 177;
	}
	
	@Override
	public void initGui() {

		super.initGui();
		
		addElement(new ElementButton(this, 78, 35, BTN_GAMBLE_STR, 176, 0, 176, 16, 176, 32, 16, 16, TEXTURE_STR));
	}
	

	@Override
	public void handleElementButtonClick(String buttonName, int mouseButton) {
		
		if (buttonName.equals(BTN_GAMBLE_STR)) {
			PacketHandler.INSTANCE.sendToServer(new MessageGui(
					te.xCoord, te.yCoord, te.zCoord,
					(byte)Gui.VEGAS,
					(byte)Gui.TYPE_BUTTON, 
					(byte)Gui.VEGAS_GAMBLE, 
					0, 0));
		}
	}
}
