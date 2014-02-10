package com.ipsis.mackit.client.gui.inventory;

import net.minecraft.entity.player.InventoryPlayer;

import com.ipsis.mackit.gui.ElementEnergyStored;
import com.ipsis.mackit.gui.GuiBase;
import com.ipsis.mackit.inventory.ContainerMachineBBBuilder;
import com.ipsis.mackit.lib.Textures;
import com.ipsis.mackit.tileentity.TileMachineBBBuilder;

public class GuiMachineBBBuilder extends GuiBase {
	
	private TileMachineBBBuilder tileMachineBBBuilder;
	private InventoryPlayer invPlayer;
	
	public GuiMachineBBBuilder(InventoryPlayer inventoryPlayer, TileMachineBBBuilder tileMachineBBBuilder) {
		
		super(new ContainerMachineBBBuilder(inventoryPlayer, tileMachineBBBuilder), Textures.GUI_MACHINE_BBBUILDER);
		this.tileMachineBBBuilder = tileMachineBBBuilder;
		this.invPlayer = inventoryPlayer;
		
		xSize = 174;
		ySize = 177;
		

	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		addElement(new ElementEnergyStored(this, 7, 22, this.tileMachineBBBuilder.storage));
		
	}
	
	@Override
	protected void updateElements() {

		tileMachineBBBuilder.storage.receiveEnergy(100, false);
	}

}
