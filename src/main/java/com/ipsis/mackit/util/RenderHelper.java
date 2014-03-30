package com.ipsis.mackit.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public final class RenderHelper {

	public static final ResourceLocation MC_BLOCK_SHEET = new ResourceLocation("textures/atlas/blocks.png");
	public static final ResourceLocation MC_ITEM_SHEET = new ResourceLocation("textures/atlas/items.png");
	public static final ResourceLocation MC_FONT_DEFAULT = new ResourceLocation("textures/font/ascii.png");
	public static final ResourceLocation MC_FONT_ALTERNATE = new ResourceLocation("textures/font/ascii_sga.png");
	
	private RenderHelper() {

	}

	public static final TextureManager engine() {

		return Minecraft.getMinecraft().renderEngine;
	}
	
	public static final void bindTexture(ResourceLocation texture) {

		engine().bindTexture(texture);
	}

	public static final void setBlockTextureSheet() {

		bindTexture(MC_BLOCK_SHEET);
	}

	public static final void setItemTextureSheet() {

		bindTexture(MC_ITEM_SHEET);
	}

	public static final void setDefaultFontTextureSheet() {

		bindTexture(MC_FONT_DEFAULT);
	}

	public static final void setSGAFontTextureSheet() {

		bindTexture(MC_FONT_ALTERNATE);
	}
	
}
