package com.ipsis.mackit.block;

import java.util.Arrays;

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
import net.minecraftforge.common.util.ForgeDirection;

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
	private static final int PRODUCE_LIFETIME = 100;
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
	
		currTicks = 0;
		selectedDye = null;
		lifetime = 0;
	}
	
	public ItemStack getDye() {
		
		return selectedDye;
	}
	
	public void setBiome() {
		
		BiomeGenBase g = worldObj.getBiomeGenForCoords(xCoord, zCoord);		
		selectedDye = biomeColorToDye(g);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public void tryProduce() {
		
		LogHelper.error("tryProduce: " + selectedDye + " " + lifetime + "/" + PRODUCE_LIFETIME);
		if (selectedDye != null && lifetime >= PRODUCE_LIFETIME) {
			/* throw the dye item on the ground */
			selectedDye.stackSize = 0;
			lifetime = 0;
		}
	}
	
	private ItemStack biomeColorToDye(BiomeGenBase biome) {
		
		ItemStack dye = null;
		
		if (biome != null) {
			int color = DYE_COLORS_SORTED[0];
			
			for (int c : DYE_COLORS_SORTED) {
	
				if (c < biome.color)
					break;
				else
					color = c;
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

		currTicks++;
		if (currTicks % UPDATE_FREQ != 0)
			return;
		
		if (worldObj.isRemote)
			return;
		
		/* if time to update life */
		lifetime++;
		if (selectedDye != null && lifetime >= PRODUCE_LIFETIME) {
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
		if (selectedDye != null) {
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			selectedDye.writeToNBT(nbttagcompound1);
			nbttaglist.appendTag(nbttagcompound1);
		}
		nbttagcompound.setTag("SelectedDye", nbttaglist);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		lifetime = nbttagcompound.getInteger("Lifetime");
		NBTTagList nbttaglist = nbttagcompound.getTagList("SelectedDye", Constants.NBT.TAG_COMPOUND);
		if (nbttaglist.tagCount() == 1) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(0);
			selectedDye = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
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
