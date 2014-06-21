package com.ipsis.mackit.block;

import com.ipsis.cofhlib.util.EntityHelper;

import com.ipsis.mackit.helper.LogHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Colour out of space
 *
 *
 * This uses code from Professor Mobius Jabba to "throw" the items at the player.
 * https://bitbucket.org/ProfMobius/jabba
 *
 * TileEntityBarrel dropItemInWorld
 *
 * It is a vast improvement over my attempt, where the item was thrown anywhere but
 * towards the player!
 *
 */
public class BlockDyeLeech extends BlockFaced {

	public BlockDyeLeech(String name) {
		
		super(Material.iron, name, new String[]{ "dyeLeech", "dyeLeech_top", "dyeLeech", "dyeLeech", "dyeLeech", "dyeLeech" } );
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TileDyeLeech();
	}
	
	@Override
	public boolean isOpaqueCube() {

		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {

		if (world.isRemote)
			return;
				
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileDyeLeech)
			((TileDyeLeech)te).setBiome();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x,	int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {

		if (entityPlayer.isSneaking())
			return false;
		
		if (!world.isRemote) {
			
			TileEntity te = world.getTileEntity(x, y, z);
			if (te != null && te instanceof TileDyeLeech) {
				
				TileDyeLeech dyeLeech = (TileDyeLeech)te;
				ItemStack out = dyeLeech.getOutput();
				if (out != null) {

                    ForgeDirection d = EntityHelper.getEntityFacingForgeDirection(entityPlayer);

                    double speedfactor = 0.4;
                    double stackCoordX = 0.0D, stackCoordY = 0.0D, stackCoordZ = 0.0D;

                    /* Spawn the item in the players direction */
                    switch (d) {
                        case NORTH:
                            stackCoordX = x + 0.5D;
                            stackCoordY = y + 0.5D;
                            stackCoordZ = z - 0.25D;
                            break;
                        case EAST:
                            stackCoordX = x + 1.25D;
                            stackCoordY = y + 0.5D;
                            stackCoordZ = z + 0.5D;
                            break;
                        case SOUTH:
                            stackCoordX = x + 0.5D;
                            stackCoordY = y + 0.5D;
                            stackCoordZ = z + 1.25D;
                            break;
                        case WEST:
                            stackCoordX = x - 0.25D;
                            stackCoordY = y + 0.5D;
                            stackCoordZ = z + 0.5D;
                            break;
                    }

                    EntityItem droppedEntity = new EntityItem(world, stackCoordX, stackCoordY, stackCoordZ, out);

                    /* Throw it towards the player */
                    Vec3 motion = Vec3.createVectorHelper(entityPlayer.posX - stackCoordX, entityPlayer.posY - stackCoordY, entityPlayer.posZ - stackCoordZ);
                    motion.normalize();
                    droppedEntity.motionX = motion.xCoord;
                    droppedEntity.motionY = motion.yCoord;
                    droppedEntity.motionZ = motion.zCoord;
                    double offset = 0.25D;
                    droppedEntity.moveEntity(motion.xCoord * offset, motion.yCoord * offset, motion.zCoord * offset);

                    droppedEntity.motionX *= speedfactor;
                    droppedEntity.motionY *= speedfactor;
                    droppedEntity.motionZ *= speedfactor;

                    world.spawnEntityInWorld(droppedEntity);
				}
			}
		}
		
		return true;
	}
}
