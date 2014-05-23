package com.ipsis.mackit.manager;

public class MKManagers {
	
	public static DyeManager dyeMgr = new DyeManager();
	public static BeaverBlockManager beaverBlockMgr = new BeaverBlockManager();
	public static SqueezerManager squeezerMgr = new SqueezerManager();

	public static void preInit() {
		
	}
	
	public static void initialise() {
		
	}
	
	public static void postInit() {
		
		beaverBlockMgr.loadBlocks();
	}
}
