package com.ipsis.mackit.manager;

public class MKManagers {
	
	public static BeaverBlockManager bbMgr = new BeaverBlockManager();

	public static void preInit() {
		
	}
	
	public static void initialise() {
		
	}
	
	public static void postInit() {
		
		bbMgr.loadBlocks();
	}
}
