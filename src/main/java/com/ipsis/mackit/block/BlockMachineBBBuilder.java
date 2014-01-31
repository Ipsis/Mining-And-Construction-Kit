package com.ipsis.mackit.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.helper.Helper;
import com.ipsis.mackit.helper.LogHelper;
import com.ipsis.mackit.lib.GuiIds;
import com.ipsis.mackit.lib.Strings;
import com.ipsis.mackit.tileentity.TileMachineBBBuilder;
import com.ipsis.mackit.tileentity.TileMachinePowered;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachineBBBuilder extends BlockContainer {
	
	public BlockMachineBBBuilder(int id) {
		
		super(id, Material.iron);
		super.setCreativeTab(MacKit.tabsMacKit);
		setHardness(0.6F);
		setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.MACHINE_BBBUILDER_NAME);
	}
	
	@SideOnly(Side.CLIENT)
	private Icon sideIcon;
	@SideOnly(Side.CLIENT)
	private Icon topIcon;
	@SideOnly(Side.CLIENT)
	private Icon bottomIcon;
	@SideOnly(Side.CLIENT)
	private Icon frontIcon;
	
	@Override
	public void registerIcons(IconRegister iconRegister) {

		sideIcon = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.MACHINE_BBBUILDER_NAME + "_side");
		topIcon = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.MACHINE_BBBUILDER_NAME + "_top");
		bottomIcon = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.MACHINE_BBBUILDER_NAME + "_bottom");
		frontIcon = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.MACHINE_BBBUILDER_NAME + "_front");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		
		if (side == 0)
			return bottomIcon;
		else if (side == 1)
			return topIcon;
		else if (side == 3) /* toolbar facing */
			return frontIcon;
		else
			return sideIcon;
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess iBlockAccess, int x, int y, int z, int side) {
		
		int facing = 2;
		TileEntity te = iBlockAccess.getBlockTileEntity(x, y, z);
		if (te instanceof TileMachinePowered)
			facing = ((TileMachinePowered) te).getFacing().ordinal();
		
		if (side == 0)
			return bottomIcon;
		else if (side == 1)
			return topIcon;
		else if (side == facing)
			return frontIcon;
		else
			return sideIcon;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if (player.isSneaking())
			return false;
		
		if (!world.isRemote) {
			TileEntity tileMachineBBBuilder = world.getBlockTileEntity(x, y, z);
			if (tileMachineBBBuilder != null && tileMachineBBBuilder instanceof TileMachineBBBuilder) {
				player.openGui(MacKit.instance, GuiIds.BBBUILDER, world, x, y, z);
			}
		}
		
		return true;
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
	public TileEntity createNewTileEntity(World world) {
		
		 return new TileMachineBBBuilder();
	}

}
