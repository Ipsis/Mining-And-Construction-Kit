package com.ipsis.mackit.item;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.reference.Gui;
import com.ipsis.mackit.reference.Reference;

/**
 * 
 * This is all based off Pahimar's ItemAlchemicalBag.java.
 * 
 * 
 * A pouch that holds torches.
 * Right click with the pouch places the torch.
 * Handles standard torches and Tinkers torches (hopefully)
 * Only stores 4(?) stacks
 * 
 */
public class ItemTorchPouch extends ItemMK {

	public ItemTorchPouch() {
		
		super();
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
	}	
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world,	EntityPlayer entityPlayer) {

		if (!world.isRemote) {
		
			if (entityPlayer.isSneaking()) {
			
				/* Open the gui */
			
				/* Add a UUID */
				if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey(Reference.UUID_MS) && itemStack.getTagCompound().hasKey(Reference.UUID_LS)) {
					/* do nothing */
				} else {
					
					if (!itemStack.hasTagCompound())
						itemStack.stackTagCompound = new NBTTagCompound();
					
					UUID uuid = UUID.randomUUID();
					itemStack.stackTagCompound.setLong(Reference.UUID_MS, uuid.getMostSignificantBits());
					itemStack.stackTagCompound.setLong(Reference.UUID_LS, uuid.getLeastSignificantBits());
				}
				
				entityPlayer.openGui(MacKit.instance, Gui.TORCH_POUCH, entityPlayer.worldObj, 
						(int)entityPlayer.posX, (int)entityPlayer.posY, (int)entityPlayer.posZ);
			} else {
				
				/* Place a torch if possible */
			}
		}
		
		return itemStack;
	}
}
