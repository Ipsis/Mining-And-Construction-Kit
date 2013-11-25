package ipsis.mackit.fluid;

import ipsis.mackit.block.ModBlocks;
import ipsis.mackit.lib.Reference;
import net.minecraftforge.fluids.Fluid;

public class FluidDye extends Fluid {
	
	public FluidDye(String name) {
		super(name);
		
		this.setViscosity(this.getViscosity() * 5);		
	
	}
	

}
