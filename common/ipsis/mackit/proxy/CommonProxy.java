package ipsis.mackit.proxy;

import ipsis.mackit.client.gui.inventory.GuiWaterFillerMachine;
import ipsis.mackit.inventory.ContainerWaterFillerMachine;
import ipsis.mackit.lib.GuiIds;
import ipsis.mackit.lib.Strings;
import ipsis.mackit.tileentity.TileWaterFiller;
import ipsis.mackit.tileentity.TileWaterFillerMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy implements IGuiHandler {
	
	public void registerTileEntities() {
		
		GameRegistry.registerTileEntity(TileWaterFiller.class, Strings.TE_WATER_FILLER);
		GameRegistry.registerTileEntity(TileWaterFillerMachine.class, Strings.TE_WATER_FILLER_MACHINE);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GuiIds.WATER_FILLER_MACHINE) {
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileWaterFillerMachine) {
				return new ContainerWaterFillerMachine(player.inventory, (TileWaterFillerMachine)te);
			}
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		if (ID == GuiIds.WATER_FILLER_MACHINE) {
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileWaterFillerMachine) {
				return new GuiWaterFillerMachine(player.inventory, (TileWaterFillerMachine)te);
			}
		}
		
		return null;
	}
}
