package com.ipsis.mackit.client.gui.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fluids.FluidStack;

import com.ipsis.mackit.fluid.ModFluids;
import com.ipsis.mackit.gui.ElementEnergyStored;
import com.ipsis.mackit.gui.ElementFluidTank;
import com.ipsis.mackit.gui.GuiBase;
import com.ipsis.mackit.inventory.ContainerMachineSqueezer;
import com.ipsis.mackit.lib.Textures;
import com.ipsis.mackit.tileentity.TileMachineSqueezer;

public class GuiMachineSqueezer extends GuiBase {
	
	private TileMachineSqueezer te;
	private InventoryPlayer invPlayer;
	
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
		addElement(new ElementFluidTank(this, 150, 12, this.te.tank));		
	}
	
	@Override
	protected void updateElements() {

		te.storage.receiveEnergy(100, false);
		te.tank.fill(new FluidStack(ModFluids.blueDye, 100), true);
	}
}
