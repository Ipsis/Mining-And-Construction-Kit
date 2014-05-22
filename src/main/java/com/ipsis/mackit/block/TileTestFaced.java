package com.ipsis.mackit.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileTestFaced extends TileEntity implements IFacing {

	private ForgeDirection facing;
	
	public TileTestFaced() {
		
		facing = ForgeDirection.EAST;
	}

	public void setFacing(ForgeDirection d) {

		facing = d;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);		
	}
	
	public ForgeDirection getFacing() {
		
		return facing;
	}
	
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
	
	
	@Override
	public Packet getDescriptionPacket() {
		
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setByte("Facing", (byte)facing.ordinal());
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {

		facing = ForgeDirection.getOrientation(pkt.func_148857_g().getByte("Facing"));
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
}
