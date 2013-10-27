package ipsis.mackit.block;

import ipsis.mackit.lib.Reference;
import ipsis.mackit.lib.Strings;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFixedEarth extends BlockMK {

	public BlockFixedEarth(int id) {
		
		super(id, Material.grass);
		this.setUnlocalizedName(Strings.BLOCK_FIXED_EARTH);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		
		for (int i = 0; i < Strings.BLOCK_FIXED_NAMES.length; i++) {
			list.add(new ItemStack(id, 1, i));
		}
	}
	
	@SideOnly(Side.CLIENT)
	private Icon icons[];
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		
		icons = new Icon[Strings.BLOCK_FIXED_NAMES.length];
		
		for (int i = 0; i < Strings.BLOCK_FIXED_NAMES.length; i++) {
			icons[i] = iconRegister.registerIcon(Reference.MOD_ID + ":" + Strings.BLOCK_FIXED_NAMES[i]);
		}		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		
		if (metadata < Strings.BLOCK_FIXED_NAMES.length) {
			return icons[metadata];
		}
		
		return null;
	}
	
	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}
	
}
