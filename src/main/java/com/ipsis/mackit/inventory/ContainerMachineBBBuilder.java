package com.ipsis.mackit.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

import com.ipsis.mackit.client.gui.inventory.SlotOutput;
import com.ipsis.mackit.tileentity.TileMachineBBBuilder;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerMachineBBBuilder extends Container {
	
	private final int PLAYER_INV_ROWS = 3;
	private final int PLAYER_INV_COLS = 9;
	
	private TileMachineBBBuilder tileMachineBBBuilder;
	
	public ContainerMachineBBBuilder(InventoryPlayer inventoryPlayer, TileMachineBBBuilder tileMachineBBBuilder) {
		this.tileMachineBBBuilder = tileMachineBBBuilder;
		
		this.addSlotToContainer(new Slot(tileMachineBBBuilder, 0, 31, 17));
		this.addSlotToContainer(new Slot(tileMachineBBBuilder, 1, 49, 17));
		this.addSlotToContainer(new Slot(tileMachineBBBuilder, 2, 67, 17));
		this.addSlotToContainer(new Slot(tileMachineBBBuilder, 3, 31, 35));
		this.addSlotToContainer(new Slot(tileMachineBBBuilder, 4, 49, 35));
		this.addSlotToContainer(new Slot(tileMachineBBBuilder, 5, 67, 35));
		
		this.addSlotToContainer(new Slot(tileMachineBBBuilder, 6, 49, 56));
		
		this.addSlotToContainer(new SlotOutput(tileMachineBBBuilder, 7, 114, 35));
		
		
		/* Player hotbar */
		for (int x = 0; x < PLAYER_INV_COLS; x++) {
			this.addSlotToContainer(new Slot(inventoryPlayer, x, 6 + x * 18, 153));
		}
		
		/* Player inventory */
		for (int y = 0; y < PLAYER_INV_ROWS; y++)  {
			for (int x = 0; x < PLAYER_INV_COLS; x++) {
				this.addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, 6 + x * 18, 95 + y * 18));
			}
		}	
		
	}
	
	public TileMachineBBBuilder getTileEntity() {
		return this.tileMachineBBBuilder;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {

		return this.tileMachineBBBuilder.isUseableByPlayer(entityplayer);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (int i = 0; i < crafters.size(); i++)
			tileMachineBBBuilder.sendGUINetworkData(this, (ICrafting)crafters.get(i));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		tileMachineBBBuilder.getGUINetworkData(id, data);
	}

}
