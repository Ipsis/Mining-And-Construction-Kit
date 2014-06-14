package com.ipsis.mackit.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ipsis.cofhlib.util.ColorHelper;
import com.ipsis.mackit.helper.ColoredBlockSwapper;
import com.ipsis.mackit.helper.DyeHelper;
import com.ipsis.mackit.helper.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDyeGun extends ItemMK {

	private static final int TANK_CAPACITY = DyeHelper.DYE_BASE_AMOUNT * 20;
	
	public ItemDyeGun() {
		
		super("Replace blocks with a colored version");
		this.setMaxStackSize(1);
		this.canRepair = false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
	 
		 ItemStack itemStack = new ItemStack(MKItems.itemDyeGun);
		 	 
		 setDefaultTags(itemStack);
		 setFluidAmount(itemStack, TANK_CAPACITY);
		 list.add(itemStack);	 
	}
	
	@Override
	public void onCreated(ItemStack itemStack, World world,	EntityPlayer entityPlayer) {

		setDefaultTags(itemStack);
	}
	
	public void setColor(ItemStack itemStack, DyeHelper.DyeColor color) {
		
		 if (itemStack.stackTagCompound == null)
			 setDefaultTags(itemStack);
		 
		 itemStack.stackTagCompound.setInteger("CurrColor", color.getDmg());
	}
	
	public void setFluidAmount(ItemStack itemStack, int amount) {
		
		 if (itemStack.stackTagCompound == null)
			 setDefaultTags(itemStack);
		
		itemStack.stackTagCompound.setInteger("FluidAmount", TANK_CAPACITY);
	}
	
	public void setDefaultTags(ItemStack itemStack) {
		
		if (itemStack.stackTagCompound == null)
			itemStack.stackTagCompound = new NBTTagCompound();
		
		itemStack.stackTagCompound.setInteger("CurrColor", DyeHelper.DyeColor.WHITE.getDmg());
		itemStack.stackTagCompound.setInteger("FluidAmount", 0);
	}
	
	private void nextColor(ItemStack itemStack) {
		
		if (itemStack.stackTagCompound == null)
			setDefaultTags(itemStack);
		
		int color = itemStack.stackTagCompound.getInteger("CurrColor");
		color = (color + 1) % 16;
		setColor(itemStack, DyeHelper.DyeColor.getFromDmg(color));
	}
	
	private void useDye(ItemStack itemStack) {
		
		if (itemStack.stackTagCompound == null)
			setDefaultTags(itemStack);
		
		int fluid = itemStack.stackTagCompound.getInteger("FluidAmount");
		fluid -= DyeHelper.DYE_BASE_AMOUNT;
		if (fluid < 0)
			fluid = 0;
		
		setFluidAmount(itemStack, fluid);
	}
		
	@Override
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer entityPlayer,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {

		if (world.isRemote)
			return false;
		
		if (entityPlayer.isSneaking()) {
			
			nextColor(itemStack);
		} else {
			
			if (itemStack.stackTagCompound != null && itemStack.stackTagCompound.hasKey("FluidAmount") && itemStack.stackTagCompound.hasKey("CurrColor")) {
				
				/* check for creative mode and enough dye available */
				int fluid = itemStack.stackTagCompound.getInteger("FluidAmount");
				DyeHelper.DyeColor color = DyeHelper.DyeColor.getFromDmg(itemStack.stackTagCompound.getInteger("CurrColor"));
				
				/* change block */
				if (ColoredBlockSwapper.swap(entityPlayer, world, x, y, z, color, false) == true) {
					
					if (!entityPlayer.capabilities.isCreativeMode)
						useDye(itemStack);
				}
			}
		}
		
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack,	EntityPlayer entityPlayer, List info, boolean useExtraInformation) {
		
		int fluid = itemStack.getItemDamage();
		
		if (itemStack.stackTagCompound != null)
			setDefaultTags(itemStack);
		
		info.add(DyeHelper.DyeColor.getFromDmg(itemStack.stackTagCompound.getInteger("CurrColor")).getName());
		info.add(itemStack.stackTagCompound.getInteger("FluidAmount") + "/" + TANK_CAPACITY);
	}
}
