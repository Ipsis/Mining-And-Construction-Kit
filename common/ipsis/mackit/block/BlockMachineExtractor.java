package ipsis.mackit.block;

import ipsis.mackit.MacKit;
import ipsis.mackit.lib.GuiIds;
import ipsis.mackit.lib.Strings;
import ipsis.mackit.tileentity.TileMachineExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMachineExtractor extends BlockMKMachine {

	public BlockMachineExtractor(int id) {
		super(id, Strings.BLOCK_MACHINE_EXTRACTOR);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileMachineExtractor) {
				player.openGui(MacKit.instance, GuiIds.GUI_MACHINE_EXTRACTOR, world, x, y, z);
			}
		}
		
		return true;
	}
	
	 @Override
	public TileEntity createTileEntity(World world, int metadata) {
		 return new TileMachineExtractor();
	}
}
