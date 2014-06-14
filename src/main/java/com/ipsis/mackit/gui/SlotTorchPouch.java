package com.ipsis.mackit.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.ipsis.mackit.container.ContainerTorchPouch;

import cpw.mods.fml.common.Loader;

/**
 * Based off Pahimar's SlotAlchemicalBag.java
 *
 */
public class SlotTorchPouch extends Slot {

	private final EntityPlayer entityPlayer;
	private ContainerTorchPouch containerTorchPouch;

	public SlotTorchPouch(ContainerTorchPouch containerTorchPouch, IInventory inventory, EntityPlayer entityPlayer, int slot, int x, int y) {

		super(inventory, slot, x, y);
		this.entityPlayer = entityPlayer;
		this.containerTorchPouch = containerTorchPouch;
	}

	@Override
	public void onSlotChange(ItemStack itemStack1, ItemStack itemStack2) {

		super.onSlotChange(itemStack1, itemStack2);
		containerTorchPouch.saveInventory(entityPlayer);
	}

	@Override
	public boolean isItemValid(ItemStack itemStack) {

		if (itemStack == null)
			return false;
		
		if (itemStack.getItem() == Item.getItemFromBlock(Blocks.torch))
			return true;
		
		return false;
	}
}


