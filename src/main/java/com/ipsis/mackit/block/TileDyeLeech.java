package com.ipsis.mackit.block;

import java.util.Arrays;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.Constants;

import org.apache.commons.lang3.ArrayUtils;

import com.ipsis.cofhlib.util.ColorHelper;
import com.ipsis.mackit.helper.LogHelper;

/**
 * 
 * When placed the current biome will determine the dye item that it will slowly produce.
 * The biome color will be used to map to the nearest dye color.
 * Ideally it should create its own dye sponges on different colors.
 * This will avoid people using this to generate bonemeal and lapis!
 *
 */
public class TileDyeLeech extends TileEntity {
	
	private static final int UPDATE_FREQ = 20;
	private static final int PRODUCE_LIFETIME = 60;
	private int currTicks;
	private ItemStack selectedDye;
	private int lifetime;
	
	private static final int[] DYE_COLORS_SORTED;
	static {
		DYE_COLORS_SORTED = ColorHelper.DYE_COLORS.clone();
	    Arrays.sort(DYE_COLORS_SORTED);
	    ArrayUtils.reverse(DYE_COLORS_SORTED);
	}
	
	public TileDyeLeech() {
	
		currTicks = UPDATE_FREQ;
		selectedDye = null;
		lifetime = PRODUCE_LIFETIME;
	}
	
	public ItemStack getDye() {
		
		return selectedDye;
	}
	
	public void setBiome() {
		
		BiomeGenBase g = worldObj.getBiomeGenForCoords(xCoord, zCoord);		
		selectedDye = biomeColorToDye(g);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public ItemStack getOutput() {
		
		ItemStack out = null;
		if (selectedDye != null && selectedDye.stackSize > 0) {
			out = selectedDye.copy();
			selectedDye.stackSize = 0;
			lifetime = PRODUCE_LIFETIME;
		}
		
		return out;
	}
	
	private ItemStack biomeColorToDye(BiomeGenBase biome) {
		
		ItemStack dye = null;
		
		if (biome != null) {
			int color = DYE_COLORS_SORTED[0];
			
			int last = -1;
			for (int c : DYE_COLORS_SORTED) {
	
				LogHelper.error("biomeColorToDye: curr " + c + " ? " + biome.color);
				if (c < biome.color) {
					if (last != -1) {
						c = last;
					}
					break;
				} else {
					color = c;
				}
				last = c;
			}
			
	
			for (int x = 0; x < ColorHelper.DYE_COLORS.length; x++) {
				if (ColorHelper.DYE_COLORS[x] == color) {
					dye = new ItemStack(Items.dye, 0, x);
					break;
				}				
			}
		}
		
		if (dye == null)
			dye = new ItemStack(Items.dye, 0, 1);	/* red */
		
		return dye;
	}
	
	@Override
	public void updateEntity() {

		/* TODO this needs fixed */
		
		currTicks--;
		if (currTicks != 0)
			return;
		
		currTicks = UPDATE_FREQ;
		
		if (worldObj.isRemote)
			return;
		
		/* if time to update life */
		lifetime--;
		if (selectedDye != null && lifetime <= 0) {
			lifetime = PRODUCE_LIFETIME;
			selectedDye.stackSize++;
			if (selectedDye.stackSize > selectedDye.getMaxStackSize())
				selectedDye.stackSize = selectedDye.getMaxStackSize();
		}
	}
	
	/*****
	 * NBT
	 *****/
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {

		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Lifetime", lifetime);
		NBTTagList nbttaglist = new NBTTagList();
		if (selectedDye != null)
			nbttagcompound.setTag("storedStack", selectedDye.writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		lifetime = nbttagcompound.getInteger("Lifetime");
		
		if (nbttagcompound.hasKey("storedStack"))
			selectedDye = ItemStack.loadItemStackFromNBT((NBTTagCompound)nbttagcompound.getTag("storedStack"));
	}
	
	/************
	 * Packets
	 ************/
	@Override
	public Packet getDescriptionPacket() {

		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
	
		readFromNBT(pkt.func_148857_g());
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
}
