package ipsis.mackit.block;

import ipsis.mackit.MacKit;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;


public abstract class BlockMK extends Block {

	public BlockMK(int id, Material material) {
		super(id, material);
		super.setCreativeTab(MacKit.tabsMacKit);
	}

}
