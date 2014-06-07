package com.ipsis.mackit.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.ipsis.cofhlib.util.BlockHelper;
import com.ipsis.cofhlib.util.EntityHelper;
import com.ipsis.mackit.creativetab.CreativeTab;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * A block with different textures on different
 * sides and where the facing information
 * is part of a tileentity.
 * 
 */
public class BlockFaced extends BlockContainer {

	private String baseName;
	
	private static final String[] defaultIconSides = {
		"_bottom", "_top", "_front", "_back", "_left", "_right"
	};
	
	/* 0 - bottom, 1 - top, 2 - front, 3 - back, 4 - left, 5 - right */
	private String[] iconSides;
	
	public BlockFaced(Material m, String name) {
		
		this(m, name, null);
	}
	
	public BlockFaced(Material m, String name, String[] sides) {
		
		super(m);
		this.setCreativeTab(CreativeTab.MK_TAB);
		this.setBlockName(name);
		
		/**
		 * The iconSides determine which textures that this block provides
		 */

		if (sides == null || sides.length != 6) {
			this.iconSides = defaultIconSides;
		} else {				
			this.iconSides = new String[6];
			System.arraycopy(sides, 0, this.iconSides, 0, sides.length);
		}
	}
	
	@Override
	public Block setBlockName(String name) {

		baseName = name;
		name = Reference.MOD_ID + ":" + name;
		return super.setBlockName(name);
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon icons[];
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {

		icons = new IIcon[6];
		
		for (int i = 0; i < iconSides.length; i++) {
			if (iconSides[i].equals("") || iconSides[i].startsWith("_"))
				icons[i] = iconRegister.registerIcon(Reference.MOD_ID + ":" + baseName + iconSides[i]);
			else
				icons[i] = iconRegister.registerIcon(Reference.MOD_ID + ":" + iconSides[i]);
		}	
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon getIconFromSide(ForgeDirection f, int side) {
		
		ForgeDirection c = ForgeDirection.getOrientation(side);
		
		if (c == f)
			return icons[2];
		else if (c == f.getOpposite())
			return icons[3];
		else {
			if (side == BlockHelper.getLeftSide(f.ordinal()))
				return icons[4];
			else if (side == BlockHelper.getRightSide(f.ordinal()))
				return icons[5];
		}
		
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata) {
		
		/**
		 * As we override the IBlockAccess version this will only be called for the toolbar
		 * Is is oriented as facing south
		 */
		
		if (side == 0 || side == 1)
			return icons[side];
		
		ForgeDirection f = ForgeDirection.SOUTH;
		return getIconFromSide(f, side);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {

		/* top or bottom */
		if (side == 0 || side == 1)
			return icons[side];
		
		TileEntity te = iblockaccess.getTileEntity(x, y, z);
		if (te != null && te instanceof IFacing) {

			ForgeDirection f = ((IFacing)te).getFacing();			
			return getIconFromSide(f, side);
		}
		
		return null;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {

		if (world.isRemote)
			return;
		
		ForgeDirection d = EntityHelper.getEntityFacingForgeDirection(entityLiving);
		
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof IFacing)
			((IFacing)te).setFacing(d);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int meta) {

		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof IInventory) {

			IInventory inventory = (IInventory) te;

			for (int i = 0; i < inventory.getSizeInventory(); i++) {

				/* Vswe tutorial break block code */
				ItemStack stack = inventory.getStackInSlotOnClosing(i);
				if (stack != null) {
					float spawnX = x + world.rand.nextFloat();
					float spawnY = y + world.rand.nextFloat();
					float spawnZ = z + world.rand.nextFloat();
					
					EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, stack);
					
					float mult = 0.05F;
					
					droppedItem.motionX = (-0.5F + world.rand.nextFloat()) * mult;
					droppedItem.motionY = (4 + world.rand.nextFloat()) * mult;
					droppedItem.motionZ = (-0.5F + world.rand.nextFloat()) * mult;
					
					world.spawnEntityInWorld(droppedItem);
				}
			}
		}

		super.breakBlock(world, x, y, z, b, meta);
	}
	

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return null;
	}
	
	
}
