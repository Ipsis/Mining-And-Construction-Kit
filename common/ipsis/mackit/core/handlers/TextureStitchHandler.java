package ipsis.mackit.core.handlers;

import ipsis.mackit.block.ModBlocks;
import ipsis.mackit.fluid.ModFluids;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class TextureStitchHandler {

	@ForgeSubscribe
	public void postStitch(TextureStitchEvent.Post event)
	{
		ModFluids.fluidRedDye.setIcons(ModBlocks.redDye.getIcon(0, 0), ModBlocks.redDye.getIcon(2, 0));
		ModFluids.fluidYellowDye.setIcons(ModBlocks.yellowDye.getIcon(0, 0), ModBlocks.yellowDye.getIcon(2, 0));
		ModFluids.fluidBlueDye.setIcons(ModBlocks.blueDye.getIcon(0, 0), ModBlocks.blueDye.getIcon(2, 0));
		ModFluids.fluidWhiteDye.setIcons(ModBlocks.whiteDye.getIcon(0, 0), ModBlocks.whiteDye.getIcon(2, 0));
		ModFluids.fluidPureDye.setIcons(ModBlocks.pureDye.getIcon(0, 0), ModBlocks.pureDye.getIcon(2, 0));
	}
	
}
