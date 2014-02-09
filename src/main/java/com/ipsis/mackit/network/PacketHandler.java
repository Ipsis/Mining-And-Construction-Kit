package com.ipsis.mackit.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.lib.Reference;
import com.ipsis.mackit.network.packet.PacketMK;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;


/**
 * PacketHandler implementation from Pahimar's EE3.
 * https://github.com/pahimar/Equivalent-Exchange-3/blob/master/src/main/java/com/pahimar/ee3/network/PacketHandler.java
 * 
 * Modified to the packets that I need to send
 *
 */

public class PacketHandler implements IPacketHandler {
	
	public static void sendPacketToServer(PacketMK packet) {
				
		PacketDispatcher.sendPacketToServer(PacketTypeHandler.populatePacket(packet));
	}
	
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {

		PacketMK packetMK = PacketTypeHandler.buildPacket(packet.data);
		packetMK.execute(manager, player);
	}

}
