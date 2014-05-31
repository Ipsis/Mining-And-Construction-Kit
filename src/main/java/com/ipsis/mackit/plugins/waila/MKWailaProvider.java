package com.ipsis.mackit.plugins.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidTank;

import com.ipsis.mackit.block.TileBeaverBlock;
import com.ipsis.mackit.block.TileMachinePainter;
import com.ipsis.mackit.block.TileMachineSqueezer;
import com.ipsis.mackit.block.TileMachineStamper;

public class MKWailaProvider implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		
		return null;
	}
 
	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		
		return currenttip;
	}
 
	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

		if (accessor.getTileEntity() instanceof TileMachineSqueezer) {
			
			TileMachineSqueezer te = (TileMachineSqueezer)accessor.getTileEntity();
			addTankInfo(currenttip, "Red   ", te.tankMgr.getTank(te.RED_TANK));
			addTankInfo(currenttip, "Yellow", te.tankMgr.getTank(te.YELLOW_TANK));
			addTankInfo(currenttip, "Blue  ", te.tankMgr.getTank(te.BLUE_TANK));
			addTankInfo(currenttip, "White ", te.tankMgr.getTank(te.WHITE_TANK));
			addTankInfo(currenttip, "Pure  ", te.tankMgr.getTank(te.PURE_TANK));
		} else if (accessor.getTileEntity() instanceof TileMachineStamper) {
			
			TileMachineStamper te = (TileMachineStamper)accessor.getTileEntity();
			addTankInfo(currenttip, "Pure  ", te.tankMgr.getTank(te.PURE_TANK));			
		} else if (accessor.getTileEntity() instanceof TileBeaverBlock) {
			
			TileBeaverBlock te = (TileBeaverBlock)accessor.getTileEntity();
			int mode = te.getMode();
			currenttip.add("Mode : " + (mode == te.MODE_CUBE ? "Mode" : mode == te.MODE_SURFACE ? "Surface" : "Tower"));			
		} else if (accessor.getTileEntity() instanceof TileMachinePainter) {
			
			TileMachinePainter te = (TileMachinePainter)accessor.getTileEntity();
			addTankInfo(currenttip, "Pure  ", te.tankMgr.getTank(te.PURE_TANK));			
		}
		
		return currenttip;
	}
 
	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		
		return currenttip;
	}
	
	private void addTankInfo(List<String> currenttip, String name, IFluidTank tank) {

		currenttip.add(name + " : " + tank.getFluidAmount() + "/" + tank.getCapacity() + " mB");
	}
 
	public static void callbackRegister(IWailaRegistrar registrar){
		
		registrar.registerBodyProvider(new MKWailaProvider(), TileMachineSqueezer.class);
		registrar.registerBodyProvider(new MKWailaProvider(), TileMachineStamper.class);
		registrar.registerBodyProvider(new MKWailaProvider(), TileMachinePainter.class);
		registrar.registerBodyProvider(new MKWailaProvider(), TileBeaverBlock.class);		
	}

}
