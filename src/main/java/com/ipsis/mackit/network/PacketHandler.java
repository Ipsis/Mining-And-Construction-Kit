package com.ipsis.mackit.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.ipsis.mackit.client.gui.inventory.GuiEnchanter;
import com.ipsis.mackit.inventory.ContainerEnchanter;
import com.ipsis.mackit.lib.GuiIds;
import com.ipsis.mackit.lib.Reference;
import com.ipsis.mackit.tileentity.TileEnchanter;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
	
	public static final int INTERFACE_PKT = 0;
	
	public static final int INTERFACE_PKT_BUTTON = 0;
	public static final int INTERFACE_PKT_SLIDER = 1;

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		
		ByteArrayDataInput reader = ByteStreams.newDataInput(packet.data);
		EntityPlayer entityPlayer = (EntityPlayer)player;
		
		byte packetId = reader.readByte();
		
		switch (packetId) {
		case INTERFACE_PKT:
			byte guiId = reader.readByte();
			byte eventId = reader.readByte();
			byte data = reader.readByte();
			
			Container container = entityPlayer.openContainer;
			if (guiId == GuiIds.ENCHANTER) {
				if (container != null && container instanceof ContainerEnchanter) {
					TileEnchanter te = ((ContainerEnchanter)container).getTileEntity();
					
					te.handleInterfacePacket(eventId, data, player);
				}
			}
			break;
		}
	}
	
	public static void sendInterfacePacket(byte guiId, byte eventId, byte data) {
		
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(byteStream);

		try {
			dataStream.writeByte((byte)INTERFACE_PKT);
			dataStream.writeByte((byte)guiId);
			dataStream.writeByte(eventId);
			dataStream.writeByte(data);
			
			PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(Reference.CHANNEL_NAME, byteStream.toByteArray()));
		} catch(IOException ex) {
			System.err.append("Failed to send button click packet");
		}
	}	

}
