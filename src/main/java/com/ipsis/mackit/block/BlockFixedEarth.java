package com.ipsis.mackit.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.lib.Strings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFixedEarth extends BlockMK {
	
	public BlockFixedEarth(int id) {
		
		super(id, Material.grass);
		super.setCreativeTab(MacKit.tabsMacKit);
		setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.FIXED_EARTH_NAME);		
	}
	
	@SideOnly(Side.CLIENT)
	private Icon icons[];
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegister) {

		icons = new Icon[Strings.FIXED_EARTH_NAMES.length];
		
		for (int i = 0; i < Strings.FIXED_EARTH_NAMES.length; i++) {
			icons[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.FIXED_EARTH_NAMES[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		
		metadata = MathHelper.clamp_int(metadata, 0, Strings.FIXED_EARTH_NAMES.length - 1);
		return icons[metadata];
	}
	
	@Override
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {

		for (int i = 0; i < Strings.FIXED_EARTH_NAMES.length; i++) {
			list.add(new ItemStack(id, 1, i));
		}
	}
	
	@Override
	public int damageDropped(int metadata) {

		return metadata;
	}

}
