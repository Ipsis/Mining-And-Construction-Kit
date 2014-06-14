package com.ipsis.mackit.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.ipsis.mackit.gui.SlotTorchPouch;

/**
 * Based off Pahimar's ContainerAlchemicalBag.java
 *
 */
public class ContainerTorchPouch extends Container {

    private final EntityPlayer entityPlayer;
    private final InventoryTorchPouch inventoryTorchPouch;
	
	public ContainerTorchPouch(EntityPlayer entityPlayer, InventoryTorchPouch inventoryTorchPouch) {
		
		this.entityPlayer = entityPlayer;
		this.inventoryTorchPouch = inventoryTorchPouch;
		
	
		this.addSlotToContainer(new SlotTorchPouch(this, this.inventoryTorchPouch, entityPlayer, 0, 68, 29));
		this.addSlotToContainer(new SlotTorchPouch(this, this.inventoryTorchPouch, entityPlayer, 1, 86, 29));
		this.addSlotToContainer(new SlotTorchPouch(this, this.inventoryTorchPouch, entityPlayer, 2, 68, 47));
		this.addSlotToContainer(new SlotTorchPouch(this, this.inventoryTorchPouch, entityPlayer, 3, 86, 47));		
		
		/* Player inventory */
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++)
				this.addSlotToContainer(new Slot(entityPlayer.inventory, x + y * 9 + 9, 6 + x * 18, 95 + y * 18));
		}
		
		/* Player hotbar */
		for (int x = 0; x < 9; x++)
			this.addSlotToContainer(new Slot(entityPlayer.inventory, x, 6 + x * 18, 153));

	}
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) {

		/* You are holding it, you cannot be that far away */
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {

		return null;
	}
	
    public void saveInventory(EntityPlayer entityPlayer) {

    	inventoryTorchPouch.onGuiSaved(entityPlayer);
    }
    
    @Override
    public void onContainerClosed(EntityPlayer entityPlayer)
    {
        super.onContainerClosed(entityPlayer);

        if (!entityPlayer.worldObj.isRemote)
            saveInventory(entityPlayer);
    }

	
}
