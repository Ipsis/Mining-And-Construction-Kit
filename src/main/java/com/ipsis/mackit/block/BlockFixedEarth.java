package com.ipsis.mackit.block;

import com.ipsis.mackit.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFixedEarth extends BlockMK {
	
	public BlockFixedEarth(Material material, String blockName) {
		
		super(material);		
		this.setBlockName(blockName);
        this.setHardness(0.5F);
	}

    @Override
    public int quantityDropped(Random rand) {

        return 0;
    }
}
