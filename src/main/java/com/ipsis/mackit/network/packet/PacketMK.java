package com.ipsis.mackit.network.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;

import com.ipsis.mackit.network.PacketTypeHandler;

import cpw.mods.fml.common.network.Player;

/**
 * PacketHandler implementation from Pahimars EE3.
 * https://github.com/pahimar/Equivalent-Exchange-3/blob/master/src/main/java/com/pahimar/ee3/network/packet/PacketEE.java
 * @author pahimar
 * 
 * Modified to the packets that I need to send
 *
 */

public class PacketMK {

    public PacketTypeHandler packetType;
    public boolean isChunkDataPacket;

    public PacketMK(PacketTypeHandler packetType, boolean isChunkDataPacket) {

        this.packetType = packetType;
        this.isChunkDataPacket = isChunkDataPacket;
    }

    public byte[] populate() {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try {
            dos.writeByte(packetType.ordinal());
            this.writeData(dos);
        }
        catch (IOException e) {
            e.printStackTrace(System.err);
        }

        return bos.toByteArray();
    }

    public void readPopulate(DataInputStream data) {

        try {
            this.readData(data);
        }
        catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public void readData(DataInputStream data) throws IOException {

    }

    public void writeData(DataOutputStream dos) throws IOException {

    }

    public void execute(INetworkManager network, Player player) {

    }

    public void setKey(int key) {

    }
}
