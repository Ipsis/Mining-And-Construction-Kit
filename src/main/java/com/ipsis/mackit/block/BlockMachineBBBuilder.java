package com.ipsis.mackit.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.lib.Strings;
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
	public TileEntity createNewTileEntity(World world) {
		 return new TileMachineBBBuilder();
	}

}
