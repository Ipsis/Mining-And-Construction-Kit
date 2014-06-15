package com.ipsis.mackit.block;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.helper.OreDictHelper;
import com.ipsis.mackit.network.message.IMessageGuiHandler;
import com.ipsis.mackit.network.message.MessageGui;
import com.ipsis.mackit.reference.Gui;

public class TileVegas extends TileInventory implements IMessageGuiHandler {
	
	public static final int INPUT_SLOT = 0;
	public static final int TOKEN_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;
	
	/* Yes there is only one for ALL the tile entities and only on the server */
	/* TODO save this across sessions register for WorldEvent.Save/Load and then read/write local file??? */
	private static HashMap<String,Integer> players = new HashMap<String,Integer>();
	
	private Random rand = new Random();	
	
	public TileVegas() {
		
		inventory = new ItemStack[3];
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {

		if (slot == INPUT_SLOT)
			return OreDictHelper.isOre(stack);
		else if (slot == TOKEN_SLOT)
			return stack.getItem() == Items.gold_nugget;
		else
			return false;
		
	}
	
	/* TODO the cost should be player specific */
	private static int currGambleCost = 1;
	private static float successPercentage = 20;
	
	private void tryGamble() {
		
		ItemStack ore = getStackInSlot(INPUT_SLOT);
		ItemStack token = getStackInSlot(TOKEN_SLOT);
		ItemStack output = getStackInSlot(OUTPUT_SLOT);
		
		if (ore == null || ore.stackSize <= 0)
			return;
		
		if (output != null) {
			if (!output.isItemEqual(ore) || output.stackSize == output.getMaxStackSize())
				return;
		}
		
		EntityPlayer entityPlayer = this.worldObj.getClosestPlayer((double)((float)this.xCoord + 0.5F), (double)((float)this.yCoord + 0.5F), (double)((float)this.zCoord + 0.5F), 3.0D);
		if (entityPlayer == null)
			return;
		
		if (token.stackSize < currGambleCost)
			return;
		
		/* Take away the inputs */
		decrStackSize(INPUT_SLOT, 1);
		decrStackSize(TOKEN_SLOT, currGambleCost);
		
		Integer count = players.get(entityPlayer.getDisplayName());
		if (count != null)
			count++;
		else
			count = 1;
		players.put(entityPlayer.getDisplayName(), count);
		
		
		if (rand.nextInt(100) < successPercentage) {
			ItemStack out;
			if (output != null) {
				
				out = output.copy();
				out.stackSize += 2;	
			} else {
				
				out = ore.copy();
				out.stackSize = 2;
			}
			
			setInventorySlotContents(OUTPUT_SLOT, out);
		}	
	}
	
	/*******************
	 * IPacketGuiHandler
	 *******************/	
	@Override
	public void handleMessageGui(MessageGui msg) {
		
		if (msg.guiId != Gui.VEGAS)
			return;

		if (msg.ctrlType == Gui.TYPE_BUTTON)
			tryGamble();
	}
}
