package ipsis.mackit.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;


public abstract class BlockMK extends Block {

	public BlockMK(int id, Material material) {
		super(id, material);
		super.setCreativeTab(CreativeTabs.tabMisc);
	}
	

}
