package com.ipsis.mackit.block;

import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.ipsis.mackit.network.PacketHandler;
import com.ipsis.mackit.network.message.IMessageGuiHandler;
import com.ipsis.mackit.network.message.MessageGui;
import com.ipsis.mackit.reference.Gui;

public class TilePortaChant extends TileInventory implements IMessageGuiHandler, IFacing, ISidedInventory {

	public static final int MAX_ENCHANT = 30;
	public static final int MIN_ENCHANT = 1;
	private byte enchantLevel;
	private Random rand = new Random();	
	private ForgeDirection facing;
	
	public static final int INPUT_SLOT = 0;
	public static final int OUTPUT_SLOT = 1;
	
	
	public TilePortaChant() {
		
		/* 0 input and 1 output */
		inventory = new ItemStack[2];
		enchantLevel = MIN_ENCHANT;
		facing = ForgeDirection.EAST;
	}
	
	@Override
	public void setFacing(ForgeDirection facing) {
		
		this.facing = facing;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	@Override
	public ForgeDirection getFacing() {
		
		return facing;
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
			PacketHandler.INSTANCE.sendToServer(new MessageGui(
					xCoord, yCoord, zCoord,
					(byte)Gui.PORTA_CHANT,
					(byte)Gui.TYPE_BUTTON, 
					(byte)Gui.PORTA_CHANT_UP, 
					0, 0));
		}
	}

	public void decEnchantLevel() {

		enchantLevel--;
		if (enchantLevel < MIN_ENCHANT)
			enchantLevel = MIN_ENCHANT;

		if (worldObj.isRemote) {
			PacketHandler.INSTANCE.sendToServer(new MessageGui(
					xCoord, yCoord, zCoord,
					(byte)Gui.PORTA_CHANT,
					(byte)Gui.TYPE_BUTTON, 
					(byte)Gui.PORTA_CHANT_DN, 
					0, 0));
		}
	}

	/*******************
	 * IPacketGuiHandler
	 *******************/
	
	@Override
	public void handleMessageGui(MessageGui msg) {
		
		if (msg.guiId != Gui.PORTA_CHANT)
			return;

		if (msg.ctrlType == Gui.TYPE_BUTTON) {
			if (msg.ctrlId == Gui.PORTA_CHANT_DN) {
				decEnchantLevel();				
			} else if (msg.ctrlId == Gui.PORTA_CHANT_UP) {
				incEnchantLevel();
			}
		}
	}
	
	/*****
	 * NBT
	 *****/
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {

		super.writeToNBT(nbttagcompound);
		nbttagcompound.setByte("Facing", (byte)facing.ordinal());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		facing = ForgeDirection.getOrientation((int)nbttagcompound.getByte("Facing"));
	}
	
	/************
	 * Packets
	 ************/
	@Override
	public Packet getDescriptionPacket() {

		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setByte("Facing", (byte)facing.ordinal());
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
	
		facing = ForgeDirection.getOrientation((int)pkt.func_148857_g().getByte("Facing"));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
		
		ItemStack out = getStackInSlot(OUTPUT_SLOT);
		ItemStack in = getStackInSlot(INPUT_SLOT);
		
		/* Enchanted items do not stack */
		if (in == null || out != null || in.stackSize < 1)
			return;
		
		EntityPlayer player = getEnchantingPlayer();	
		if (player == null || player.experienceLevel < enchantLevel && !player.capabilities.isCreativeMode)
			return;
		
		/* We only want one */
		ItemStack src = decrStackSize(INPUT_SLOT, 1);
		
		ItemStack enchanted = EnchantmentHelper.addRandomEnchantment(this.rand, src, enchantLevel);
		setInventorySlotContents(OUTPUT_SLOT, enchanted);
	}

	private static final int[] accessSlots = new int[]{ INPUT_SLOT, OUTPUT_SLOT };
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {

		return accessSlots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
		
		if (slot != INPUT_SLOT)
			return false;
		
		return isItemValidForSlot(slot, itemStack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack var2, int side) {

		if (slot != OUTPUT_SLOT)
			return false;
		
		/* You can remove anything from the output slot */
		return true;
	}
}
