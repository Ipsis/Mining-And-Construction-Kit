package ipsis.mackit.fluid;

import net.minecraftforge.fluids.Fluid;

public class FluidDye extends Fluid {
	
	public FluidDye(String name) {
		super(name);
		
		this.setViscosity(this.getViscosity() * 5);
	}

}