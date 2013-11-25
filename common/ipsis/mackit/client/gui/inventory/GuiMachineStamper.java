package ipsis.mackit.client.gui.inventory;

import ipsis.mackit.inventory.ContainerMachineStamper;
import ipsis.mackit.lib.Reference;
import ipsis.mackit.tileentity.TileMachineStamper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import cofh.gui.GuiBase;
import cofh.gui.element.ElementDualScaled;
import cofh.gui.element.ElementFluidTank;

public class GuiMachineStamper extends GuiBase {

	private TileMachineStamper machine;
	private static final ResourceLocation guiTexture = new ResourceLocation(Reference.MOD_ID, "textures/gui/machineStamper.png");
	private static final int TEXTURE_X_SIZE = 174;
	private static final int TEXTURE_Y_SIZE = 177;
	
	private ElementDualScaled progress;
	private ElementDualScaled energy;
	
	
	public GuiMachineStamper(InventoryPlayer invPlayer, TileMachineStamper machine) {
		super(new ContainerMachineStamper(invPlayer, machine), guiTexture);
		
		this.machine = machine;
		
		xSize = TEXTURE_X_SIZE;
		ySize = TEXTURE_Y_SIZE;	
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		addElement(new ElementFluidTank(this, 150, 12, machine.getTank()).setGauge(0));
		
		progress = new ElementDualScaled(this, 103, 34);
		progress.setMode(1);
		progress.setBackground(true);
		progress.setTexture(Reference.MOD_ID + ":textures/gui/guiProgress.png", 64, 64);
		progress.setSize(18, 17);
		addElement(progress);
	}
}
