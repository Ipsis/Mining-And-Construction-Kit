package com.ipsis.mackit.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.ipsis.cofhlib.gui.GuiBase;
import com.ipsis.cofhlib.gui.element.ElementButton;
import com.ipsis.mackit.block.TilePortaChant;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.reference.Reference;

public class GuiPortaChant extends GuiBase {

	public static final String TEXTURE_STR = "mackit:textures/gui/enchanter.png";
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/" + "enchanter.png");
	
	private static final String BTN_UP_STR = "Up";
	private static final String BTN_DN_STR  = "Down";
	
	private TilePortaChant te;
	private InventoryPlayer invPlayer;
	private ElementButton up;
	private ElementButton down;
	
	public GuiPortaChant(InventoryPlayer invPlayer, TilePortaChant te) {
		
		super(new ContainerPortaChant(invPlayer, te), TEXTURE);
		this.te = te;
		this.invPlayer = invPlayer;
		
		xSize = 174;
		ySize = 177;
	}
	
	@Override
	public void initGui() {

		super.initGui();
		
		up = ((ElementButton)addElement(new ElementButton(this, 77, 13, BTN_UP_STR, 176, 0, 176, 16, 176, 32, 16, 16, TEXTURE_STR)));
		down = ((ElementButton)addElement(new ElementButton(this, 77, 56, BTN_DN_STR, 192, 0, 192, 16, 192, 32, 16, 16, TEXTURE_STR)));
		
		
		//addTab(new TabInfo(this, "A portable enchanting table\n\nUse your levels before you plunge into lava.", 1));
	}
	
	private static final int COL_OK = 0x404040;
	private static final int COL_BAD = 0xFF0000;
	
	private int getEnchantColour(int level) {
		
		if (this.mc.thePlayer.capabilities.isCreativeMode)
			return COL_OK;
		
		if (this.mc.thePlayer.experienceLevel >= level)
			return COL_OK;
		else
			return COL_BAD;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

		super.drawGuiContainerForegroundLayer(x, y);
		fontRendererObj.drawString(Byte.toString(te.getEnchantLevel()) + " Levels", 70, 35, getEnchantColour(te.getEnchantLevel()));		
	}
	
	@Override
	protected void updateElements() {

		super.updateElements();
		
		if (te.getEnchantLevel() == te.MAX_ENCHANT) 
			up.setDisabled();
		else
			up.setActive();
			
		if (te.getEnchantLevel() == te.MIN_ENCHANT)
			down.setDisabled();
		else
			down.setActive();
	}
	
	@Override
	public void handleElementButtonClick(String buttonName, int mouseButton) {
		
		if (buttonName.equals(BTN_UP_STR))
			te.incEnchantLevel();
		else if (buttonName.equals(BTN_DN_STR))
			te.decEnchantLevel();
	}
}
