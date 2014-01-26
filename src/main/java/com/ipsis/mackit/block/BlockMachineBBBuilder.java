package com.ipsis.mackit.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.lib.GuiIds;
import com.ipsis.mackit.lib.Strings;
import com.ipsis.mackit.tileentity.TileEnchanter;
import com.ipsis.mackit.tileentity.TileMachineBBBuilder;

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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if (player.isSneaking())
		{
			return false;
		}
		
		if (!world.isRemote)
		{
			TileEntity tileMachineBBBuilder = world.getBlockTileEntity(x, y, z);
			if (tileMachineBBBuilder != null && tileMachineBBBuilder instanceof TileMachineBBBuilder)
			{
				player.openGui(MacKit.instance, GuiIds.BBBUILDER, world, x, y, z);
			}
		}
		
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		 return new TileMachineBBBuilder();
	}

}
