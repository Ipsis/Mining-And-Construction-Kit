package com.ipsis.mackit.block;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.helper.Helper;
import com.ipsis.mackit.lib.Strings;
import com.ipsis.mackit.tileentity.TileMachineBBBuilder;
import com.ipsis.mackit.tileentity.TileMachinePowered;
import com.ipsis.mackit.tileentity.TileMachineSqueezer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachine extends BlockContainer {
	
	public BlockMachine(int id) {
		
		super(id, Material.iron);
		super.setCreativeTab(MacKit.tabsMacKit);
		setHardness(0.6F);
		setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.MACHINE_BLOCK_NAME);
	}
	
	@Override
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		
		for (int i = 0; i < MachineTypes.values().length; i++)
			list.add(new ItemStack(id, 1, i));
	}
	
	@Override
	public int damageDropped(int metadata) {

		return metadata;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
		
		if (!world.isRemote) {
			ForgeDirection orientation = Helper.getFacing(entityLiving).getOpposite();
			
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileMachinePowered) {
				((TileMachinePowered)te).setFacing(orientation);
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if (player.isSneaking())
			return false;
		
		if (!world.isRemote) {
			
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileMachinePowered) {
				int id = ((TileMachinePowered)te).getGuiID();
				if (id != -1)
					player.openGui(MacKit.instance, id, world, x, y, z);
			}
		}
		
		return true;
	}
	
	/*
	 * All machines have the same textures apart from their face
	 */
	@SideOnly(Side.CLIENT)
	private Icon sideIcon;
	@SideOnly(Side.CLIENT)
	private Icon topIcon;
	@SideOnly(Side.CLIENT)
	private Icon bottomIcon;
	@SideOnly(Side.CLIENT)
	private Icon frontIconsActive[];
	@SideOnly(Side.CLIENT)
	private Icon frontIconsInactive[];
	
	@Override
	public void registerIcons(IconRegister iconRegister) {
		
		sideIcon = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.MACHINE_BLOCK_NAME + "_side");
		topIcon = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.MACHINE_BLOCK_NAME + "_top");
		bottomIcon = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.MACHINE_BLOCK_NAME + "_bottom");
		
		/* Each machine has its own face */
		frontIconsActive = new Icon[Strings.MACHINE_NAMES.length];
		frontIconsInactive = new Icon[Strings.MACHINE_NAMES.length];
		
		for (int i = 0; i < Strings.MACHINE_NAMES.length; i++) {
			frontIconsActive[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.MACHINE_NAMES[i] + "_active");
			frontIconsInactive[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.MACHINE_NAMES[i] + "_inactive");
		}
		
	}
	
	@Override
	public Icon getIcon(int side, int metadata) {

		/**
		 * As far as I can work out, this will only be called for inventory icons as we have getBlockTexture overidden.
		 * Side 3 is the standard side for you front texture in this situation.
		 */
		if (side == 0) {
			return bottomIcon;
		} else if (side == 1) {
			return topIcon;
		} else if (side != 3) {
			return sideIcon;			
		} else {
			metadata = MathHelper.clamp_int(metadata, 0, Strings.MACHINE_NAMES.length - 1);
			return frontIconsInactive[metadata];
		}
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side) {
		
		int metadata = iblockaccess.getBlockMetadata(x, y, z);		
		metadata = MathHelper.clamp_int(metadata, 0, Strings.MACHINE_NAMES.length - 1);
		
		if (side == 0) {
			return bottomIcon;
		} else if (side == 1) {
			return topIcon;
		} else {

			
			TileEntity te = iblockaccess.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileMachinePowered) {
				TileMachinePowered tep = (TileMachinePowered)te;
				if (side == 0) {
					return bottomIcon;
				} else if (side == 1) {
					return topIcon;
				} else  {
					ForgeDirection curr = ForgeDirection.getOrientation(side);
					if (curr == tep.getFacing())
						return tep.getIsActive() ? frontIconsActive[metadata] : frontIconsInactive[metadata];
					else
						return sideIcon;
				}
			} else {
				return frontIconsInactive[metadata];
			}
		}
	}

	public static enum MachineTypes {
		BBBUILDER, SQUEEZER, MIXER, PAINTER, STAMPER;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
	
		if (metadata < 0 || metadata >= MachineTypes.values().length)
			return null;
		
		switch (metadata) {
		case 0:
			return new TileMachineBBBuilder();
		case 1:
			return new TileMachineSqueezer();
/*
		case 2:
			return new TileMachineMixer();
			break;
		case 3:
			return new TileMachinePainter();
			break;
		case 4:
			return new TileMachineStamper();
			break; */
		default:
			return null;
		}
	}

}
