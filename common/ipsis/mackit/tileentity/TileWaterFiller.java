package ipsis.mackit.tileentity;

import ipsis.mackit.core.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileWaterFiller extends TileEntity {
	
	private int step;
	private boolean running = false;
	private int mode;
	private int depth;
	private int tcount = 0;
	
	private static int STEPS_CIRC = 7;
	private static int STEPS_PYRAMID = 3;
	private static int STEPS_COL7 = 15;
	private static int STEPS_COL11 = 11;
	
	private static int UPDATE_FREQ = 20;

	
	public TileWaterFiller() {
	}
	
	private void placeDirt(int x, int y, int z) {
		
		int id = worldObj.getBlockId(x, y, z);
		if (id == Block.waterStill.blockID || id == Block.waterMoving.blockID) {		
			worldObj.setBlock(x,  y,  z, Block.dirt.blockID);
		}
	}
	
	private void runCircle() {
		
		placeDirt(xCoord - step, yCoord, zCoord);
		placeDirt(xCoord + step, yCoord, zCoord);
		
		placeDirt(xCoord, yCoord, zCoord - step);
		placeDirt(xCoord, yCoord, zCoord + step);
		
		int d = 1;
		for (int c = (-1 * step) + 1; c < 0; c++) {
			placeDirt(xCoord + c, yCoord, zCoord + d);
			placeDirt(xCoord + c, yCoord, zCoord - d);
			d++;
		}
		
		d = 1;
		for (int c = step - 1; c > 0; c--) {
			placeDirt(xCoord + c, yCoord, zCoord + d);
			placeDirt(xCoord + c, yCoord, zCoord - d);
			d++;
		}		
	}
	
	private void runPyramid() {
		
		int ox = xCoord - step;
		int oz = zCoord + step;
		
		for (int c = 0; c < (step * 2) + 1; c++) {
			for (int d = 0; d < (step * 2) + 1; d++) {
				int px = ox + c;
				int pz = oz - d;
				int py = yCoord - step;
				
				if (step == 1 && px == xCoord && py == yCoord && pz == zCoord) {
					continue;
				}
				
				placeDirt(px, py, pz);
			}
		}
	}
	
	private void runColumn(int width) {
		
		int ox = xCoord - ((width - 1) / 2);
		
		for (int c = 0; c < width; c++) {
			
			int px = ox + c;
			int pz = zCoord;
			int py = yCoord - step + 1;
			
			if (step == 1 && px == xCoord && py == yCoord && pz == zCoord) {
				continue;
			}
			
			placeDirt(px, py, pz);
		}
		
	}
	
	private boolean isFinished() {

		if (mode == 0 || step > depth) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public void updateEntity() {
		
		tcount++;
		if (tcount % UPDATE_FREQ != 0) {
			return;
		} else {
			tcount = 0;
		}
		
		if (!running || mode == 0) {
			return;
		}
			
		if (!worldObj.isRemote) {
			
			if (isFinished()) {
				/* all finished so replace the water filler block */
				worldObj.setBlock(xCoord, yCoord, zCoord, Block.dirt.blockID);
			} else {
				switch (mode) {
				case 1:
					runCircle();
					break;
				case 2:
					runPyramid();
					break;
				case 3:
					runColumn(7);
					break;
				case 4:
					runColumn(11);
					break;
				}
					
				step++;
			}
		}
	}
	
	public void setRunning() {
		
		if (!running) {
			running = true;
			step = 1;
			tcount = 0;
			mode = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			
			switch (mode) {
			case 0:
					running = false;
					break;
			case 1:
					depth = STEPS_CIRC;
					break;
			case 2:
					depth = STEPS_PYRAMID;
					break;
			case 3:
					depth = STEPS_COL7;
					break;
			case 4:
					depth = STEPS_COL11;
					break;
			default:
					running = false;
					break;
			}
		}
	}
	
	public boolean getRunning() {
		return running;
	}
	
	
	@Override
	public void writeToNBT(NBTTagCompound compound) { 
		super.writeToNBT(compound);
		
		compound.setByte("Mode", (byte)mode);
		compound.setByte("Step", (byte)step);
		compound.setBoolean("Running", running);
		compound.setByte("Depth",  (byte)depth);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		mode = compound.getByte("Mode");
		step = compound.getByte("Step");
		running = compound.getBoolean("Running");
		depth = compound.getByte("Depth");
	}
}
