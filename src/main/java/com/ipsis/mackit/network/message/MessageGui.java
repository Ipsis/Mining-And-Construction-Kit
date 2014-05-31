package com.ipsis.mackit.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

import com.ipsis.mackit.helper.LogHelper;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageGui implements IMessage, IMessageHandler<MessageGui, IMessage> {

	public int x, y, z;
	public byte guiId;
	public byte ctrlType;
	public byte ctrlId;
	public int data1;
	public int data2;
	
	public MessageGui() {
	}
	
	public MessageGui(int x, int y, int z, byte guiId, byte ctrlType,
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
	public IMessage onMessage(MessageGui message, MessageContext ctx) {

		/**
		 * This should probably go somewhere else.
		 * Only use "message", not "this"!
		 */
		
		EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
		if (entityPlayer == null)
			return null;
		
		TileEntity tileEntity = entityPlayer.worldObj.getTileEntity(message.x, message.y, message.z);
        if (tileEntity instanceof IMessageGuiHandler)
        {
        	((IMessageGuiHandler)tileEntity).handleMessageGui(message);	
        }

        return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {

		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.guiId = buf.readByte();
		this.ctrlType = buf.readByte();
		this.ctrlId = buf.readByte();
		this.data1 = buf.readInt();
		this.data2 = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {

		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeByte(this.guiId);
		buf.writeByte(this.ctrlType);
		buf.writeByte(this.ctrlId);
		buf.writeInt(this.data1);
		buf.writeInt(this.data2);				
	}
	
	@Override
	public String toString() {

		return "MessageGui: (" + this.x + ", " + this.y + ", " + this.z + ") " + 
				this.guiId + " " + this.ctrlType + " " + this.ctrlId + 
				" " + this.data1 + " " + data2;
	}
}
