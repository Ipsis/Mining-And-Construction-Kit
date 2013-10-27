package ipsis.mackit.proxy;

import ipsis.mackit.lib.Strings;
import ipsis.mackit.tileentity.TileWaterFiller;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public void registerTileEntities() {
		
		GameRegistry.registerTileEntity(TileWaterFiller.class, Strings.TE_WATER_FILLER);
	}
}
