package com.ipsis.mackit.manager;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class BeaverBlockManager {

	private ArrayList<Block> validBlocks;
	
	public BeaverBlockManager() {
		
		validBlocks = new ArrayList<Block>();
	}
	
	public void loadBlocks() {
		
		validBlocks.add(Blocks.water);
		validBlocks.add(Blocks.flowing_water);
	}
	
	public boolean isValid(Block b) {
		
		return validBlocks.contains(b);
	}
}
