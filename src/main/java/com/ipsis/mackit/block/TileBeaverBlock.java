package com.ipsis.mackit.block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.helper.Point;

public class TileBeaverBlock extends TileEntity {

	private boolean running;
	private ArrayList<Point> surfaceBlocks;
	private int currTicks = 0;
	private int mode;
	private int currLevel = 0; /* cube or tower mode depth */
	
	private static final int SURFACE_RANGE = 4;
	private static final int CUBE_RANGE = 7;
	private static final int TOWER_RANGE = 39;
	private static final int UPDATE_FREQ = 5;
	private static final int BLOCKS_PER_UPDATE = 5;
	
	
	public static final int MODE_SURFACE = 0;
	public static final int MODE_CUBE = 1;
	public static final int MODE_TOWER = 2;
	
	public TileBeaverBlock() {
		
		running = false;
		mode = MODE_SURFACE;
	}
	
	public void setRunning() {
		
		running = true;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	private void setMode(int mode) {
	
		this.mode = mode;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public int getMode() {
		
		if (mode < 0 || mode > 3)
			return 0;
		
		return mode;
	}
	
	public void setNextMode() {
		
		if (!running)
			setMode((mode + 1) % 3);	
	}
	
	private void changeBlockAtPoint(Point p) {
		
		worldObj.setBlock(p.x, p.y, p.z, Blocks.dirt);
	}
	
	private void changeLiquidBlockAtPoint(Point p) {
		
		Block b = worldObj.getBlock(p.x, p.y, p.z);
		if (!isValidLiquidBlock(b))
			return;
		
		changeBlockAtPoint(p);
	}
			
	private boolean isValidLiquidBlock(Block b) {
		
		if (true) return true;
		
		if (b == Blocks.flowing_water || b == Blocks.water)
			return true;
		
		return false;
	}
	
	private void tryAddPoint(Point p) {
		
		Block b = worldObj.getBlock(p.x, p.y, p.z);
		if (isValidLiquidBlock(b))
			surfaceBlocks.add(p);
	}
	
	
	/************************
	 * Surface Mode
	 ************************/
	private void createSurfaceBlockList() {
		
		surfaceBlocks = new ArrayList<Point>();
		
		Point p = new Point(this.xCoord, this.yCoord, this.zCoord);
		
		int xRange = SURFACE_RANGE;
		int zRange;
		
		for (int l = 0; l < 2; l++) {
			
			zRange = SURFACE_RANGE;
			for (int tx = 0; tx <= xRange; tx++) {
				for (int tz = (zRange * -1) ; tz <= zRange; tz++) {
					
					Point np = new Point(p.x, p.y, p.z);
					np.translate(tx * (l == 0 ? -1 : 1), 0, tz);
					if (!np.equals(p))
						tryAddPoint(np);
				}
				
				zRange -= 1;
			}
		}
		
		Collections.shuffle(surfaceBlocks);
	}
	
	private void runSurface() {
		
		Iterator<Point> iter = surfaceBlocks.iterator();

		int c = BLOCKS_PER_UPDATE;
		while (iter.hasNext() && c != 0) {
			
			Point p = iter.next();
			iter.remove();
			changeLiquidBlockAtPoint(p);
			c--;
		}
	
		if (surfaceBlocks.isEmpty()) {
			running = false;
			changeBlockAtPoint(new Point(this.xCoord, this.yCoord, this.zCoord));
		}
	}
	
	/************************
	 * Cube Mode
	 ************************/
	private void createCubeBlockList() {
		
		surfaceBlocks = new ArrayList<Point>();
		
		Point p = new Point(this.xCoord, this.yCoord, this.zCoord);
		
		for (int xOffset = -3; xOffset <= 3; xOffset++) {
			for (int zOffset = -3; zOffset <= 3; zOffset++) {
				
				Point np = new Point(p.x + xOffset, p.y - currLevel, p.z + zOffset);
				if (!np.equals(p))
					tryAddPoint(np);
			}
		}
			
		Collections.shuffle(surfaceBlocks);					
	}
	
	private void runCube() {
		
		Iterator<Point> iter = surfaceBlocks.iterator();

		int c = BLOCKS_PER_UPDATE;
		while (iter.hasNext() && c != 0) {
			
			Point p = iter.next();
			iter.remove();
			changeLiquidBlockAtPoint(p);
			c--;
		}
		
		if (surfaceBlocks.isEmpty()) {
			if (currLevel == 0) {
				running = false;
				changeBlockAtPoint(new Point(this.xCoord, this.yCoord, this.zCoord));
			} else {
				currLevel--;
				createCubeBlockList();
			}
		}		
	}
	
	/************************
	 * Tower Mode
	 ************************/
	private void createTowerBlockList() {
		
		surfaceBlocks = new ArrayList<Point>();
		
		Point p = new Point(this.xCoord, this.yCoord, this.zCoord);
		
		for (int xOffset = -1; xOffset <= 1; xOffset++) {
			for (int zOffset = -1; zOffset <= 1; zOffset++) {
				
				Point np = new Point(p.x + xOffset, p.y - currLevel, p.z + zOffset);
				if (!np.equals(p))
					tryAddPoint(np);
			}
		}
			
		Collections.shuffle(surfaceBlocks);
	}
	
	private void runTower() {
		
		Iterator<Point> iter = surfaceBlocks.iterator();

		int c = BLOCKS_PER_UPDATE;
		while (iter.hasNext() && c != 0) {
			
			Point p = iter.next();
			iter.remove();
			changeLiquidBlockAtPoint(p);
			c--;
		}
		
		if (surfaceBlocks.isEmpty()) {
			if (currLevel == 0) {
				running = false;
				changeBlockAtPoint(new Point(this.xCoord, this.yCoord, this.zCoord));
			} else {
				currLevel--;
				createTowerBlockList();
			}
		}	
	}
	
	private void runMode() {
		
		Iterator<Point> iter = surfaceBlocks.iterator();

		int c = BLOCKS_PER_UPDATE;
		while (iter.hasNext() && c != 0) {
			
			Point p = iter.next();
			iter.remove();
			changeLiquidBlockAtPoint(p);
			c--;
		}
		
		if (mode == MODE_SURFACE) {
			
			if (surfaceBlocks.isEmpty()) {
				running = false;
				changeBlockAtPoint(new Point(this.xCoord, this.yCoord, this.zCoord));
			}
		} else {
			
			if (surfaceBlocks.isEmpty()) {
				if (currLevel == 0) {
					running = false;
					changeBlockAtPoint(new Point(this.xCoord, this.yCoord, this.zCoord));
				} else {
					currLevel--;
					if (mode == MODE_CUBE)
						createCubeBlockList();
					else
						createTowerBlockList();
				}
			}				
		}
	}
	
	private void initMode() {
		
		if (mode == MODE_SURFACE) {
			currLevel = 0;
			createSurfaceBlockList();
		} else if (mode == MODE_CUBE) {
			currLevel = CUBE_RANGE;
			createCubeBlockList();
		} else if (mode == MODE_TOWER) {
			currLevel = TOWER_RANGE;
			createTowerBlockList();
		}
	}
	
	private void runMode2() {
		
		if (mode == MODE_SURFACE)
			runSurface();
		else if (mode == MODE_CUBE)
			runCube();
		else if (mode == MODE_TOWER)
			runTower();
	}
	
	@Override
	public void updateEntity() {
		
		if (!running)
			return;
		
		currTicks++;
		if (currTicks % UPDATE_FREQ != 0)
			return;

		if (worldObj.isRemote)
			return;
		
		if (surfaceBlocks == null)
			initMode();
		else
			runMode();
	}

	/************************
	 * NBT
	 ************************/
	@Override
	public void readFromNBT(NBTTagCompound tags) {
		
		mode = tags.getInteger("Mode");
		running = tags.getBoolean("Running");
		
		/*
		if (surfaceBlocks != null) {
			Iterator<Point> iter = surfaceBlocks.iterator();

			int c = 5;
			while (iter.hasNext() && c != 0) {				
				Point p = iter.next();
				
			}			
		} */
		
		super.readFromNBT(tags);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tags) {

		tags.setInteger("Mode", mode);
		tags.setBoolean("Running", running);
		super.writeToNBT(tags);
	}
	
	/************************
	 * Packets
	 ************************/
	@Override
	public Packet getDescriptionPacket() {
		
		NBTTagCompound tags = new NBTTagCompound();
		tags.setInteger("Mode", mode);
		tags.setBoolean("Running", running);
		super.writeToNBT(tags);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tags);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {

		mode = pkt.func_148857_g().getInteger("Mode");
		running = pkt.func_148857_g().getBoolean("Running");	
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	
}
