package com.ipsis.mackit.block;

import net.minecraftforge.common.util.ForgeDirection;

public interface IFacing {

	public void setFacing(ForgeDirection d);
	public ForgeDirection getFacing();
}
