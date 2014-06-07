package com.ipsis.mackit.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
	private String info;
	
	public ItemMK() {
		
		this(null);
	}
	
	public ItemMK(String info) {
		
		super();
		this.setCreativeTab(CreativeTab.MK_TAB);
		this.setNoRepair();
		this.info = info;
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack,	EntityPlayer entityPlayer, List info, boolean useExtraInformation) {

		if (this.info != null)
			info.add(this.info);
	}
}
