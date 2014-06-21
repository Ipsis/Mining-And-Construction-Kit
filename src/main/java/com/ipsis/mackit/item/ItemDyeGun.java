package com.ipsis.mackit.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.ipsis.cofhlib.util.ColorHelper;
import com.ipsis.mackit.helper.ColoredBlockSwapper;
import com.ipsis.mackit.helper.DyeHelper;
import com.ipsis.mackit.helper.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * TODO Chat color change message could be nicer!
 */
public class ItemDyeGun extends ItemMK {

	private static final int TANK_CAPACITY = DyeHelper.DYE_BASE_AMOUNT * 20;

    private static final String FLUID_TAG = "FluidAmount";
    private static final String COLOR_TAG = "CurrColor";
	
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
	public boolean getShareTag() {

		return true;
	}
	
	@Override
	public void onCreated(ItemStack itemStack, World world,	EntityPlayer entityPlayer) {

		setDefaultTags(itemStack);
	}
	
	public void setColor(ItemStack itemStack, DyeHelper.DyeColor color) {
		
		 if (itemStack.stackTagCompound == null)
			 setDefaultTags(itemStack);
		 
		 itemStack.stackTagCompound.setInteger(COLOR_TAG, color.getDmg());
	}

    public DyeHelper.DyeColor getColor(ItemStack itemStack) {

        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        return DyeHelper.DyeColor.getFromDmg(itemStack.stackTagCompound.getInteger(COLOR_TAG));
    }
	
	public static void setFluidAmount(ItemStack itemStack, int amount) {
		
		 if (itemStack.stackTagCompound == null)
			 setDefaultTags(itemStack);
		
		if (amount > TANK_CAPACITY)
			amount = TANK_CAPACITY;
		
		itemStack.stackTagCompound.setInteger(FLUID_TAG, amount);
	}
	
	public static int getFluidAmount(ItemStack itemStack) {
		
		 if (itemStack.stackTagCompound == null)
			 setDefaultTags(itemStack);
		
		 return itemStack.stackTagCompound.getInteger(FLUID_TAG);
	}
	
	public static void setDefaultTags(ItemStack itemStack) {
		
		if (itemStack.stackTagCompound == null)
			itemStack.stackTagCompound = new NBTTagCompound();
		
		itemStack.stackTagCompound.setInteger(COLOR_TAG, DyeHelper.DyeColor.WHITE.getDmg());
		itemStack.stackTagCompound.setInteger(FLUID_TAG, 0);
	}
	
	private void nextColor(ItemStack itemStack) {
		
		if (itemStack.stackTagCompound == null)
			setDefaultTags(itemStack);
		
		int color = itemStack.stackTagCompound.getInteger(COLOR_TAG);
		color = (color + 1) % 16;
		setColor(itemStack, DyeHelper.DyeColor.getFromDmg(color));
	}
	
	private void useDye(ItemStack itemStack) {
		
		if (itemStack.stackTagCompound == null)
			setDefaultTags(itemStack);
		
		int fluid = itemStack.stackTagCompound.getInteger(FLUID_TAG);
		fluid -= DyeHelper.DYE_BASE_AMOUNT;
		if (fluid < 0)
			fluid = 0;
		
		setFluidAmount(itemStack, fluid);
	}

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {

        if (world.isRemote)
            return itemStack;

        if (entityPlayer.isSneaking()) {

            nextColor(itemStack);
            entityPlayer.addChatComponentMessage(new ChatComponentText(getColor(itemStack).getName()));
        }

        return itemStack;
    }

    @Override
	public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer entityPlayer,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {

		if (world.isRemote)
			return false;
		
		if (!entityPlayer.isSneaking()) {

			if (itemStack.stackTagCompound != null && itemStack.stackTagCompound.hasKey(FLUID_TAG) && itemStack.stackTagCompound.hasKey(COLOR_TAG)) {
				
				int fluid = getFluidAmount(itemStack);
				if (!entityPlayer.capabilities.isCreativeMode && fluid < DyeHelper.DYE_BASE_AMOUNT)
					return true;

                if (!entityPlayer.canPlayerEdit(x, y, z, side, itemStack))
                    return true;
				
				DyeHelper.DyeColor color = DyeHelper.DyeColor.getFromDmg(itemStack.stackTagCompound.getInteger(COLOR_TAG));

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
		
		if (itemStack.stackTagCompound == null)
			setDefaultTags(itemStack);
		
		info.add(getColor(itemStack).getName());
		info.add(itemStack.stackTagCompound.getInteger(FLUID_TAG) + "/" + TANK_CAPACITY);
	}
}
