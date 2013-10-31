package ipsis.mackit.block;

import ipsis.mackit.MacKit;
import ipsis.mackit.lib.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLiquidDye extends BlockFluidClassic {

	private String texture;
	
	@SideOnly(Side.CLIENT)
    public Icon stillIcon;
	@SideOnly(Side.CLIENT)
    public Icon flowIcon;
	
	public BlockLiquidDye(int id, Fluid fluid, String name, String texture) {
			
		super(id, fluid, Material.water);
		this.setCreativeTab(MacKit.tabsMacKit);
		this.setUnlocalizedName(name);
		this.texture = texture;
			
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {

		stillIcon = iconRegister.registerIcon(Reference.MOD_ID + ":" + this.texture + "Still");
		flowIcon =  iconRegister.registerIcon(Reference.MOD_ID + ":" + this.texture + "Flowing");
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon (int side, int meta)
    {
        if (side == 0 || side == 1)
            return stillIcon;
        return flowIcon;
    }
}
