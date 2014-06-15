package com.ipsis.mackit.item;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.container.InventoryTorchPouch;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.reference.Gui;
import com.ipsis.mackit.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
	
	private void openGui(ItemStack itemStack, EntityPlayer entityPlayer) {
		
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
		
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		
		/* handle right click in the air */
		
		if (!world.isRemote && entityPlayer.isSneaking()) {
			
			openGui(itemStack, entityPlayer);
			return itemStack;
		}
			
		return super.onItemRightClick(itemStack, world, entityPlayer);
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		
		if (!world.isRemote) {

			/* No sneak check, try and work the same way as normal torches */

			if (itemStack.hasTagCompound()) {

				InventoryTorchPouch inv = new InventoryTorchPouch(itemStack);
				for (int slot = 0; slot < inv.getSizeInventory(); slot++) {

					ItemStack s = inv.getStackInSlot(slot);
					if (s != null && s.stackSize != 0) {
						/* we assume if it is in here it is a torch */
						Item item = s.getItem();
						if (item.onItemUse(s, entityPlayer, world, x, y, z, side, hitX, hitY, hitZ) == true) {

							inv.onGuiSaved(entityPlayer);
							break;
						}
					}
				}
			}			
		}

		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack,	EntityPlayer entityPlayer, List info, boolean useExtraInformation) {
		
		int count = 0;
		
		if (itemStack.hasTagCompound()) {

			/* TODO This doesn't seem very efficient! 
			 * Probably want to store the count in the NBT!
			 */
			InventoryTorchPouch inv = new InventoryTorchPouch(itemStack);
			for (int slot = 0; slot < inv.getSizeInventory(); slot++) {

				ItemStack s = inv.getStackInSlot(slot);
				if (s != null)
					count += s.stackSize;
			}
		}	
		
		info.add("Torches " + count + "/256");
	}
}
