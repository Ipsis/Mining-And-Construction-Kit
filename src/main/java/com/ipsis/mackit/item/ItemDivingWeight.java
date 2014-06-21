package com.ipsis.mackit.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDivingWeight extends ItemMK {

    public ItemDivingWeight()  {

        super();
        this.setMaxDamage(0);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean isHeld) {

        /* If it is in the hot bar then it is "active" */
        EntityPlayer entityPlayer = (EntityPlayer)entity;
        InventoryPlayer inv = entityPlayer.inventory;

        if (slot < inv.getHotbarSize()) {

            /* Need a downwards pull motion in liquids */
            if (entityPlayer.isInWater())
                entity.motionY += (-2.0F);
        }
    }
}
