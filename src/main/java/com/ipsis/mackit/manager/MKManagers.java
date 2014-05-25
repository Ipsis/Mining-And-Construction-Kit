package com.ipsis.mackit.manager;

import com.ipsis.mackit.util.DyeHelper;

public class MKManagers {
	
	public static DyeManager dyeMgr = new DyeManager();
	public static BeaverBlockManager beaverBlockMgr = new BeaverBlockManager();
	public static SqueezerManager squeezerMgr = new SqueezerManager();
	public static PainterManager painterMgr = new PainterManager();
	public static StamperManager stamperMgr = new StamperManager();
	public static DyeHelper dyeHelper = new DyeHelper();

	public static void preInit() {
	
	}
	
	public static void initialise() {
		
		dyeHelper.loadOres();
		squeezerMgr.addRecipes();
	}
	
	public static void postInit() {
		
		beaverBlockMgr.loadBlocks();
		painterMgr.loadRecipes();
	}
}
