package com.ipsis.mackit.manager;

public class MKRegistry {
	
	private static SqueezerManager squeezerManager;
	private static DyeManager dyeManager;
	
	private MKRegistry() {
		
	}
	
	public static SqueezerManager getSqueezerManager() {
		
		return squeezerManager;
	}
	
	public static DyeManager getDyeManager() {
		
		return dyeManager;
	}
	
	public static void init() {
		
		squeezerManager = new SqueezerManager();
		dyeManager = new DyeManager();
	}
	
}
