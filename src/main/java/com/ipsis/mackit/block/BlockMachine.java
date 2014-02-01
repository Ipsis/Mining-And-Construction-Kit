package com.ipsis.mackit.block;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.lib.Strings;

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
		
		for (int i = 0; i < Types.values().length; i++)
			list.add(new ItemStack(id, 1, i));
	}
	
	@Override
	public int damageDropped(int metadata) {

		return metadata;
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

	public static enum Types {
		BBBUILDER, SQUEEZER, MIXER, PAINTER, STAMPER;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return null;
	}

}
