package com.ipsis.mackit.tileentity;

import com.ipsis.mackit.block.BlockBeaverBlock;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class TileBeaverBlock extends TileEntity {
	
	public TileBeaverBlock() {
		this.isRunning = false;
		this.quadrant = 0;
		this.level = 0;
		this.ticks = 0;
	}
	
	private BlockBeaverBlock.Mode mode;
	
	private boolean isRunning;
	public boolean getIsRunning() {
		return isRunning;
	}
	
	public void setIsRunning() {
		if (!isRunning) {
			this.isRunning = true;
			int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			mode = BlockBeaverBlock.getMetadataMode(metadata);
		}
	}
	
	private void placeDirt(int x, int y, int z, boolean check) {
		if (check) {
			int id = worldObj.getBlockId(x, y, z);
			if (id != Block.waterStill.blockID && id != Block.waterMoving.blockID)
				return;
		}
		
		worldObj.setBlock(x, y, z, Block.dirt.blockID);		
	}
	
	private int quadrant;
	private int level;
	private int ticks;
	
	@Override
	public void updateEntity() {
		
		ticks++;
		if (ticks % 20 != 0)
			return;
		
		if (worldObj.isRemote)
			return;
		
		if (!isRunning)
			return;
		
		if (level == 5) {
			/* destroy the block and tile entity */
			worldObj.setBlock(xCoord, yCoord, zCoord, Block.dirt.blockID);
		}
			
		
		/* Assume surface for now! */
		
		int x, y, z;
		
			int mx, mz;
			if (quadrant == 0) {
				mx = 1;
				mz = 1;
			} else if (quadrant == 1) {
				mx = 1;
				mz = -1;
			} else if (quadrant == 2) {
				mx = -1;
				mz = -1;
			} else {
				mx = -1;
				mz = 1;
			}
			
			/* Horizontal */
			for (x = 1; x <= level; x++) {
				placeDirt(xCoord + (x * mx), yCoord, zCoord + (level * mz), false);				
			}
			
			for (z = 1; z <= level; z++) {
				placeDirt(xCoord + (level * mx), yCoord, zCoord + (z * mz), false);
			}
		
			if (quadrant == 3)
				level++;
			
			quadrant = (quadrant + 1) % 4;
		
		
	}

}
