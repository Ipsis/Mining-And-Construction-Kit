package com.ipsis.mackit.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

import com.ipsis.cofhlib.gui.GuiBase;
import com.ipsis.cofhlib.gui.element.ElementButton;
import com.ipsis.cofhlib.gui.element.ElementDualScaled;
import com.ipsis.cofhlib.gui.element.ElementEnergyStored;
import com.ipsis.cofhlib.gui.element.ElementFluidTank;
import com.ipsis.mackit.block.TileMachinePainter;
import com.ipsis.mackit.block.TileMachineStamper;
import com.ipsis.mackit.gui.element.ElementIcon;
import com.ipsis.mackit.gui.element.TabEnergy;
import com.ipsis.mackit.gui.element.TabInfo;
import com.ipsis.mackit.reference.Reference;

public class GuiMachinePainter extends GuiBase {

	private static final String TEXTURE_STR = Reference.MOD_ID.toLowerCase() + ":textures/gui/machinePainter.png";
	private static final String PROGRESS_TEXTURE_STR = Reference.MOD_ID.toLowerCase() + ":textures/gui/progress.png";
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/" + "machinePainter.png");
	
	private TileMachinePainter te;
	private InventoryPlayer invPlayer;
	private ElementDualScaled progress;
	
	public GuiMachinePainter(InventoryPlayer invPlayer, TileMachinePainter te) {
		
		super(new ContainerMachinePainter(invPlayer, te), TEXTURE);
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
		
		addTab(new TabEnergy(this, this.te));
		addTab(new TabInfo(this, "Paint your blocks on the cheap"));
	}
	
}
