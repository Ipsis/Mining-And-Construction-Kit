package com.ipsis.mackit.block;

import java.util.List;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import com.ipsis.cofhlib.inventory.IInventoryManager;
import com.ipsis.cofhlib.inventory.InventoryManager;
import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.reference.Gui;
import com.ipsis.mackit.util.network.packet.AbstractPacket;
import com.ipsis.mackit.util.network.packet.IPacketGuiHandler;
import com.ipsis.mackit.util.network.packet.types.PacketGui;

public class TilePortaChant extends TileEntityInventoryMK implements IPacketGuiHandler {

	public static final int MAX_ENCHANT = 30;
	public static final int MIN_ENCHANT = 1;
	private byte enchantLevel;
	private IInventoryManager invMgr;
	private Random rand = new Random();	
	
	public static final int INPUT_SLOT = 0;
	public static final int OUTPUT_SLOT = 1;
	
	
	public TilePortaChant() {
		
		/* 0 input and 1 output */
		inventory = new ItemStack[7];
		enchantLevel = MIN_ENCHANT;
		invMgr = InventoryManager.create(this, ForgeDirection.UNKNOWN);
	}
	
	public byte getEnchantLevel() {
		
		return enchantLevel;
	}
	
	public void setEnchantLevel(byte l) {
		
		if (l < MIN_ENCHANT)
			l = MIN_ENCHANT;
		if (l > MAX_ENCHANT)
			l = MAX_ENCHANT;
		enchantLevel = l;
	}
	
	public void incEnchantLevel() {

		enchantLevel++;
		if (enchantLevel > MAX_ENCHANT)
			enchantLevel = MAX_ENCHANT;
		
		if (worldObj.isRemote) {
			AbstractPacket p = new PacketGui(
					xCoord, yCoord, zCoord,
					(byte)Gui.PORTA_CHANT,
					(byte)Gui.TYPE_BUTTON, 
					(byte)Gui.PORTA_CHANT_UP, 
					0, 0);
			MacKit.pp.sendToServer(p);
		}
	}

	public void decEnchantLevel() {

		enchantLevel--;
		if (enchantLevel < MIN_ENCHANT)
			enchantLevel = MIN_ENCHANT;
		
		if (worldObj.isRemote) {
			AbstractPacket p = new PacketGui(
					xCoord, yCoord, zCoord,
					(byte)Gui.PORTA_CHANT,
					(byte)Gui.TYPE_BUTTON, 
					(byte)Gui.PORTA_CHANT_DN, 
					0, 0);
			MacKit.pp.sendToServer(p);
		}
	}

	/*******************
	 * IPacketGuiHandler
	 *******************/
	
	@Override
	public void handlePacketGui(PacketGui packet) {

		if (packet.guiId != Gui.PORTA_CHANT)
			return;
		
		if (packet.ctrlType == Gui.TYPE_BUTTON) {
			if (packet.ctrlId == Gui.PORTA_CHANT_DN) {
				decEnchantLevel();				
			} else if (packet.ctrlId == Gui.PORTA_CHANT_UP) {
				incEnchantLevel();
			}
		}
	}
	
	/****************
	 * Processing
	 ****************/
	
	private EntityPlayer getEnchantingPlayer() {

		return this.worldObj.getClosestPlayer((double)((float)this.xCoord + 0.5F), (double)((float)this.yCoord + 0.5F), (double)((float)this.zCoord + 0.5F), 3.0D);
	}
	
	@Override
	public void updateEntity() {
		
		if (worldObj.isRemote)
			return;
		
		ItemStack out = invMgr.getSlotContents(OUTPUT_SLOT);
		ItemStack in = invMgr.getSlotContents(INPUT_SLOT);
		
		/* Enchanted items do not stack */
		if (in == null || out != null)
			return;
		
		EntityPlayer player = getEnchantingPlayer();
		
		if ( player.experienceLevel < enchantLevel && ! player.capabilities.isCreativeMode)
			return;
		
		/* We only want one */
		ItemStack src = invMgr.removeItem(1, invMgr.getSlotContents(INPUT_SLOT));
		if (src == null)
			return;
		
		ItemStack enchanted = EnchantmentHelper.addRandomEnchantment(this.rand, src, enchantLevel);
		setInventorySlotContents(OUTPUT_SLOT, enchanted);
	}
}
