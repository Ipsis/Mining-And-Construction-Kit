package ipsis.mackit.block;

import ipsis.mackit.lib.Reference;
import ipsis.mackit.lib.Strings;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDyeTransposer extends BlockMK {
	
	/**
	 *  metadata will store
	 *  enabled (1) or disabled (0)
	 */
	
	public BlockDyeTransposer(int id) {

		super(id, Material.rock);
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
		
		if (side == 3) {
			return icons[(metadata == 0 ? 1 : 2)];		
		}
		
		return icons[0];
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
		
		super.onBlockPlacedBy(world, i, j, k, entityliving, stack);
		
		
	}
}
