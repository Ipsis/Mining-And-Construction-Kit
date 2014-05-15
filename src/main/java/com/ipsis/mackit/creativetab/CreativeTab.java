package com.ipsis.mackit.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.ipsis.mackit.item.MKItems;
import com.ipsis.mackit.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTab {

	public static final CreativeTabs MK_TAB = new CreativeTabs(Reference.MOD_ID) {
		
		@Override
		public Item getTabIconItem() {

			return MKItems.itemFixerFoamGun;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public String getTranslatedTabLabel() {
			
			return "Mining & Construction Kit";
		}
	};
}
