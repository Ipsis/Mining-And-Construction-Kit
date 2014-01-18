package com.ipsis.mackit.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

import com.ipsis.mackit.MacKit;
import com.ipsis.mackit.lib.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMK extends Item {

	public static final int SHIFTED_ID_RANGE_CORRECTION = 256;
	
	public ItemMK(int id) {
		
		super(id - SHIFTED_ID_RANGE_CORRECTION);
		this.setCreativeTab(MacKit.tabsMacKit);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		/* this is broken as you get mackit:fixerFoamPellet.png */
		itemIcon = iconRegister.registerIcon(Reference.MOD_ID + ":" + 
					this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
	}
}
