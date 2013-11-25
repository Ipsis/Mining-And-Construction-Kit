package ipsis.mackit.block;

import ipsis.mackit.MacKit;
import ipsis.mackit.lib.Reference;
import ipsis.mackit.lib.Strings;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMKMachine extends BlockContainer {
		
		private String name;
	
		public BlockMKMachine(int id, String name) {
			super(id, Material.rock);
			super.setCreativeTab(MacKit.tabsMacKit);
			
			this.name = name;
			this.setUnlocalizedName(this.name);
		}
		
		@SideOnly(Side.CLIENT)
		private Icon sideIcon;
		private Icon topIcon;
		private Icon frontIcons[];
		
		@Override
		@SideOnly(Side.CLIENT)
		public void registerIcons(IconRegister iconRegister) {

			sideIcon = iconRegister.registerIcon(Reference.MOD_ID + ":" + Strings.BLOCK_MACHINE_SIDE);
			topIcon = iconRegister.registerIcon(Reference.MOD_ID + ":" + Strings.BLOCK_MACHINE_TOP);
			
			frontIcons = new Icon[2];
			frontIcons[0] = iconRegister.registerIcon(Reference.MOD_ID + ":" + this.name + "Off");
			frontIcons[1] = iconRegister.registerIcon(Reference.MOD_ID + ":" + this.name + "On");
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public Icon getIcon(int side, int metadata) {
			
			int front = (metadata & 0x3) + 2;
			int enabled = (metadata & 0x8) >> 3;
			
			if (side == 0 || side == 1)
				return topIcon;
			
			if (side == front) {
				return frontIcons[enabled];
			} else {
				return sideIcon;
			}
			
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
		public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
			super.onBlockPlacedBy(world, x, y, z, entityLiving, itemStack);
			
			/* Place facing the player */
			if (!world.isRemote) {
				int l = MathHelper.floor_double((double)(entityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
				ForgeDirection orientation = getForgeDir(l);
				int metadata = orientation.getOpposite().ordinal() - 2;
				world.setBlockMetadataWithNotify(x, y, z, metadata, 3);
			}
		}

		@Override
		public TileEntity createNewTileEntity(World world) {
			return null;
		}
	
}
