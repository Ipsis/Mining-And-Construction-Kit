package ipsis.mackit.block;

import ipsis.mackit.MacKit;
import ipsis.mackit.lib.GuiIds;
import ipsis.mackit.lib.Reference;
import ipsis.mackit.lib.Strings;
import ipsis.mackit.tileentity.TileMachineWaterFiller;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWaterFillerMachine extends BlockContainer {

	/* metadata is the facing direction */
	
	public BlockWaterFillerMachine(int id) {
		super(id, Material.iron);
		setCreativeTab(MacKit.tabsMacKit);
		setUnlocalizedName(Strings.BLOCK_WATER_FILLER_MACHINE);
	}
	
	@SideOnly(Side.CLIENT)
	private Icon sideIcon;
	private Icon topIcon;
	private Icon frontIcon;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		sideIcon = register.registerIcon(Reference.MOD_ID + ":" + Strings.BLOCK_WATER_FILLER_MACHINE + "_side");
		topIcon = register.registerIcon(Reference.MOD_ID + ":" + Strings.BLOCK_WATER_FILLER_MACHINE);
		frontIcon = register.registerIcon(Reference.MOD_ID + ":" + Strings.BLOCK_WATER_FILLER_MACHINE + "_front");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		
		if (side == 0 || side == 1)
			return topIcon;
		
		if (side == metadata)
			return frontIcon;
		
		return sideIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileMachineWaterFiller();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			
				TileEntity te = world.getBlockTileEntity(x, y, z);
				if (te != null && te instanceof TileMachineWaterFiller) {
					player.openGui(MacKit.instance, GuiIds.WATER_FILLER_MACHINE, world, x, y, z);
				}
		}

		return true;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof IInventory) {
			IInventory inventory = (IInventory)te;
		
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				ItemStack stack = inventory.getStackInSlotOnClosing(i);
				
				if (stack != null) {
					float spX = x + world.rand.nextFloat();
					float spY = y + world.rand.nextFloat();
					float spZ = z + world.rand.nextFloat();
					
					float scale = 0.05F;
					EntityItem droppedItem = new EntityItem(world, spX, spY, spZ, stack);
					droppedItem.motionX = (-0.5F + world.rand.nextFloat()) * scale;
					droppedItem.motionY = (4F + world.rand.nextFloat()) * scale;
					droppedItem.motionZ = (-0.5F + world.rand.nextFloat()) * scale;
					
					world.spawnEntityInWorld(droppedItem);
				}
			}
		}
		
		super.breakBlock(world, x, y, z, id, meta);
	}

	private ForgeDirection getForgeDir(int x)
	{
		if (x == 0) {
			return ForgeDirection.SOUTH;
		} else if (x == 1) {
			return ForgeDirection.WEST;
		} else if (x == 2) {
			return ForgeDirection.NORTH;
		} else if (x == 3) {
			return ForgeDirection.EAST;
		} else {
			return ForgeDirection.SOUTH;
		}			
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack itemStack) {
		
		super.onBlockPlacedBy(world, x, y, z, entityliving, itemStack);
		
		/* init to facing player with circle mode */
		if (!world.isRemote) {
		
			/* this calculates the minecraft world direction N/S/E/W */
			int l = MathHelper.floor_double((double)(entityliving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			
			ForgeDirection orientation = getForgeDir(l);
			world.setBlockMetadataWithNotify(x, y, z, orientation.getOpposite().ordinal(), 3);
		}
			
	} 
}
