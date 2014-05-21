package com.ipsis.mackit.util.network.packet.types;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

import com.ipsis.mackit.block.TilePortaChant;
import com.ipsis.mackit.container.ContainerPortaChant;
import com.ipsis.mackit.util.network.packet.AbstractPacket;
import com.ipsis.mackit.util.network.packet.IPacketGuiHandler;

public class PacketGui extends AbstractPacket {
	
	public int x, y, z;
	public byte guiId;
	public byte ctrlType;
	public byte ctrlId;
	public int data1;
	public int data2;
	
	public PacketGui() {
		
	}
	
	public PacketGui(int x, int y, int z, byte guiId, byte ctrlType,
						byte ctrlId, int data1, int data2) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.guiId = guiId;
		this.ctrlType = ctrlType;
		this.ctrlId = ctrlId;
		this.data1 = data1;
		this.data2 = data2;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {

		PacketBuffer p = new PacketBuffer(buffer);
		p.writeInt(this.x);
		p.writeInt(this.y);
		p.writeInt(this.z);
		p.writeByte(this.guiId);
		p.writeByte(this.ctrlType);
		p.writeByte(this.ctrlId);
		p.writeInt(this.data1);
		p.writeInt(this.data2);		
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {

		PacketBuffer p = new PacketBuffer(buffer);
		this.x = p.readInt();
		this.y = p.readInt();
		this.z = p.readInt();
		this.guiId = p.readByte();
		this.ctrlType = p.readByte();
		this.ctrlId = p.readByte();
		this.data1 = p.readInt();
		this.data2 = p.readInt();
	}
	
	@Override
	public void handleClientSide(EntityPlayer player) {

		/* Client->server message only */

	}
	
	@Override
	public void handleServerSide(EntityPlayer player) {

		EntityPlayer entityPlayer = (EntityPlayer)player;
		Container container = entityPlayer.openContainer;
		
		TileEntity te = player.worldObj.getTileEntity(this.x, this.y, this.z);
		if (te != null && te instanceof IPacketGuiHandler)
			((IPacketGuiHandler)te).handlePacketGui(this);		
	}
	
	@Override
	public String toString() {

		return "PacketGui: (" + this.x + ", " + this.y + ", " + this.z + ") " + 
				this.guiId + " " + this.ctrlType + " " + this.ctrlId + 
				" " + this.data1 + " " + data2;
	}
	


}
