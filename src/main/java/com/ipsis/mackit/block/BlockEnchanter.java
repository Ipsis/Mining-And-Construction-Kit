package com.ipsis.mackit.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.lib.Strings;

public class BlockEnchanter extends Block {

	public BlockEnchanter(int id)
	{
		super(id, Material.iron);
		setHardness(0.6F);
		setCreativeTab(MacKit.tabsMacKit);
		setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.ENCHANTER_NAME);
	}
}
