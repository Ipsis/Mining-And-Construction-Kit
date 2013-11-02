package ipsis.mackit.block;

import ipsis.mackit.MacKit;
import ipsis.mackit.core.util.LogHelper;
import ipsis.mackit.lib.GuiIds;
import ipsis.mackit.lib.Reference;
import ipsis.mackit.lib.Strings;
import ipsis.mackit.tileentity.TileDyeTransposer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDyeTransposer extends BlockContainer {
	
	public BlockDyeTransposer(int id) {

		super(id, Material.rock);
		super.setCreativeTab(MacKit.tabsMacKit);
		this.setUnlocalizedName(Strings.BLOCK_DYE_TRANSPOSER);
	}
	
	@SideOnly(Side.CLIENT)
	private Icon icons[];
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		
		icons = new Icon[Strings.BLOCK_DYE_TRANSPOSER_NAMES.length];
		
		for (int i = 0; i < Strings.BLOCK_DYE_TRANSPOSER_NAMES.length; i++) {
			icons[i] = iconRegister.registerIcon(Reference.MOD_ID + ":" + Strings.BLOCK_DYE_TRANSPOSER_NAMES[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		
		if (side == metadata) {
			return icons[1]; /* front for now */
		}
		
		return icons[0];
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof TileDyeTransposer) {
				player.openGui(MacKit.instance, GuiIds.DYE_TRANSPOSER, world, x, y, z);
			}
		}
		
		return true;
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

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileDyeTransposer();
	}
}
