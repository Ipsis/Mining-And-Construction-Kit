package com.ipsis.mackit.network;

import com.ipsis.mackit.network.message.MessageGui;
import com.ipsis.mackit.reference.Reference;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toLowerCase());

    public static void init()
    {
    	INSTANCE.registerMessage(MessageGui.class,  MessageGui.class, 0, Side.SERVER);
    }
	
}
