package com.ipsis.mackit.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import com.ipsis.mackit.creativetab.CreativeTab;
import com.ipsis.mackit.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Simple item
 * Texture name is based on the unlocalised name
 *
 */
public class ItemMK extends Item {

	private String iconName;
	
	public ItemMK() {
		
		super();
		this.setCreativeTab(CreativeTab.MK_TAB);
		this.setNoRepair();
	}
	
	@Override
	public Item setUnlocalizedName(String name) {

		iconName = name;
		name = Reference.MOD_ID + ":" + name;
		return super.setUnlocalizedName(name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {

		itemIcon = iconRegister.registerIcon(Reference.MOD_ID + ":" + iconName);
	}
}
