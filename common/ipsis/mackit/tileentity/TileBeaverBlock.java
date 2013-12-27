package ipsis.mackit.tileentity;

import ipsis.mackit.block.BlockBeaverBlock;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileBeaverBlock extends TileEntity {
	
	private int step;
	private boolean running = false;
	private int mode;
	private int tcount = 0;
	
	private static int steps[] = { 7, 3, 15, 11 };
	private static int UPDATE_FREQ = 10;

	
	public TileBeaverBlock() {
	}
	
	private void placeDirt(int x, int y, int z) {
		
		int id = worldObj.getBlockId(x, y, z);
		if (id == Block.waterStill.blockID || id == Block.waterMoving.blockID) {		
			worldObj.setBlock(x,  y,  z, Block.dirt.blockID);
		}
	}
	
	private void runSurface() {
		
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
	
	private void runDam() {
		
	}
	
	private void runColumn() {
		
	}
	
	private boolean isFinished() {

		if (running && step > steps[mode]) {
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
		
		if (!running) {
			return;
		}
			
		if (!worldObj.isRemote) {
			
			if (isFinished()) {
				/* all finished so replace the water filler block */
				worldObj.setBlock(xCoord, yCoord, zCoord, Block.dirt.blockID);
			} else {
				if (mode == 0)
					runSurface();
				else if (mode == 1)
					runDam();
				else
					runColumn();
			}
		}
	}
	
	public void setRunning() {
		
		if (!running) {
			running = true;
			step = 1;
			tcount = 0;
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			mode = (meta & BlockBeaverBlock.MODE_MASK) >> BlockBeaverBlock.MODE_SHIFT;			
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
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		mode = compound.getByte("Mode");
		step = compound.getByte("Step");
		running = compound.getBoolean("Running");
	}
}
