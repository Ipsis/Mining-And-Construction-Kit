package ipsis.mackit.block;

import ipsis.mackit.MacKit;
import ipsis.mackit.lib.GuiIds;
import ipsis.mackit.lib.Strings;
import ipsis.mackit.tileentity.TileMachineStamper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMachineStamper extends BlockMKMachine {
	
	public BlockMachineStamper(int id) {
		super(id, Strings.BLOCK_MACHINE_STAMPER);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileMachineStamper) {
				player.openGui(MacKit.instance, GuiIds.GUI_MACHINE_STAMPER, world, x, y, z);
			}
		}
		
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileMachineStamper();
	}

}
