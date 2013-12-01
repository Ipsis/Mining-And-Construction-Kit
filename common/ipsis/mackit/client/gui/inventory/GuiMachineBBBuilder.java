package ipsis.mackit.client.gui.inventory;

import ipsis.mackit.inventory.ContainerMachineBBBuilder;
import ipsis.mackit.lib.Reference;
import ipsis.mackit.tileentity.TileMachineBBBuilder;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import cofh.gui.GuiBase;
import cofh.gui.element.ElementDualScaled;

public class GuiMachineBBBuilder extends GuiBase {

	private TileMachineBBBuilder machine;
	private static final ResourceLocation guiTexture = new ResourceLocation(Reference.MOD_ID, "textures/gui/machineBBBuilder.png");
	private static final int TEXTURE_X_SIZE = 174;
	private static final int TEXTURE_Y_SIZE = 177;
	
	private ElementDualScaled progress;
	private ElementDualScaled energy;
	
	public GuiMachineBBBuilder(InventoryPlayer invPlayer, TileMachineBBBuilder machine) {
		super(new ContainerMachineBBBuilder(invPlayer, machine), guiTexture);
		
		this.machine = machine;
		
		xSize = TEXTURE_X_SIZE;
		ySize = TEXTURE_Y_SIZE;	
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		/* progress bar */
		progress = new ElementDualScaled(this, 103, 34);
		progress.setMode(1);
		progress.setBackground(true);
		progress.setTexture(Reference.MOD_ID + ":textures/gui/guiProgress.png", 64, 64);
		progress.setSize(18, 17);
		addElement(progress);
		
		/* Need to change to RF energy to use CofhLIB energy element */
		energy = new ElementDualScaled(this, 8, 23);
		energy.setMode(0);
		energy.setTexture("cofh:textures/gui/elements/Energy.png", 32, 64);
		energy.setSize(12, 40);
		addElement(energy);
	}
	
	@Override
	protected void updateElements() {
		this.progress.setQuantity(machine.getScaledProgress(18));
		
	}
}
