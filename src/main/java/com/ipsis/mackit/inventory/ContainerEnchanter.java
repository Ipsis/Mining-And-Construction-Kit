package com.ipsis.mackit.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ipsis.mackit.client.gui.inventory.SlotOutput;
import com.ipsis.mackit.tileentity.TileEnchanter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerEnchanter extends Container {
	
	private final int PLAYER_INV_ROWS = 3;
	private final int PLAYER_INV_COLS = 9;
	
	private TileEnchanter tileEnchanter;
	
	public ContainerEnchanter(InventoryPlayer inventoryPlayer, TileEnchanter tileEnchanter) {
		
		this.tileEnchanter = tileEnchanter;
		
		this.addSlotToContainer(new Slot(tileEnchanter, TileEnchanter.INV_SLOT_INPUT, 24, 35));
		
		this.addSlotToContainer(new SlotOutput(tileEnchanter, TileEnchanter.OutputSlot.INV_SLOT_OUTPUT1.slot(), 114, 17));
		this.addSlotToContainer(new SlotOutput(tileEnchanter, TileEnchanter.OutputSlot.INV_SLOT_OUTPUT2.slot(), 132, 17));
		this.addSlotToContainer(new SlotOutput(tileEnchanter, TileEnchanter.OutputSlot.INV_SLOT_OUTPUT3.slot(), 114, 35));
		this.addSlotToContainer(new SlotOutput(tileEnchanter, TileEnchanter.OutputSlot.INV_SLOT_OUTPUT4.slot(), 132, 35));
		this.addSlotToContainer(new SlotOutput(tileEnchanter, TileEnchanter.OutputSlot.INV_SLOT_OUTPUT5.slot(), 114, 53));
		this.addSlotToContainer(new SlotOutput(tileEnchanter, TileEnchanter.OutputSlot.INV_SLOT_OUTPUT6.slot(), 132, 53));
		
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
	
	public TileEnchanter getTileEntity() {
		return tileEnchanter;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {

		return tileEnchanter.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		Slot slot = getSlot(i);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			ItemStack result = stack.copy();
			
			if (i >= 36) {
				if (!mergeItemStack(stack, 0, 36, false)) {
					return null;
				}
			}else if (!mergeItemStack(stack, 36, 36 + tileEnchanter.getSizeInventory(), false)) {
				return null;
			}
			
			if (stack.stackSize == 0) {
				slot.putStack(null);
			}else{
				slot.onSlotChanged();
			}
			
			slot.onPickupFromSlot(player, stack);
			
			return result;
		}
		
		return null;
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting player) {
		super.addCraftingToCrafters(player);
		
		player.sendProgressBarUpdate(this, 0, tileEnchanter.getEnchantLevel());
		player.sendProgressBarUpdate(this, 1, tileEnchanter.getCanEnchant() ? 1 : 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		
		if (id == 0) {
			tileEnchanter.setEnchantLevel((byte)data);
		} else if (id == 1) {
			tileEnchanter.setCanEnchant(data == 1 ? true : false);
		}
	}
	
	private boolean oldCanEnchant;
	private byte oldEnchantLevel;
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (Object player : crafters) {
			
			if (oldEnchantLevel != tileEnchanter.getEnchantLevel()) {
				((ICrafting)player).sendProgressBarUpdate(this, 0, tileEnchanter.getEnchantLevel());
			}
			
			if (oldCanEnchant != tileEnchanter.getCanEnchant()) {
				((ICrafting)player).sendProgressBarUpdate(this, 1, tileEnchanter.getCanEnchant() ? 1 : 0);
			}
		}
		
		oldCanEnchant = tileEnchanter.getCanEnchant();
		oldEnchantLevel = tileEnchanter.getEnchantLevel();
	}
	
}
