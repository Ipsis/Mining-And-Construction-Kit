package com.ipsis.mackit.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.lib.Strings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFluidDye extends BlockFluidClassic {
	
	private String name;
	
	public BlockFluidDye(int id, Fluid fluid, String name) {
		
		super(id, fluid, Material.water);
		this.setCreativeTab(MacKit.tabsMacKit);
		this.setUnlocalizedName(Strings.RESOURCE_PREFIX + name);
		this.name = name;
	}

	@SideOnly(Side.CLIENT)
	public Icon stillIcon;
	@SideOnly(Side.CLIENT)
	public Icon flowIcon;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {

		stillIcon = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + this.name + "_still");
		flowIcon = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + this.name + "_flowing");		
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int meta)
    {
        if (side == 0 || side == 1)
            return stillIcon;
        return flowIcon;
    }
	
}
