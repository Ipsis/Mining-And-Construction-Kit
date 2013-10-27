package ipsis.mackit.block;

import ipsis.mackit.lib.Reference;
import ipsis.mackit.lib.Strings;
import ipsis.mackit.tileentity.TileWaterFiller;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;



public class BlockWaterFiller extends BlockContainer {
	
	public BlockWaterFiller(int id) {
		super(id, Material.iron);
		super.setCreativeTab(CreativeTabs.tabMisc);
		super.setUnlocalizedName(Strings.TE_WATER_FILLER);
	}
	
	public enum WaterFillerModes {
		DISABLED, CIRCLE, PYRAMID, COL7, COL11
	}
	
	@SideOnly(Side.CLIENT)
	private Icon sideIcon;
	@SideOnly(Side.CLIENT)
	private Icon botIcon;
	@SideOnly(Side.CLIENT)
	private Icon[] topIcons;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		sideIcon = register.registerIcon(Reference.MOD_ID + ":" + Strings.BLOCK_WATER_FILLER);
		botIcon = register.registerIcon(Reference.MOD_ID + ":" + Strings.BLOCK_WATER_FILLER_BOT);
		
		topIcons = new Icon[Strings.BLOCK_WATER_FILLER_NAMES.length];
		for (int i = 0; i < topIcons.length; i++) {
			topIcons[i] = register.registerIcon(Reference.MOD_ID + ":" + Strings.BLOCK_WATER_FILLER_NAMES[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (side == 0) {
			return botIcon;
		} else if (side == 1) {
			if (meta >= WaterFillerModes.DISABLED.ordinal() && meta <= WaterFillerModes.COL11.ordinal()) {
				return topIcons[meta];
			} else {
				return topIcons[0];
			}
		} else {
			return sideIcon;
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileWaterFiller();
	}
	
	private int nextMode(int meta) {
		
		meta++;
		if (meta > WaterFillerModes.COL11.ordinal()) {
			meta = WaterFillerModes.DISABLED.ordinal();
		}
		
		return meta;
	}
	
	private boolean isDisabled(int meta) {
		return meta == WaterFillerModes.DISABLED.ordinal();
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z);
		/*
		int id = world.getBlockId(x, y, z);
		if (id == Block.waterMoving.blockID || id == Block.waterStill.blockID) {
			return true;
		}
		
		return false; */
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		/* no mode change while it is running */
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileWaterFiller) {	
			
			if (((TileWaterFiller)te).getRunning()) {
				return true;
			}
		}

		
		/* cycle through the different modes */
		int meta = world.getBlockMetadata(x,  y,  z);
		meta = nextMode(meta);		

		if (!world.isRemote) {
			world.setBlockMetadataWithNotify(x, y, z, meta, 3);
		}
		
		return true;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		int meta = world.getBlockMetadata(x, y, z);
		
		if (isDisabled(meta)) {
			return;
		}
		
		if (!world.isRemote && world.isBlockIndirectlyGettingPowered(x, y, z) && !isDisabled(meta)) {
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileWaterFiller) {				
				TileWaterFiller wf = (TileWaterFiller)te;		
				wf.setRunning();
			}
			
		}
	}

}
