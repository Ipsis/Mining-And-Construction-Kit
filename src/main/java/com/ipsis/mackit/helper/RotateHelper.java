package com.ipsis.mackit.helper;

import net.minecraftforge.common.util.ForgeDirection;

import com.ipsis.cofhlib.util.BlockCoord;

/**
 * Not sure about all this.
 * I get the feeling I don't know what I'm doing :)
 */

public class RotateHelper {

	private static enum QUAD {
		Q1,
		Q2,
		Q3,
		Q4
	};
	
	private static final int angles[][] = {
		{ 0, 90, 180, 270 },
		{ 270, 0, 90, 180 },
		{ 180, 270, 0, 90 },
		{ 90, 180, 270, 0 }
	};
	
	private static int dirToIdx(ForgeDirection d) {
		int i = 0;
		
		switch (d) {
		case EAST:
			i = 0;
			break;
		case SOUTH:
			i = 1;
			break;
		case WEST:
			i = 2;
			break;
		case NORTH:
			i = 3;
			break;
		default:
			break;
		}
		
		return i;
	}
	
	private static int getRotationAngle(ForgeDirection src, ForgeDirection dst) {
		
		return angles[dirToIdx(src)][dirToIdx(dst)];
	}
	
	private static QUAD getSrcQuadrant(BlockCoord p) {
		
		if (p.x >= 0 && p.z >= 0)
			return QUAD.Q1;
		
		if (p.x < 0 && p.z >= 0)
			return QUAD.Q2;
		
		if (p.x < 0 && p.z < 0)
			return QUAD.Q3;
		
		if (p.x >= 0 && p.z < 0)
			return QUAD.Q4;
		
		return QUAD.Q1;
	}
	
	private static final QUAD dstQ[][] = {
		{ QUAD.Q1, QUAD.Q2, QUAD.Q3, QUAD.Q4 },
		{ QUAD.Q2, QUAD.Q3, QUAD.Q4, QUAD.Q1 },
		{ QUAD.Q3, QUAD.Q4, QUAD.Q1, QUAD.Q2 },
		{ QUAD.Q4, QUAD.Q1, QUAD.Q2, QUAD.Q3 }
	};
	
	private static int angleToIdx(int a) {
		int i = 0;
		
		switch (a) {
		case 0:
			i = 0;
			break;
		case 90:
			i = 1;
			break;
		case 180:
			i = 2;
			break;
		case 270:
			i = 3;
			break;
		}
		
		return i;
	}
	
	private static QUAD getDstQuadrant(QUAD srcQ, int angle) {
		
		return dstQ[srcQ.ordinal()][angleToIdx(angle)];
	}
	
	public static BlockCoord rotatePointXZ(ForgeDirection src, ForgeDirection dst, BlockCoord p) {
	
		BlockCoord newP = p.copy();
		int angle = getRotationAngle(src, dst);
		QUAD srcQ = getSrcQuadrant(p);
		QUAD dstQ = getDstQuadrant(srcQ, angle);
		
		newP.y = p.y;
		
		if (srcQ == QUAD.Q1) {
			/* x=+ve z=+ve */
			if (dstQ == QUAD.Q2) {
				newP.x = p.z * -1;
				newP.z = p.x;
			} else if (dstQ == QUAD.Q3) {
				newP.x = p.x * -1;
				newP.z = p.z * -1;
			} else if (dstQ == QUAD.Q4) {
				newP.x = p.z;
				newP.z = p.x * -1;
			}
		} else if (srcQ == QUAD.Q2){
			/* x=-ve, z=+ve */
			if (dstQ == QUAD.Q3) {
				newP.x = p.z * -1;
				newP.z = p.x;
			} else if (dstQ == QUAD.Q4) {
				newP.x = p.x * -1;
				newP.z = p.z * -1;
			} else if (dstQ == QUAD.Q1) {
				newP.x = p.z;
				newP.z = p.x * -1;
			}			
		} else if (srcQ == QUAD.Q3){
			/* x=-ve, z=-ve */
			if (dstQ == QUAD.Q4) {
				newP.x = p.z * -1;
				newP.z = p.x;
			} else if (dstQ == QUAD.Q1) {
				newP.x = p.x * -1;
				newP.z = p.z * -1;
			} else if (dstQ == QUAD.Q2) {
				newP.x = p.z;
				newP.z = p.x * -1;
			}				
		} else if (srcQ == QUAD.Q4) {
			/* x=+ve, z=-ve */
			if (dstQ == QUAD.Q1) {
				newP.x = p.z * -1;
				newP.z = p.x;
			} else if (dstQ == QUAD.Q2) {
				newP.x = p.x * -1;
				newP.z = p.z * -1;
			} else if (dstQ == QUAD.Q3) {
				newP.x = p.z;
				newP.z = p.x * -1;
			}	
		}
		
		return newP;
	}
}
