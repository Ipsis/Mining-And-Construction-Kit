package com.ipsis.mackit.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.lib.Reference;
import com.ipsis.mackit.network.packet.PacketGui;
import com.ipsis.mackit.network.packet.PacketMK;
import com.ipsis.mackit.network.packet.PacketTileUpdate;

/**
 * PacketHandler implementation from Pahimars EE3.
 * https://github.com/pahimar/Equivalent-Exchange-3/blob/master/src/main/java/com/pahimar/ee3/network/PacketTypeHandler.java
 * @author pahimar
 * 
 * Modified to the packets that I need to send
 *
 */

public enum PacketTypeHandler {

	TILE(PacketTileUpdate.class),
	GUI(PacketGui.class);
	
	private Class<? extends PacketMK> clazz;
	
	PacketTypeHandler(Class<? extends PacketMK> clazz) {
		
		this.clazz = clazz;
	}
	
    public static PacketMK buildPacket(byte[] data) {

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        int selector = bis.read();
        DataInputStream dis = new DataInputStream(bis);

        PacketMK packet = null;
        
        try {
            packet = values()[selector].clazz.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }

        packet.readPopulate(dis);
        return packet;
    }

    public static PacketMK buildPacket(PacketTypeHandler type) {

        PacketMK packet = null;

        try {
            packet = values()[type.ordinal()].clazz.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return packet;
    }

    public static Packet populatePacket(PacketMK packetMK) {

        byte[] data = packetMK.populate();

        Packet250CustomPayload packet250 = new Packet250CustomPayload();
        packet250.channel = Reference.CHANNEL_NAME;
        packet250.data = data;
        packet250.length = data.length;
        packet250.isChunkDataPacket = packetMK.isChunkDataPacket;

        return packet250;
    }
}
