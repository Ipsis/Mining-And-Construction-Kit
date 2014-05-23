package com.ipsis.mackit.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import com.ipsis.mackit.creativetab.CreativeTab;
import com.ipsis.mackit.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFluidDye extends BlockFluidClassic {

	protected String iconName;
	
	public BlockFluidDye(Fluid f, String name) {
		
		super(f, Material.lava);
		setBlockName(name);
		this.setCreativeTab(CreativeTab.MK_TAB);
	}
	
	@Override
	public Block setBlockName(String name) {

		iconName = name;
		name = Reference.MOD_ID + ":" + name;
		return super.setBlockName(name);
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon flowIcon;
	@SideOnly(Side.CLIENT)
	private IIcon stillIcon;
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {

		flowIcon = iconRegister.registerIcon(Reference.MOD_ID + ":fluid/Fluid_" + iconName + "_Flow");
		stillIcon = iconRegister.registerIcon(Reference.MOD_ID + ":fluid/Fluid_" + iconName + "_Still");
	}
	
	@Override
	public IIcon getIcon(int side, int mets) {

		if (side == 0 || side == 1)
			return stillIcon;
		
		return flowIcon;
	}
}
