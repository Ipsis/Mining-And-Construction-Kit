package com.ipsis.mackit.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraftforge.common.ForgeDirection;

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
	
	/*
	 * Gui Updating
	 * 
	 * current energy stored
	 * recipe energy used
	 * recipe ebergy total
	 */
	private static final int GUI_UPD_ENERGY_STORED = 0;
	private static final int GUI_UPD_ENERGY_CONSUMED = 1;
	
	@Override
	public void addCraftingToCrafters(ICrafting iCrafting) {
		super.addCraftingToCrafters(iCrafting);
	
		iCrafting.sendProgressBarUpdate(this, GUI_UPD_ENERGY_STORED, tileMachineBBBuilder.storage.getEnergyStored());
		iCrafting.sendProgressBarUpdate(this, GUI_UPD_ENERGY_CONSUMED, tileMachineBBBuilder.getEnergyConsumed());
	}
	
	private int lastEnergyStored;
	private int lastEnergyConsumed;
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
        for (Object crafter : this.crafters)
        {
            ICrafting icrafting = (ICrafting) crafter;
            
            if (lastEnergyStored != tileMachineBBBuilder.storage.getEnergyStored())
            	icrafting.sendProgressBarUpdate(this, GUI_UPD_ENERGY_STORED, tileMachineBBBuilder.storage.getEnergyStored());
            
            if (lastEnergyConsumed != tileMachineBBBuilder.getEnergyConsumed())
            	icrafting.sendProgressBarUpdate(this, GUI_UPD_ENERGY_CONSUMED, tileMachineBBBuilder.getEnergyConsumed());
        }
        
        lastEnergyStored = tileMachineBBBuilder.storage.getEnergyStored();
        lastEnergyConsumed = tileMachineBBBuilder.getEnergyConsumed();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		if (id == GUI_UPD_ENERGY_STORED)
			tileMachineBBBuilder.storage.setEnergyStored(data);
		else if (id == GUI_UPD_ENERGY_CONSUMED)
			tileMachineBBBuilder.setEnergyConsumed(data);
	}

}
