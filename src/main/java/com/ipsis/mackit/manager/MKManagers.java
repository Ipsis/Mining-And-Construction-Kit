package com.ipsis.mackit.manager;

import com.ipsis.mackit.helper.DyeOreDictHelper;

public class MKManagers {
	
	public static BeaverBlockManager beaverBlockMgr = new BeaverBlockManager();
	public static SqueezerManager squeezerMgr = new SqueezerManager();
	public static PainterManager painterMgr = new PainterManager();
	public static StamperManager stamperMgr = new StamperManager();
	
	public static DyeOreDictHelper dyeOreDictHelper = new DyeOreDictHelper();

	public static void preInit() {
	
		/* Be VERY careful what is here as no other mod will be loaded */
	}
	
	public static void initialise() {
		
		dyeOreDictHelper.loadDyes();
	}
	
	public static void postInit() {
		
		beaverBlockMgr.loadBlocks();
		squeezerMgr.addRecipes();
	}
}
