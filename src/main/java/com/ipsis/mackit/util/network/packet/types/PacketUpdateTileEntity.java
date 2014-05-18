package com.ipsis.mackit.util.network.packet.types;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.util.network.packet.AbstractPacket;

public class PacketUpdateTileEntity extends AbstractPacket {

	private int x, y, z;
	private int data;
	
	public PacketUpdateTileEntity() {
		
	}
	
	public PacketUpdateTileEntity(int x, int y, int z, int data) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.data = data;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {

		PacketBuffer p = new PacketBuffer(buffer);
		p.writeInt(x);
		p.writeInt(y);
		p.writeInt(z);
		p.writeInt(data);	
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {

		PacketBuffer p = new PacketBuffer(buffer);
		x = p.readInt();
		y = p.readInt();
		z = p.readInt();
		data = p.readInt();	
	}
	
	@Override
	public void handleClientSide(EntityPlayer player) {

		LogHelper.error("handleClientSide");
	}
	
	@Override
	public void handleServerSide(EntityPlayer player) {
		
		/* this packet only ever goes from server->client */		
	}
}
