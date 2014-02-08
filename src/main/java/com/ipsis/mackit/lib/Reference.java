package com.ipsis.mackit.lib;

import java.io.InputStream;
import java.util.Properties;

import com.google.common.base.Throwables;

public class Reference {
	
	public static final String MOD_ID = "mackit";
	public static final String MOD_NAME = "Mining And Construction Kit";
	public static final String VERSION_NUMBER = "1.6.4-0.1";
	public static final String CHANNEL_NAME = MOD_ID;
	public static final String DEPENDENCIES = "required-after:Forge@[9.10.1.849,)";
	
	public static final String SERVER_PROXY_CLASS = "com.ipsis.mackit.proxy.ServerProxy";
	public static final String CLIENT_PROXY_CLASS = "com.ipsis.mackit.proxy.ClientProxy";
	
	public static final int SHIFTED_ID_RANGE_CORRECTION = 256;
	
}
