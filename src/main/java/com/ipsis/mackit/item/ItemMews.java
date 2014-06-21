package com.ipsis.mackit.item;

import java.util.List;

import com.ipsis.mackit.helper.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * (M)ob (E)arly (W)arning (S)ystem
 *
 * TODO right click a cat with a cage to create
 */
public class ItemMews extends ItemMK {

	public ItemMews() {
		
		super("Mob Early Warning System (Purr)");
		this.setMaxStackSize(1);
		this.setMaxDamage(FREQUENCY);
	}
	
	private static final int SCAN_RANGE = 10;
	private static final int FREQUENCY = 10;

    private static final String ACTIVE_TAG = "Active";

    @Override
    public boolean getShareTag() {

        return true;
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer entityPlayer) {

        setDefaultTags(itemStack);
    }

    private void setDefaultTags(ItemStack itemStack) {

        if (itemStack.stackTagCompound == null)
            itemStack.stackTagCompound = new NBTTagCompound();

        itemStack.stackTagCompound.setBoolean(ACTIVE_TAG, false);
    }

    private void toggle(ItemStack itemStack) {

        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        itemStack.stackTagCompound.setBoolean(ACTIVE_TAG, isActive(itemStack) ? false : true);
    }

    private boolean isActive(ItemStack itemStack) {

        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        return itemStack.stackTagCompound.getBoolean(ACTIVE_TAG);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {

        if (world.isRemote)
            return itemStack;

        if (entityPlayer.isSneaking())
            toggle(itemStack);

        return itemStack;
    }

    private boolean canRun(EntityPlayer entityPlayer, int slot) {

        InventoryPlayer inv = entityPlayer.inventory;

        if (slot >= inv.getHotbarSize())
            return false;

        if (!entityPlayer.capabilities.isCreativeMode && entityPlayer.experienceLevel < 1)
            return false;

        return true;
    }

    @Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean isHeld) {

        if (!isActive(itemStack))
            return;

        if (!canRun((EntityPlayer)entity, slot))
            return;

        boolean checkArea = false;
		if (this.getDamage(itemStack) == FREQUENCY) {
			this.setDamage(itemStack, 0);
			checkArea = true;
		} else {
			this.setDamage(itemStack, this.getDamage(itemStack) + 1);
		}
		
		if (checkArea && !world.isRemote) {
			
			double x = entity.posX;
			double y = entity.posY;
			double z = entity.posZ;

            ((EntityPlayer)entity).addExperience(-1);

            Entity e = world.findNearestEntityWithinAABB(EntityMob.class,
                    AxisAlignedBB.getBoundingBox(x - SCAN_RANGE, y - SCAN_RANGE, z - SCAN_RANGE, x + SCAN_RANGE, y + SCAN_RANGE, z + SCAN_RANGE), entity);

            if (e != null) {

                /* Closer the entity, the higher the volume */
                double d1 = entity.getDistanceSqToEntity(e);
                double max = Math.pow(SCAN_RANGE, 2);

                if (d1 > max)
                    d1 = max;

                d1 = max - d1;
                float volume = (0.8F / 100.0F) * (float)d1;
                world.playSoundAtEntity(entity, "random.levelup", volume, 1.0F);
            }
		}
	}
}
