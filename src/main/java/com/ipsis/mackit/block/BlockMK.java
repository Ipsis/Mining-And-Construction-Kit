package com.ipsis.mackit.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import com.ipsis.mackit.creativetab.CreativeTab;
import com.ipsis.mackit.reference.Reference;

/**
 * Single texture block
 * Texture name is based on the block name
 */
public class BlockMK extends Block {
	
	private String iconName;
	
	public BlockMK(Material material) {
			
		super(material);
		this.setCreativeTab(CreativeTab.MK_TAB);
	}
	
	@Override
	public Block setBlockName(String name) {

		iconName = name;
		name = Reference.MOD_ID + ":" + name;
		return super.setBlockName(name);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		blockIcon = iconRegister.registerIcon(Reference.MOD_ID + ":" + iconName);
	}

}
