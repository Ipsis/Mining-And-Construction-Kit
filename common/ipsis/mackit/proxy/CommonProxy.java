package ipsis.mackit.proxy;

import ipsis.mackit.client.gui.inventory.GuiDyeTransposer;
import ipsis.mackit.client.gui.inventory.GuiMachineBBBuilder;
import ipsis.mackit.client.gui.inventory.GuiMachineExtractor;
import ipsis.mackit.client.gui.inventory.GuiMachineStamper;
import ipsis.mackit.client.gui.inventory.GuiWaterFillerMachine;
import ipsis.mackit.inventory.ContainerDyeTransposer;
import ipsis.mackit.inventory.ContainerMachineBBBuilder;
import ipsis.mackit.inventory.ContainerMachineExtractor;
import ipsis.mackit.inventory.ContainerMachineStamper;
import ipsis.mackit.inventory.ContainerWaterFillerMachine;
import ipsis.mackit.lib.GuiIds;
import ipsis.mackit.lib.Strings;
import ipsis.mackit.tileentity.TileDyeTransposer;
import ipsis.mackit.tileentity.TileMachineBBBuilder;
import ipsis.mackit.tileentity.TileMachineExtractor;
import ipsis.mackit.tileentity.TileMachineStamper;
import ipsis.mackit.tileentity.TileMachineWaterFiller;
import ipsis.mackit.tileentity.TileWaterFiller;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy implements IGuiHandler {
	
	public void registerTileEntities() {
		
		GameRegistry.registerTileEntity(TileWaterFiller.class, Strings.TE_WATER_FILLER);
		GameRegistry.registerTileEntity(TileMachineWaterFiller.class, Strings.TE_WATER_FILLER_MACHINE);
		GameRegistry.registerTileEntity(TileDyeTransposer.class, Strings.TE_DYE_TRANSPOSER);
		
		GameRegistry.registerTileEntity(TileMachineExtractor.class, Strings.TE_MACHINE_EXTRACTOR);
		GameRegistry.registerTileEntity(TileMachineStamper.class, Strings.TE_MACHINE_STAMPER);
		GameRegistry.registerTileEntity(TileMachineBBBuilder.class, Strings.TE_MACHINE_BBBUILDER);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GuiIds.WATER_FILLER_MACHINE) {
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileMachineWaterFiller) {
				return new ContainerWaterFillerMachine(player.inventory, (TileMachineWaterFiller)te);
			}
		} else if (ID == GuiIds.DYE_TRANSPOSER) {
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileDyeTransposer) {
				return new ContainerDyeTransposer(player.inventory, (TileDyeTransposer)te);
			}
		}
		
		else {
			TileEntity te;
			switch (ID) {
				case GuiIds.GUI_MACHINE_EXTRACTOR:
					te = world.getBlockTileEntity(x,  y,  z);
					if (te != null && te instanceof TileMachineExtractor) {
						return new ContainerMachineExtractor(player.inventory, (TileMachineExtractor)te);
					}
				break;
				case  GuiIds.GUI_MACHINE_MIXER:
				break;
				case GuiIds.GUI_MACHINE_STAMPER:
					te = world.getBlockTileEntity(x,  y,  z);
					if (te != null && te instanceof TileMachineStamper) {
						return new ContainerMachineStamper(player.inventory, (TileMachineStamper)te);
					}
				break;
				case GuiIds.GUI_MACHINE_APPLICATOR:
				break;
				case GuiIds.GUI_MAHCINE_BBBUILDER:
					te = world.getBlockTileEntity(x,  y,  z);
					if (te != null && te instanceof TileMachineBBBuilder) {
						return new ContainerMachineBBBuilder(player.inventory, (TileMachineBBBuilder)te);
					}
				break;
				default:
				break;
				}
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		if (ID == GuiIds.WATER_FILLER_MACHINE) {
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileMachineWaterFiller) {
				return new GuiWaterFillerMachine(player.inventory, (TileMachineWaterFiller)te);
			}
		} else if (ID == GuiIds.DYE_TRANSPOSER) {
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileDyeTransposer) {
				return new GuiDyeTransposer(player.inventory, (TileDyeTransposer)te);
			}
		}
		else {
			TileEntity te;
			switch (ID) {
				case GuiIds.GUI_MACHINE_EXTRACTOR:
					te = world.getBlockTileEntity(x,  y,  z);
					if (te != null && te instanceof TileMachineExtractor) {
						return new GuiMachineExtractor(player.inventory, (TileMachineExtractor)te);
					}
				break;
				case  GuiIds.GUI_MACHINE_MIXER:
				break;
				case GuiIds.GUI_MACHINE_STAMPER:
					te = world.getBlockTileEntity(x,  y,  z);
					if (te != null && te instanceof TileMachineStamper) {
						return new GuiMachineStamper(player.inventory, (TileMachineStamper)te);
					}
				break;
				case GuiIds.GUI_MACHINE_APPLICATOR:
				break;
				case GuiIds.GUI_MAHCINE_BBBUILDER:
					te = world.getBlockTileEntity(x,  y,  z);
					if (te != null && te instanceof TileMachineBBBuilder) {
						return new GuiMachineBBBuilder(player.inventory, (TileMachineBBBuilder)te);
					}
				break;
				default:
				break;
				}
		}
		
		return null;
	}
	

}
