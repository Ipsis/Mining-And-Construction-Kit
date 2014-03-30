package com.ipsis.mackit.client.gui.inventory;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;

import com.ipsis.mackit.gui.ElementDualScaled;
import com.ipsis.mackit.gui.ElementEnergyStored;
import com.ipsis.mackit.gui.ElementFluidTank;
import com.ipsis.mackit.gui.ElementIcon;
import com.ipsis.mackit.gui.GuiBase;
import com.ipsis.mackit.inventory.ContainerMachineStamper;
import com.ipsis.mackit.lib.GuiIds;
import com.ipsis.mackit.lib.Reference;
import com.ipsis.mackit.lib.Textures;
import com.ipsis.mackit.manager.MKRegistry;
import com.ipsis.mackit.network.PacketHandler;
import com.ipsis.mackit.network.packet.PacketGui;
import com.ipsis.mackit.network.packet.PacketMK;
import com.ipsis.mackit.tileentity.TileMachineStamper;

public class GuiMachineStamper extends GuiBase {

	private TileMachineStamper te;
	private InventoryPlayer invPlayer;
	private ElementDualScaled progress;
	private ElementIcon selected;

	
	public GuiMachineStamper(InventoryPlayer inventoryPlayer, TileMachineStamper tileMachineStamper) {
		
		super(new ContainerMachineStamper(inventoryPlayer, tileMachineStamper), Textures.GUI_MACHINE_STAMPER);
		this.te = tileMachineStamper;
		this.invPlayer = inventoryPlayer;
		
		xSize = 174;
		ySize = 177;
	}
	
	public static final int GUI_BUTTON_INCR = 0;
	public static final int GUI_BUTTON_DECR = 1;
	
	private GuiButton incrButton;
	private GuiButton decrButton;
	
	@Override
	public void initGui() {
		super.initGui();
		
		addElement(new ElementEnergyStored(this, 7, 22, this.te.storage));
		addElement(new ElementFluidTank(this, 150, 12, this.te.pureTank));
		
		/* setTexture - png and the texture sheet size */
		/* setSize - how big is the area to draw */
		this.progress = ((ElementDualScaled)addElement(new ElementDualScaled(this, 71, 35).setMode(1).setBackground(false).setSize(24, 16).setTexture(Reference.MOD_ID.toLowerCase() + ":" + Textures.GUI_SHEET_LOCATION + "progress.png", 64, 64)));
		addElement(progress);
		
		this.selected = ((ElementIcon)addElement(new ElementIcon(this, 75, 68).setIcon(Item.bone.getIconFromDamage(0))));
		addElement(selected);
		
		
			
		buttonList.clear();
		incrButton = new GuiButton(GUI_BUTTON_INCR, guiLeft + 98, guiTop + 67, 18, 18, "+");
		buttonList.add(incrButton);
		decrButton = new GuiButton(GUI_BUTTON_DECR, guiLeft + 55, guiTop + 67, 18, 18, "-");
		buttonList.add(decrButton);	
	}
	
	@Override
	protected void updateElements() {

		this.progress.setQuantity(this.te.getScaledProgress(24));
		
		selected.setIcon(MKRegistry.getStamperManager().getIcon(this.te.getSelectedIndex()));
	}
	
	@Override
	public void handleElementButtonClick(String buttonName, int mouseButton) {
		
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {

		PacketMK packet = new PacketGui(GuiIds.STAMPER, PacketGui.CTRL_BUTTON, button.id, 0);
		PacketHandler.sendPacketToServer(packet);
	}
}
