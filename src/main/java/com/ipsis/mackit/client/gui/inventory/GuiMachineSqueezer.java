package com.ipsis.mackit.client.gui.inventory;

import net.minecraft.entity.player.InventoryPlayer;

import com.ipsis.mackit.gui.ElementDualScaled;
import com.ipsis.mackit.gui.ElementEnergyStored;
import com.ipsis.mackit.gui.ElementFluidTank;
import com.ipsis.mackit.gui.GuiBase;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.inventory.ContainerMachineSqueezer;
import com.ipsis.mackit.lib.Reference;
import com.ipsis.mackit.lib.Textures;
import com.ipsis.mackit.tileentity.TileMachineSqueezer;

public class GuiMachineSqueezer extends GuiBase {
	
	private TileMachineSqueezer te;
	private InventoryPlayer invPlayer;
	private ElementDualScaled progress;
	
	public GuiMachineSqueezer(InventoryPlayer inventoryPlayer, TileMachineSqueezer tileMachineSqueezer) {
		
		super(new ContainerMachineSqueezer(inventoryPlayer, tileMachineSqueezer), Textures.GUI_MACHINE_SQUEEZER);
		this.te = tileMachineSqueezer;
		this.invPlayer = inventoryPlayer;
		
		xSize = 174;
		ySize = 177;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		addElement(new ElementEnergyStored(this, 7, 22, this.te.storage));
		
		addElement(new ElementFluidTank(this, 60, 12, this.te.redTank));
		addElement(new ElementFluidTank(this, 78, 12, this.te.yellowTank));
		addElement(new ElementFluidTank(this, 96, 12, this.te.blueTank));
		addElement(new ElementFluidTank(this, 114, 12, this.te.whiteTank));
		addElement(new ElementFluidTank(this, 150, 12, this.te.pureTank));
		
		/* setTexture - png and the texture sheet size */
		/* setSize - how big is the area to draw */
		this.progress = ((ElementDualScaled)addElement(new ElementDualScaled(this, 28, 54).setMode(1).setBackground(false).setSize(24, 16).setTexture(Reference.MOD_ID.toLowerCase() + ":" + Textures.GUI_SHEET_LOCATION + "progress.png", 64, 64)));
		addElement(progress);
	}
	
	@Override
	protected void updateElements() {

		this.progress.setQuantity(this.te.getScaledProgress(24));
	}
}
