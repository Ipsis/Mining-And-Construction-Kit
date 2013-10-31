package ipsis.mackit.block;

import ipsis.mackit.MacKit;
import ipsis.mackit.lib.Reference;
import ipsis.mackit.lib.Strings;
import ipsis.mackit.tileentity.TileWaterFiller;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWaterFiller extends BlockContainer {
	
	public BlockWaterFiller(int id) {
		super(id, Material.iron);
		setCreativeTab(MacKit.tabsMacKit);
		setUnlocalizedName(Strings.BLOCK_WATER_FILLER);
	}
	
	/**
	 * metadata will hold
	 * top 2 bits will be the mode
	 * bottom 2 bits will be the facing direction
	 * 
	 * facing direction is offset by 2 as we dont care about up/down
	 */
	
	public final static int MODE_MASK = 0xC;
	public final static int MODE_SHIFT = 2;
	public final static int FACING_MASK = 0x3;
	

	
	@SideOnly(Side.CLIENT)
	private Icon sideIcon;
	@SideOnly(Side.CLIENT)
	private Icon topIcon;
	@SideOnly(Side.CLIENT)
	private Icon modeIcons[];

	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		sideIcon = register.registerIcon(Reference.MOD_ID + ":" + Strings.BLOCK_WATER_FILLER + "_side");
		topIcon = register.registerIcon(Reference.MOD_ID + ":" + Strings.BLOCK_WATER_FILLER);
		
		modeIcons = new Icon[Strings.BLOCK_WATER_FILLER_MODES.length];
		for (int i = 0; i < modeIcons.length; i++) {
			modeIcons[i] = register.registerIcon(Reference.MOD_ID + ":" + Strings.BLOCK_WATER_FILLER_MODES[i]);
		}

	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		
		/* top or bottom */
		if (side == 0 || side == 1) {
			return topIcon;
		}
		
		int mode = (meta & MODE_MASK) >> MODE_SHIFT;
		int facing = (meta & FACING_MASK) + 2;
		
		return (side == facing ? modeIcons[mode] : sideIcon);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileWaterFiller();
	}
	

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		
		int id = world.getBlockId(x, y, z);
		if (id == Block.waterMoving.blockID || id == Block.waterStill.blockID) {
			return true;
		}
		
		return false;
	}
	
	private ForgeDirection getForgeDir(int x)
	{
		if (x == 0) {
			return ForgeDirection.SOUTH;
		} else if (x == 1) {
			return ForgeDirection.WEST;
		} else if (x == 2) {
			return ForgeDirection.NORTH;
		} else if (x == 3) {
			return ForgeDirection.EAST;
		} else {
			return ForgeDirection.SOUTH;
		}			
	}
	
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack itemStack) {
		
		super.onBlockPlacedBy(world, x, y, z, entityliving, itemStack);
		
		/* init to facing player with circle mode */
		if (!world.isRemote) {
		
			/* this calculates the minecraft world direction N/S/E/W */
			int l = MathHelper.floor_double((double)(entityliving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			
			ForgeDirection orientation = getForgeDir(l);
			orientation = orientation.getOpposite();
			
			int newMeta = 0;
			newMeta = (orientation.ordinal() - 2) & FACING_MASK;
			world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);
		}
			
	} 
	
	private int setNextMode(int meta) {
		
		int mode = (meta & MODE_MASK) >> MODE_SHIFT;
		meta &= ~MODE_MASK;
		meta |= (++mode & 0x03) << MODE_SHIFT;
		return meta;
	}
	
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if (!world.isRemote) {
			/* no mode change while it is running */
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (!(te instanceof TileWaterFiller)) {
				return false;
			}
	
			if (((TileWaterFiller)te).getRunning())
				return true;
			
			int newMeta = world.getBlockMetadata(x, y, z);
			newMeta = setNextMode(newMeta);
			
			world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);		
		}
		
		return true;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
	
		if (!world.isRemote && world.isBlockIndirectlyGettingPowered(x, y, z)) {
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileWaterFiller) {				
				TileWaterFiller wf = (TileWaterFiller)te;		
				wf.setRunning();
			}
			
		}
	}

}
