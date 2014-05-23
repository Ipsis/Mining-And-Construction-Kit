package com.ipsis.mackit.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.ipsis.cofhlib.gui.GuiBase;
import com.ipsis.cofhlib.gui.element.ElementDualScaled;
import com.ipsis.cofhlib.gui.element.ElementEnergyStored;
import com.ipsis.cofhlib.gui.element.ElementFluidTank;
import com.ipsis.mackit.block.TileMachineSqueezer;
import com.ipsis.mackit.reference.Reference;

public class GuiMachineSqueezer extends GuiBase {
	
	public static final String TEXTURE_STR = Reference.MOD_ID.toLowerCase() + ":textures/gui/machineSqueezer.png";
	public static final String PROGRESS_TEXTURE_STR = Reference.MOD_ID.toLowerCase() + ":textures/gui/progress.png";
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/" + "machineSqueezer.png");

	private TileMachineSqueezer te;
	private InventoryPlayer invPlayer;
	private ElementDualScaled progress;

	public GuiMachineSqueezer(InventoryPlayer invPlayer, TileMachineSqueezer te) {
		
		super(new ContainerMachineSqueezer(invPlayer, te), TEXTURE);
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
		addElement(new ElementFluidTank(this, 60, 12, this.te.tankMgr.getTank(this.te.RED_TANK)));
		addElement(new ElementFluidTank(this, 78, 12, this.te.tankMgr.getTank(this.te.YELLOW_TANK)));
		addElement(new ElementFluidTank(this, 96, 12, this.te.tankMgr.getTank(this.te.BLUE_TANK)));
		addElement(new ElementFluidTank(this, 114, 12, this.te.tankMgr.getTank(this.te.WHITE_TANK)));
		addElement(new ElementFluidTank(this, 150, 12, this.te.tankMgr.getTank(this.te.PURE_TANK)));
		
		this.progress = ((ElementDualScaled)addElement(new ElementDualScaled(this, 28, 54).setMode(1).setBackground(false).setSize(24, 16).setTexture(PROGRESS_TEXTURE_STR, 64, 64)));
		addElement(progress);
	}
	
	@Override
	protected void updateElements() {

		this.progress.setQuantity(this.te.getScaledProgress(24));
	}
	
}
