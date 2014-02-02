package com.ipsis.mackit.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraftforge.common.ForgeDirection;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.network.PacketTypeHandler;

import cpw.mods.fml.common.network.Player;

/**
 * PacketHandler implementation from Pahimars EE3.
 * https://github.com/pahimar/Equivalent-Exchange-3/blob/master/src/main/java/com/pahimar/ee3/network/packet/PacketTileUpdate.java
 * @author pahimar
 * 
 * Modified to the packets that I need to send
 *
 */

public class PacketTileUpdate extends PacketMK {
	
	public int x;
	public int y;
	public int z;
	public byte orientation;
	public byte active;
	public String customName;

	public PacketTileUpdate() {
		
		super(PacketTypeHandler.TILE, true);
	}
	
	public PacketTileUpdate(int x, int y, int z, ForgeDirection orientation, byte active, String customName) {
		
		super(PacketTypeHandler.TILE, true);
		this.x = x;
		this.y = y;
		this.z = z;
		this.orientation = (byte)orientation.ordinal();
		this.active = active;
		this.customName = customName;		
	}
	
	@Override
	public void writeData(DataOutputStream dos) throws IOException {

		dos.writeInt(x);
		dos.writeInt(y);
		dos.writeInt(z);
		dos.writeByte(orientation);
		dos.writeByte(active);
		dos.writeUTF(customName);		
	}
	
	@Override
	public void readData(DataInputStream data) throws IOException {
		x = data.readInt();
        y = data.readInt();
	    z = data.readInt();
	    orientation = data.readByte();
	    active = data.readByte();
	    customName = data.readUTF();
	}
	
	@Override
	public void execute(INetworkManager network, Player player) {

		MacKit.proxy.handleTileEntityPacket(player, x, y, z, ForgeDirection.getOrientation(orientation), active, customName);
	}
}
