package com.ipsis.mackit.manager;

public class MKRegistry {
	
	private static SqueezerManager squeezableManager;
	private static DyeManager squeezerManager;
	
	private MKRegistry() {
		
	}
	
	public static SqueezerManager getSqueezableManager() {
		
		return squeezableManager;
	}
	
	public static DyeManager getSqueezerManager() {
		
		return squeezerManager;
	}
	
	public static void init() {
		
		squeezableManager = new SqueezerManager();
		squeezerManager = new DyeManager();
	}
	
}
