package com.ipsis.mackit.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.ipsis.cofhlib.gui.GuiBase;
import com.ipsis.mackit.reference.Reference;

public class GuiTorchPouch extends GuiBase {
	
	public static final String TEXTURE_STR = "mackit:textures/gui/torchPouch.png";
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/" + "torchPouch.png");
	
    private final ItemStack parentStack;
    private final InventoryTorchPouch inventoryTorchPouch;
	
	public GuiTorchPouch(EntityPlayer entityPlayer, InventoryTorchPouch inventoryTorchPouch) {
		
		super(new ContainerTorchPouch(entityPlayer, inventoryTorchPouch), TEXTURE);
		
		xSize = 174;
		ySize = 177;
		
		this.inventoryTorchPouch = inventoryTorchPouch;
		this.parentStack = inventoryTorchPouch.parentStack;
	}
}
