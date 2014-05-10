package com.ipsis.mackit.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import com.ipsis.mackit.block.BlockBeaverBlock;
import com.ipsis.mackit.block.BlockBeaverBlock.Mode;
import com.ipsis.mackit.helper.Helper;
import com.ipsis.mackit.helper.LogHelper;

public class TileBeaverBlock extends TileEntity {
	
	public TileBeaverBlock() {
		this.isRunning = false;
		this.quadrant = 0;
		this.level = 1;
		this.ticks = 0;
	}
		
	
	private boolean isRunning;
	public boolean getIsRunning() {
		
		return isRunning;
	}
	
	public void setIsRunning() {
		
		if (!isRunning) {
			this.isRunning = true;
			int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			mode = BlockBeaverBlock.getMetadataMode(metadata);
			facing = BlockBeaverBlock.getMetadataOrientation(metadata);
		}
	}
	
	private void placeDirt(int x, int y, int z, boolean check) {
		
		if (worldObj.isRemote)
			return;
		
		if (check) {
			int id = worldObj.getBlockId(x, y, z);
			if (id != Block.waterStill.blockID && id != Block.waterMoving.blockID)
				return;
		}
		
		worldObj.setBlock(x, y, z, Block.dirt.blockID);
		
		/* Probably a better way of doing this */
		worldObj.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), Block.dirt.stepSound.getPlaceSound(), (Block.dirt.stepSound.getVolume() + 1.0F) / 2.0F, Block.dirt.stepSound.getPitch() * 0.8F);
	}
	
	private BlockBeaverBlock.Mode mode;
	private ForgeDirection facing;
	private int quadrant;
	private int level;
	private int ticks;

	private final int SURFACE_MAX_WIDTH = 8;
	private final int COLUMN_MAX_WIDTH = 8;
	private final int COLUMN_MAX_DEPTH = 16;
	private final int DAM_MAX_WIDTH = 16;
	private final int DAM_MAX_DEPTH = 8;
	
	private void runSurface() {
		
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
		for (int x = 0; x <= level; x++)
			placeDirt(xCoord + (x * mx), yCoord, zCoord + (level * mz), false);
		
		/* Vertical */
		for (int z = 0; z <= level; z++)
			placeDirt(xCoord + (level * mx), yCoord, zCoord + (z * mz), false);
		
		if (quadrant == 3 && level == SURFACE_MAX_WIDTH) {
			/* destroy the block and tile entity */
			worldObj.setBlock(xCoord, yCoord, zCoord, Block.dirt.blockID);
		} else if (quadrant == 3) {
			level++;
		}
				
		quadrant = (quadrant + 1) % 4;
	}
	
	private void runColumn() {
			
		if (level == 0) {
			for (int y = 1; y <= COLUMN_MAX_DEPTH; y++)
				placeDirt(xCoord, yCoord - y, zCoord, false);
		} else {
			
			if (facing == ForgeDirection.SOUTH || facing == ForgeDirection.NORTH) {
				for (int y = 1; y <= COLUMN_MAX_DEPTH; y++) {
					placeDirt(xCoord + level, yCoord - y, zCoord, false);
					placeDirt(xCoord - level, yCoord - y, zCoord, false);
				}
			} else {
				for (int y = 1; y <= COLUMN_MAX_DEPTH; y++) {
					placeDirt(xCoord + level, yCoord - y, zCoord, false);
					placeDirt(xCoord - level, yCoord - y, zCoord, false);
				}
			}
		}
		
		if (level == COLUMN_MAX_WIDTH) {
			/* destroy the block and tile entity */
			worldObj.setBlock(xCoord, yCoord, zCoord, Block.dirt.blockID);
		} else {
			level++;
		}
	}
	
	private void runDam() {
		
		if (facing == ForgeDirection.SOUTH || facing == ForgeDirection.NORTH) {
			if (level == DAM_MAX_DEPTH) {
				for (int x = 1; x < DAM_MAX_WIDTH; x++) {
					placeDirt(xCoord + x, yCoord, zCoord, false);
					placeDirt(xCoord - x, yCoord, zCoord, false);
				}
			} else {
				for (int x = 0; x < DAM_MAX_WIDTH; x++) {
					placeDirt(xCoord + x, yCoord - level, zCoord, false);
					placeDirt(xCoord - x, yCoord - level, zCoord, false);
				}
			}
			
		} else {
			if (level == DAM_MAX_DEPTH) {
				for (int z = 1; z < DAM_MAX_WIDTH; z++) {
					placeDirt(xCoord, yCoord, zCoord + z, false);
					placeDirt(xCoord, yCoord, zCoord - z, false);
				}
			} else {
				for (int z = 1; z < DAM_MAX_WIDTH; z++) {
					placeDirt(xCoord, yCoord - level, zCoord + z, false);
					placeDirt(xCoord, yCoord - level, zCoord - z, false);
				}
			}
		}
		
		if (level == DAM_MAX_DEPTH) {
			/* destroy the block and tile entity */
			worldObj.setBlock(xCoord, yCoord, zCoord, Block.dirt.blockID);
		} else {
			level++;
		}
	}
	
	@Override
	public void updateEntity() {
		
		ticks++;
		if (ticks % 5 != 0)
			return;
		
		if (worldObj.isRemote)
			return;
		
		if (!isRunning)
			return;
		
		if (this.mode == BlockBeaverBlock.Mode.SURFACE)
			runSurface();
		else if (this.mode == BlockBeaverBlock.Mode.COLUMN)
			runColumn();
		else
			runDam();
		
	}

	/* NBT */
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		
		super.writeToNBT(nbtTagCompound);
		
		nbtTagCompound.setBoolean("Running", this.isRunning);
		Helper.writeToNBTForgeDirection("Facing", this.facing, nbtTagCompound);
		
		nbtTagCompound.setByte("Mode", (byte)this.mode.getMdValue());
		nbtTagCompound.setByte("Quadrant", (byte)this.quadrant);
		nbtTagCompound.setByte("Level", (byte)this.level);
		
		LogHelper.severe(this);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		
		super.readFromNBT(nbtTagCompound);
		
		this.isRunning = nbtTagCompound.getBoolean("Running");
		this.facing = Helper.readFromNBTForgeDirection("Facing", nbtTagCompound);
		
		this.mode = Mode.getMode(nbtTagCompound.getByte("Mode"));
		this.quadrant = nbtTagCompound.getByte("Quadrant");
		this.level = nbtTagCompound.getByte("Level");		
	}
	
	@Override
	public String toString() {

		return String.format("TileBeaverBlock - Class:%s isRunning:%s facing:%s mode:%s quadrant:%d level:%d\n",
				this.getClass().getSimpleName(),
				this.isRunning, this.facing, this.mode, this.quadrant, this.level);
	}
	
}

