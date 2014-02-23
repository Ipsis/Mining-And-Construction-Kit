package com.ipsis.mackit.manager;

public class MKRegistry {
	
	private static SqueezableManager squeezableManager;
	private static SqueezerManager squeezerManager;
	
	private MKRegistry() {
		
	}
	
	public static SqueezableManager getSqueezableManager() {
		
		return squeezableManager;
	}
	
	public static SqueezerManager getSqueezerManager() {
		
		return squeezerManager;
	}
	
	public static void init() {
		
		squeezableManager = new SqueezableManager();
		squeezerManager = new SqueezerManager();
	}
	
}
