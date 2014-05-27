package com.ipsis.mackit.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

import com.ipsis.cofhlib.gui.GuiBase;
import com.ipsis.cofhlib.gui.element.ElementButton;
import com.ipsis.cofhlib.gui.element.ElementDualScaled;
import com.ipsis.cofhlib.gui.element.ElementEnergyStored;
import com.ipsis.cofhlib.gui.element.ElementFluidTank;
import com.ipsis.mackit.block.TileMachineStamper;
import com.ipsis.mackit.gui.element.ElementIcon;
import com.ipsis.mackit.gui.element.TabEnergy;
import com.ipsis.mackit.gui.element.TabInfo;
import com.ipsis.mackit.manager.MKManagers;
import com.ipsis.mackit.reference.Reference;

public class GuiMachineStamper extends GuiBase {

	private static final String TEXTURE_STR = Reference.MOD_ID.toLowerCase() + ":textures/gui/machineStamper.png";
	private static final String PROGRESS_TEXTURE_STR = Reference.MOD_ID.toLowerCase() + ":textures/gui/progress.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/" + "machineStamper.png");

	private static final String BTN_UP_STR = "Up";
	private static final String BTN_DN_STR  = "Down";
	
	private TileMachineStamper te;
	private InventoryPlayer invPlayer;
	private ElementDualScaled progress;
	private ElementButton up;
	private ElementButton down;
	private ElementIcon selected;
	
	public GuiMachineStamper(InventoryPlayer invPlayer, TileMachineStamper te) {
		
		super(new ContainerMachineStamper(invPlayer, te), TEXTURE);
		this.te = te;
		this.invPlayer = invPlayer;
		
		xSize = 174;
		ySize = 177;
	}
	
	@Override
	public void initGui() {

		super.initGui();
		
		addElement(new ElementEnergyStored(this, 7, 22, this.te.getEnergyStorage()));
		
		/* tanks */
		addElement(new ElementFluidTank(this, 150, 12, this.te.tankMgr.getTank(this.te.PURE_TANK)));
		
		this.progress = ((ElementDualScaled)addElement(new ElementDualScaled(this, 71, 35).setMode(1).setBackground(false).setSize(24, 16).setTexture(PROGRESS_TEXTURE_STR, 64, 64)));
		addElement(progress);
		
		this.selected = ((ElementIcon)addElement(new ElementIcon(this, 78, 62).setIcon(Items.bone.getIconFromDamage(0))));
		addElement(selected);
		
		/* buttons */
		addElement(new ElementButton(this, 60, 62, BTN_DN_STR, 176, 0, 176, 16, 176, 32, 16, 16, TEXTURE_STR));
		addElement(new ElementButton(this, 96, 62, BTN_UP_STR, 192, 0, 192, 16, 192, 32, 16, 16, TEXTURE_STR));
		
		addTab(new TabEnergy(this, this.te));
		addTab(new TabInfo(this, "Stamp dyes from your supply of pure dye"));
	}
	
	@Override
	protected void updateElements() {

		this.progress.setQuantity(this.te.getScaledProgress(24));
		this.selected.setIcon(MKManagers.stamperMgr.getIcon(te.getSelected()));
	}
	
	@Override
	public void handleElementButtonClick(String buttonName, int mouseButton) {
		
		if (buttonName.equals(BTN_UP_STR)) {
			te.incSelected();
		} else if (buttonName.equals(BTN_DN_STR)) {
			te.decSelected();
		}
	}
}
