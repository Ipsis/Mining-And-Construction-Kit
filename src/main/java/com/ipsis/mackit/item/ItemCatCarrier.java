package com.ipsis.mackit.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCatCarrier extends ItemMK {

    public ItemCatCarrier() {

        super();
        this.setMaxStackSize(1);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer entityPlayer, EntityLivingBase entityLivingBase) {

        if (entityPlayer.worldObj.isRemote)
            return false;

        if (entityLivingBase instanceof EntityOcelot)
        {
            EntityOcelot entityOcelot = (EntityOcelot)entityLivingBase;

            /* Remove the ocelot, destroy the cat carrier, create a Mews */
            entityOcelot.setDead();
            itemStack.stackSize--;

            float spawnX = (float) entityPlayer.posX + entityPlayer.worldObj.rand.nextFloat();
            float spawnY = (float) entityPlayer.posY + entityPlayer.worldObj.rand.nextFloat();
            float spawnZ = (float) entityPlayer.posZ + entityPlayer.worldObj.rand.nextFloat();
            float mult = 0.05F;

            EntityItem droppedItem = new EntityItem(entityPlayer.worldObj, spawnX, spawnY, spawnZ, new ItemStack(MKItems.itemMews));
            droppedItem.motionX = (-0.5F + entityPlayer.worldObj.rand.nextFloat()) * mult;
            droppedItem.motionY = (4 + entityPlayer.worldObj.rand.nextFloat()) * mult;
            droppedItem.motionZ = (-0.5F + entityPlayer.worldObj.rand.nextFloat()) * mult;

            entityPlayer.worldObj.spawnEntityInWorld(droppedItem);

            return true;
        }

        return false;
    }
}
