package ipsis.mackit.item.crafting;

import java.util.ArrayList;

import net.minecraftforge.fluids.FluidStack;

public class DyeSource {

	private int id;
	private ArrayList<FluidStack> fluids;
	
	public DyeSource(int id) {
		this.id = id;
		fluids = new ArrayList<FluidStack>();
	}
	
	public void addOutputFluid(FluidStack output) {
		fluids.add(output);
	}
	
	public FluidStack[] getOutput() {
		return fluids.toArray(new FluidStack[fluids.size()]);
	}
	
	public int getItemId() {
		return id;
	}
	
	public int getOutputAmount(FluidStack output) {
		for (FluidStack f: fluids) {
			if (f.isFluidEqual(output)) {
				return f.amount;
			}
		}
		
		return 0;
	}
	
	public boolean isOutput(FluidStack ouptut) {
		
		for (FluidStack f: fluids) {
			if (f.isFluidEqual(ouptut))
				return true;
		}
		
		return false;
	}
}
