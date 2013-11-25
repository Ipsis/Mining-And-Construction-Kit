package ipsis.mackit.fluid;

import ipsis.mackit.lib.Strings;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {
	
	public static Fluid fluidRedDye;
	public static Fluid fluidYellowDye;
	public static Fluid fluidBlueDye;
	public static Fluid fluidWhiteDye;
	public static Fluid fluidPureDye;

	
	public static void init() {
		
		fluidRedDye = new FluidDye(Strings.LIQUID_RED_DYE);
		fluidYellowDye = new FluidDye(Strings.LIQUID_YELLOW_DYE);
		fluidBlueDye = new FluidDye(Strings.LIQUID_BLUE_DYE);
		fluidWhiteDye = new FluidDye(Strings.LIQUID_WHITE_DYE);
		fluidPureDye = new FluidDye(Strings.LIQUID_PURE_DYE);

		
		FluidRegistry.registerFluid(fluidRedDye);
		FluidRegistry.registerFluid(fluidYellowDye);
		FluidRegistry.registerFluid(fluidBlueDye);
		FluidRegistry.registerFluid(fluidWhiteDye);
		FluidRegistry.registerFluid(fluidPureDye);

	}
}
