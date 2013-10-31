package ipsis.mackit.fluid;

import ipsis.mackit.lib.Strings;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {
	
	public static Fluid fluidRedDye;
	public static Fluid fluidYellowDye;
	public static Fluid fluidGreenDye;
	public static Fluid fluidBlueDye;
	public static Fluid fluidWhiteDye;
	public static Fluid fluidBlackDye;
	public static Fluid fluidBrownDye;
	
	public static void init() {
		
		fluidRedDye = new FluidDye(Strings.LIQUID_RED_DYE);
		fluidYellowDye = new FluidDye(Strings.LIQUID_YELLOW_DYE);
		fluidGreenDye = new FluidDye(Strings.LIQUID_GREEN_DYE);
		fluidBlueDye = new FluidDye(Strings.LIQUID_BLUE_DYE);
		fluidWhiteDye = new FluidDye(Strings.LIQUID_WHITE_DYE);
		fluidBlackDye = new FluidDye(Strings.LIQUID_BLACK_DYE);
		fluidBrownDye = new FluidDye(Strings.LIQUID_BROWN_DYE);
		
		FluidRegistry.registerFluid(fluidRedDye);
		FluidRegistry.registerFluid(fluidYellowDye);
		FluidRegistry.registerFluid(fluidGreenDye);
		FluidRegistry.registerFluid(fluidBlueDye);
		FluidRegistry.registerFluid(fluidWhiteDye);
		FluidRegistry.registerFluid(fluidBlackDye);
		FluidRegistry.registerFluid(fluidBrownDye);
	}
}
