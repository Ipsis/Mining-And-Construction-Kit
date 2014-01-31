package com.ipsis.mackit.lib;

import java.io.InputStream;
import java.util.Properties;

import com.google.common.base.Throwables;

public class Reference {

    static {

        Properties prop = new Properties();

        try {
            InputStream stream = Reference.class.getClassLoader().getResourceAsStream("version.properties");
            prop.load(stream);
            stream.close();
        }
        catch (Exception e) {
            Throwables.propagate(e); // just throw it...
        }

        VERSION_NUMBER = prop.getProperty("version") + " (build " + prop.getProperty("build_number") + ")";
    }
	
	
	public static final String MOD_ID = "mackit";
	public static final String MOD_NAME = "Mining And Construction Kit";
	public static final String VERSION_NUMBER;
	public static final String CHANNEL_NAME = MOD_ID;
	public static final String DEPENDENCIES = "required-after:Forge@[9.10.1.849,)";
	
	public static final String SERVER_PROXY_CLASS = "com.ipsis.mackit.proxy.ServerProxy";
	public static final String CLIENT_PROXY_CLASS = "com.ipsis.mackit.proxy.ClientProxy";
	
	public static final int SHIFTED_ID_RANGE_CORRECTION = 256;
	
}
