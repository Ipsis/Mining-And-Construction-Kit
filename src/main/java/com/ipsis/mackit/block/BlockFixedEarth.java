package com.ipsis.mackit.block;

import com.ipsis.mackit.reference.Reference;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Fixed earth cannot be collected
 * Destroys itself when broken.
 */
public class BlockFixedEarth extends BlockMK {
	
	public BlockFixedEarth(Material material, String blockName) {
		
		super(material);		
		this.setBlockName(blockName);
	}
	
}
