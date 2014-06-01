package com.ipsis.mackit.block;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import codechicken.core.ClientUtils;
import codechicken.lib.render.RenderUtils;
import codechicken.lib.render.TextureUtils;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Transformation;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileDyeLeechRenderer extends TileEntitySpecialRenderer {

    private final RenderItem customRenderItem;

    public TileDyeLeechRenderer()
    {
        customRenderItem = new RenderItem()
        {
            @Override
            public boolean shouldBob()
            {
                return false;
            }
        };

        customRenderItem.setRenderManager(RenderManager.instance);
    }
	
    
    /**
     * This it the code that ChickenBones uses in his Translocator mode to show the crafting table.
     * https://bitbucket.org/ChickenBones
     * Translocator/codechicken/translocator/TileCraftingGridRenderer.java
     */
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {

		if (tileEntity instanceof TileDyeLeech) {
					
			TileDyeLeech te = (TileDyeLeech)tileEntity;
            
			TextureUtils.bindAtlas(0);
			
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glPushMatrix();
            {
            	GL11.glTranslated(x+0.5, y+0.5, z+0.5);
            	Transformation orient = Rotation.quarterRotations[3];
            	ItemStack item = new ItemStack(Items.blaze_powder);
            	           	
            	GL11.glPushMatrix();
            	{            
            		GL11.glTranslated(0, 0.6 + 0.02 * Math.sin(ClientUtils.getRenderTime() / 10), 0);
            		GL11.glScaled(0.8, 0.8, 0.8);
            		RenderUtils.renderItemUniform(item, ClientUtils.getRenderTime());
            	}
            	GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL); 
		}
	}
}
