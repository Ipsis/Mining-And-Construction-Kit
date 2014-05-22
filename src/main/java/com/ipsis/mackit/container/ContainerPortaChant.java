package com.ipsis.mackit.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ipsis.cofhlib.gui.slot.SlotOutput;
import com.ipsis.mackit.block.TilePortaChant;
import com.ipsis.mackit.gui.SlotEnchantable;
import com.ipsis.mackit.util.PlayerHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerPortaChant extends Container {

	TilePortaChant te;
	
	public ContainerPortaChant(InventoryPlayer invPlayer, TilePortaChant te) {

		this.te = te;
		
		this.addSlotToContainer(new SlotEnchantable(te, te.INPUT_SLOT, 24, 35));
		this.addSlotToContainer(new SlotOutput(te, te.OUTPUT_SLOT, 114, 35));
		
		/* Player inventory */
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++)
				this.addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 6 + x * 18, 95 + y * 18));
		}
		
		/* Player hotbar */
		for (int x = 0; x < 9; x++)
			this.addSlotToContainer(new Slot(invPlayer, x, 6 + x * 18, 153));
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {

		return PlayerHelper.canInteractWithPlayer(entityPlayer, te.xCoord, te.yCoord, te.zCoord); 
	}
	
	/**********************
	 *  GUI Crafting Update
	 **********************/
	@Override
	public void addCraftingToCrafters(ICrafting player) {

		/* initial opening of the gui */
		super.addCraftingToCrafters(player);
		player.sendProgressBarUpdate(this, 0, te.getEnchantLevel());
	}
	
	private byte lastEnchantLevel;
	
	@Override
	public void detectAndSendChanges() {

		super.detectAndSendChanges();
		
		for (Object player : crafters) {
		
			if (lastEnchantLevel != te.getEnchantLevel())
				((ICrafting)player).sendProgressBarUpdate(this, 0, te.getEnchantLevel());
		}
		
		lastEnchantLevel = te.getEnchantLevel();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {

		if (id == 0)
			te.setEnchantLevel((byte)data);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {

		return null;
	}
	
}
