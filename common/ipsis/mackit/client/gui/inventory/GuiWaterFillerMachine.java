package ipsis.mackit.client.gui.inventory;

import ipsis.mackit.inventory.ContainerWaterFillerMachine;
import ipsis.mackit.lib.Reference;
import ipsis.mackit.tileentity.TileWaterFillerMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiWaterFillerMachine extends GuiContainer {

	public GuiWaterFillerMachine(InventoryPlayer invPlayer, TileWaterFillerMachine machine) {
		super(new ContainerWaterFillerMachine(invPlayer, machine));
		
		xSize = 174;
		ySize = 177;
	}
	
	private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/gui/waterFillerMachine.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1, 1, 1, 1);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

}
