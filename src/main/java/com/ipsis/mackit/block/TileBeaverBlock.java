package com.ipsis.mackit.block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

import com.ipsis.cofhlib.util.BlockCoord;
import com.ipsis.mackit.manager.MKManagers;

public class TileBeaverBlock extends TileEntity {

	private boolean running;
	private ArrayList<BlockCoord> surfaceBlocks;
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
					
	private void tryAddBlock(BlockCoord p) {
		
		Block b = worldObj.getBlock(p.x, p.y, p.z);
		if (MKManagers.beaverBlockMgr.isValid(b))
			surfaceBlocks.add(p);
	}
	
	
	/************************
	 * Surface Mode
	 ************************/
	private void createSurfaceBlockList() {
		
		surfaceBlocks = new ArrayList<BlockCoord>();
		
		BlockCoord p = new BlockCoord(this);
		
		int xRange = SURFACE_RANGE;
		int zRange;
		
		for (int l = 0; l < 2; l++) {
			
			zRange = SURFACE_RANGE;
			for (int tx = 0; tx <= xRange; tx++) {
				for (int tz = (zRange * -1) ; tz <= zRange; tz++) {
					
					/* Translate the block position in both x and z */
					BlockCoord np = p.copy();
					np.x += (tx * (l == 0 ? -1 : 1));
					np.y += -1; /* block sits above the water */
					np.z += tz;

					if (!np.equals(p))
						tryAddBlock(np);
				}
				
				zRange -= 1;
			}
		}
		
		Collections.shuffle(surfaceBlocks);
	}
	
	/************************
	 * Cube Mode
	 ************************/
	private void createCubeBlockList() {
		
		surfaceBlocks = new ArrayList<BlockCoord>();
		
		BlockCoord p = new BlockCoord(this);
		
		for (int xOffset = -3; xOffset <= 3; xOffset++) {
			for (int zOffset = -3; zOffset <= 3; zOffset++) {
				
				BlockCoord np = new BlockCoord(p.x + xOffset, p.y - currLevel - 1, p.z + zOffset);
				if (!np.equals(p))
					tryAddBlock(np);
			}
		}
			
		Collections.shuffle(surfaceBlocks);					
	}
	
	/************************
	 * Tower Mode
	 ************************/
	private void createTowerBlockList() {
		
		surfaceBlocks = new ArrayList<BlockCoord>();
		
		BlockCoord p = new BlockCoord(this);
		
		for (int xOffset = -1; xOffset <= 1; xOffset++) {
			for (int zOffset = -1; zOffset <= 1; zOffset++) {
				
				BlockCoord np = new BlockCoord(p.x + xOffset, p.y - currLevel - 1, p.z + zOffset);
				if (!np.equals(p))
					tryAddBlock(np);
			}
		}
			
		Collections.shuffle(surfaceBlocks);
	}
	

	private void runMode() {
		
		Iterator<BlockCoord> iter = surfaceBlocks.iterator();

		int c = BLOCKS_PER_UPDATE;
		while (iter.hasNext() && c != 0) {
			
			if (c == BLOCKS_PER_UPDATE) {
				worldObj.playSoundEffect(
						(double)((float)xCoord + 0.5F), (double)((float)yCoord + 0.5F), (double)((float)zCoord + 0.5F),
						Block.soundTypeGrass.getBreakSound(), 
						1.0F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
						
			BlockCoord p = iter.next();
			iter.remove();
			worldObj.setBlock(p.x, p.y, p.z, Blocks.dirt);;
			c--;
		}
		
		if (mode == MODE_SURFACE) {
			
			if (surfaceBlocks.isEmpty()) {
				running = false;
				worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.dirt);
			}
		} else {
			
			if (surfaceBlocks.isEmpty()) {
				if (currLevel == 0) {
					running = false;
					worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.dirt);
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
	public void readFromNBT(NBTTagCompound nbttagcompound) {
				
		super.readFromNBT(nbttagcompound);
		
		mode = nbttagcompound.getInteger("Mode");
		running = nbttagcompound.getBoolean("Running");
		currLevel = nbttagcompound.getInteger("Level");

		NBTTagList nbttaglist = nbttagcompound.getTagList("Blocks", Constants.NBT.TAG_COMPOUND);
		
		if (nbttaglist.tagCount() > 0) {
			surfaceBlocks = new ArrayList<BlockCoord>();
		
			for (int i = 0; i < nbttaglist.tagCount(); i++) {
				NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
						.getCompoundTagAt(i);
				int x = nbttagcompound.getInteger("xCoord");
				int y = nbttagcompound.getInteger("yCoord");
				int z = nbttagcompound.getInteger("zCoord");

				surfaceBlocks.add(new BlockCoord(x, y, z));
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		
		super.writeToNBT(nbttagcompound);

		nbttagcompound.setInteger("Mode", mode);
		nbttagcompound.setBoolean("Running", running);
		nbttagcompound.setInteger("Level", currLevel);
		
		NBTTagList nbttaglist = new NBTTagList();

		if (surfaceBlocks != null) {
			Iterator<BlockCoord> iter = surfaceBlocks.iterator();

			while (iter.hasNext()) {

				BlockCoord p = iter.next();

				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setInteger("xCoord", p.x);
				nbttagcompound1.setInteger("yCoord", p.y);
				nbttagcompound1.setInteger("zCoord", p.z);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Blocks", nbttaglist);
	}
	
	/************************
	 * Packets
	 ************************/
	@Override
	public Packet getDescriptionPacket() {
		
		/* We only sync the mode and running */
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
		super.readFromNBT(pkt.func_148857_g());
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	
}
