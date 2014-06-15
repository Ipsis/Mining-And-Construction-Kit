package com.ipsis.mackit.manager;

import com.ipsis.mackit.helper.DyeOreDictHelper;
import com.ipsis.mackit.helper.OreDictHelper;

public class MKManagers {
	
	public static BeaverBlockManager beaverBlockMgr = new BeaverBlockManager();
	public static SqueezerManager squeezerMgr = new SqueezerManager();
	public static PainterManager painterMgr = new PainterManager();
	public static StamperManager stamperMgr = new StamperManager();
	

	public static void preInit() {
	
		/* Be VERY careful what is here as no other mod will be loaded */
	}
	
	public static void initialise() {
		
		DyeOreDictHelper.loadDyes();
	}
	
	public static void postInit() {
		
		OreDictHelper.loadOres();
		beaverBlockMgr.loadBlocks();
		squeezerMgr.addRecipes();
	}
}
