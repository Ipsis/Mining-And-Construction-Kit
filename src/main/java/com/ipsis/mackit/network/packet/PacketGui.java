package com.ipsis.mackit.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetworkManager;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.inventory.ContainerEnchanter;
import com.ipsis.mackit.lib.GuiIds;
import com.ipsis.mackit.network.PacketTypeHandler;
import com.ipsis.mackit.tileentity.TileEnchanter;

import cpw.mods.fml.common.network.Player;

public class PacketGui extends PacketMK {

	public static int CTRL_BUTTON = 0;
	public static int CTRL_SLIDER = 1; /* yeah right! */
	
	public int guiId;
	public int ctrlType;
	public int ctrlId;
	public int ctrlData;
	
	public PacketGui() {
		
		super(PacketTypeHandler.GUI, false);
	}
	
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
		
		EntityPlayer entityPlayer = (EntityPlayer)player;
		Container container = entityPlayer.openContainer;
		
		if (container != null) {
			if (guiId == GuiIds.ENCHANTER && container instanceof ContainerEnchanter) {
				TileEnchanter te = ((ContainerEnchanter)container).getTileEntity();
				if (te != null)
					te.handleInterfacePacket(ctrlType, ctrlId, ctrlData, player);
			}
		}	
	}
	
	@Override
	public String toString() {

		return String.format("PacketGui: gui=%d type=%d id=%d data=%d", guiId, ctrlType, ctrlId, ctrlData);
	}
}
