package com.ipsis.mackit.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.network.PacketTypeHandler;

import cpw.mods.fml.common.network.Player;

public class PacketGui extends PacketMK {

	public static int CTRL_BUTTON;
	public static int CTRL_SLIDER; /* yeah right! */
	
	public int guiId;
	public int ctrlType;
	public int ctrlId;
	public int ctrlData;
	
	public PacketGui(int guiId, int ctrlType, int ctrlId, int ctrlData) {
		
		super(PacketTypeHandler.GUI, false);
		this.guiId = guiId;
		this.ctrlType = ctrlType;
		this.ctrlId = ctrlId;
		this.ctrlData = ctrlData;
	}
	
	@Override
	public void writeData(DataOutputStream dos) throws IOException {

		dos.writeByte((byte)guiId);
		dos.writeByte((byte)ctrlType);
		dos.writeByte((byte)ctrlId);
		dos.writeInt(ctrlData);
	}
	
	@Override
	public void readData(DataInputStream data) throws IOException {

		guiId = data.readByte();
		ctrlType = data.readByte();
		ctrlId = data.readByte();
		ctrlData = data.readInt();
	}
	
	@Override
	public void execute(INetworkManager network, Player player) {

		MacKit.proxy.handlePacketGui(player, guiId, ctrlType, ctrlId, ctrlData);
	}
	
	@Override
	public String toString() {

		return String.format("PacketGui: gui=%d type=%d id=%d data=%d", guiId, ctrlType, ctrlId, ctrlData);
	}
}
