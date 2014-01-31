package com.ipsis.mackit.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.helper.Helper;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.lib.Strings;
import com.ipsis.mackit.tileentity.TileBeaverBlock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/* 
 * Yes it has Block in its name.
 * Yes it is intentional!
 * 
 * Metadata is used for
 * 
 * Orientation 2 bits (0x3)
 * Mode 2 bits (0xC)
 */
public class BlockBeaverBlock extends BlockContainer {

	public BlockBeaverBlock(int id) {
		
		super(id, Material.iron);
		super.setCreativeTab(MacKit.tabsMacKit);
		setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.BEAVER_BLOCK_NAME);
	}
	
	public enum Mode {
		
		SURFACE(0, "surface", "Surface Mode"),
		DAM(1, "dam", "Dam Mode"),
		COLUMN(2, "column", "Column Mode");
		
		private final int mdValue;
		private final String texture;
		private final String displayName;
		public static final Mode[] VALID_MODES = { SURFACE, DAM, COLUMN };
		
		private Mode(int mdValue, String texture, String displayName) {
			
			this.mdValue = mdValue;
			this.texture = texture;
			this.displayName = displayName;
		}
		
		public String getTexture() {
			
			return texture;
		}
		
		public String getDisplayName() {
			
			return displayName;
		}
		
		public int getMdValue() {
			
			return mdValue;
		}
		
		/**
		 * Get the next mode 
		 * @param m the current mode
		 * @return the next mode 
		 */
		public static Mode getNextMode(Mode m) {
			
			if (m == SURFACE)
				return Mode.DAM;
			else if (m == DAM)
				return COLUMN;
			else
				return SURFACE;
		}
		
		/**
		 * Get the mode for the metadata value
		 * @param mdValue the metadata value
		 * @return the mode
		 */
		public static Mode getMode(int mdValue) {
			
			if (mdValue >= 0 && mdValue < VALID_MODES.length)
				return VALID_MODES[mdValue];
			else
				return VALID_MODES[0];
		}
	}
	
	/* Metadata manipulation */
	
	public static Mode getMetadataMode(int metadata) {
		
		int v = (metadata & 0xC) >> 2;
		return Mode.getMode(v);
	}
	
	public static int setMetadataMode(int metadata, Mode mode) {
		
		metadata &= ~0xC;
		int v = mode.getMdValue();
		metadata |= ((v & 0x3) << 2);
		return metadata;
	}
	
	/** Set the orientation in the metadata */
	public static int setMetadataOrientation(int metadata, ForgeDirection dir) {
		
		int v = 0;
		if (dir == ForgeDirection.NORTH)
			v = 0;
		else if (dir == ForgeDirection.SOUTH)
			v = 1;
		else if (dir == ForgeDirection.WEST)
			v = 2;
		else if (dir == ForgeDirection.EAST)
			v = 3;
		
		metadata &= 0x3;
		metadata |= (v & 0x3);
		return metadata;
	}
	
	public static ForgeDirection getMetadataOrientation(int metadata) {
		
		int v = (metadata & 0x3);
		return ForgeDirection.getOrientation(v + 2);
	}
	

	/** Placement and interaction */	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
	
		if (!world.isRemote) {
			ForgeDirection orientation = Helper.getFacing(entityLiving).getOpposite();
			int metadata = 0;
			metadata = setMetadataOrientation(metadata, orientation);
			metadata = setMetadataMode(metadata, Mode.SURFACE);
			world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
		}
	}
		
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if (player.isSneaking())
			return false;
		
		if (!world.isRemote){
			
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileBeaverBlock) {
				TileBeaverBlock bb = (TileBeaverBlock) te;
				
				if (!bb.getIsRunning()) {
					int metadata = world.getBlockMetadata(x, y, z);
					Mode m = getMetadataMode(metadata);
					m = Mode.getNextMode(m);
					metadata = setMetadataMode(metadata, m);
					
					player.addChatMessage(m.getDisplayName());			
					world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
				}
			}
		}
		
		return true;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {

		if (!world.isRemote && world.isBlockIndirectlyGettingPowered(x, y, z)) {
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileBeaverBlock) {
				TileBeaverBlock bb = (TileBeaverBlock)te;
				bb.setIsRunning();
			}			
		}
	}
	
	@SideOnly(Side.CLIENT)
	private Icon topIcon;
	private Icon bottomIcon;
	private Icon sideIcon;
	private Icon[] modeIcons;
	
	private String[] modeNames = new String[] { "surface", "dam", "column" };
	
	@Override
	public void registerIcons(IconRegister iconRegister) {

		sideIcon = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.BEAVER_BLOCK_NAME + "_side");
		topIcon = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.BEAVER_BLOCK_NAME + "_top");
		bottomIcon = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.BEAVER_BLOCK_NAME + "_bottom");
		
		modeIcons = new Icon[Mode.VALID_MODES.length];
		for (int i = 0; i < Mode.VALID_MODES.length; i++)
			modeIcons[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.BEAVER_BLOCK_NAME + "_" + Mode.VALID_MODES[i].texture);
		
	}
	
	@Override
	public Icon getIcon(int side, int metadata) {
		
		Mode m = getMetadataMode(metadata);
		ForgeDirection forgeDir = getMetadataOrientation(metadata);
		ForgeDirection forgeSide = ForgeDirection.getOrientation(side);
		
		if (forgeSide == ForgeDirection.UP)
			return topIcon;
		else if (forgeSide == ForgeDirection.DOWN)
			return bottomIcon;
		else if (forgeSide == forgeDir)
			return modeIcons[m.getMdValue()];
		else
			return sideIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		
		return new TileBeaverBlock();
	}
}
