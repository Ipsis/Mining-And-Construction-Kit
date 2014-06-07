package com.ipsis.mackit.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.ipsis.cofhlib.render.IconRegistry;
import com.ipsis.cofhlib.util.EntityHelper;
import com.ipsis.mackit.creativetab.CreativeTab;
import com.ipsis.mackit.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFacedMachine extends BlockContainer {

	private String baseName;

	public BlockFacedMachine(Material m, String name) {
		
		super(m);
		this.setCreativeTab(CreativeTab.MK_TAB);
		this.setBlockName(name);
	}
	
	@Override
	public Block setBlockName(String name) {

		baseName = name;
		name = Reference.MOD_ID + ":" + name;
		return super.setBlockName(name);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		IconRegistry.addIcon(baseName + "_side", Reference.MOD_ID + ":" + "machine_side", iconRegister);
		IconRegistry.addIcon(baseName + "_top", Reference.MOD_ID + ":" + "machine_top", iconRegister);
		IconRegistry.addIcon(baseName + "_front_active", Reference.MOD_ID + ":" + baseName + "_front_active", iconRegister);
		IconRegistry.addIcon(baseName + "_front_inactive", Reference.MOD_ID + ":" + baseName + "_front_inactive", iconRegister);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata) {
		
		/**
		 * As we override the IBlockAccess version this will only be called for the toolbar
		 * Is is oriented as facing south
		 */

		ForgeDirection facing = ForgeDirection.SOUTH;
		return getIconFromSide(facing, side, false);
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon getIconFromSide(ForgeDirection facing, int side, boolean running) {
		
		ForgeDirection facingCurr = ForgeDirection.getOrientation(side);
		
		if (side == 0 || side == 1)
			return IconRegistry.getIcon(baseName + "_top");

		if (facingCurr == facing) {
			if (running)
				return IconRegistry.getIcon(baseName + "_front_active");
			else
				return IconRegistry.getIcon(baseName + "_front_inactive");
		} else if (facingCurr == facing.getOpposite())
			return IconRegistry.getIcon(baseName + "_side");
		else {
			return IconRegistry.getIcon(baseName + "_side");
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {
	
		TileEntity te = iblockaccess.getTileEntity(x, y, z);
		if (te != null && te instanceof IFacingMachine) {
			
			return getIconFromSide(((IFacingMachine)te).getFacing(), side, ((IFacingMachine)te).getRunning());
		}
		
		return null;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {

		if (world.isRemote)
			return;
		
		ForgeDirection d = EntityHelper.getEntityFacingForgeDirection(entityLiving);
		
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof IFacing)
			((IFacing)te).setFacing(d);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int meta) {

		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof IInventory) {

			IInventory inventory = (IInventory) te;

			for (int i = 0; i < inventory.getSizeInventory(); i++) {

				/* Vswe tutorial break block code */
				ItemStack stack = inventory.getStackInSlotOnClosing(i);
				if (stack != null) {
					float spawnX = x + world.rand.nextFloat();
					float spawnY = y + world.rand.nextFloat();
					float spawnZ = z + world.rand.nextFloat();
					
					EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, stack);
					
					float mult = 0.05F;
					
					droppedItem.motionX = (-0.5F + world.rand.nextFloat()) * mult;
					droppedItem.motionY = (4 + world.rand.nextFloat()) * mult;
					droppedItem.motionZ = (-0.5F + world.rand.nextFloat()) * mult;
					
					world.spawnEntityInWorld(droppedItem);
				}
			}
		}

		super.breakBlock(world, x, y, z, b, meta);
	}
	

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return null;
	}
}
