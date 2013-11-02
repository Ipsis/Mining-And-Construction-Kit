package ipsis.mackit.client.gui.inventory;

import java.util.Arrays;

import ipsis.mackit.core.util.LogHelper;
import ipsis.mackit.inventory.ContainerDyeTransposer;
import ipsis.mackit.lib.Reference;
import ipsis.mackit.tileentity.TileDyeTransposer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiDyeTransposer extends GuiContainer {
	
	private TileDyeTransposer machine;
	
	public GuiDyeTransposer(InventoryPlayer invPlayer, TileDyeTransposer machine) {
		super(new ContainerDyeTransposer(invPlayer, machine));
		
		this.machine = machine;
		
		xSize = 174;
		ySize = 177;
		
	}
	
	private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/gui/dyeTransposer.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		
		GL11.glColor4f(1, 1, 1, 1);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		/* energy */
		float filled = (float)machine.getEnergyStored() / machine.getMaxEnergyStored();
		int barHeight = (int)(filled * 41);
		if (barHeight > 0) {
			int srcX = xSize;
			int srcY = 41 - barHeight;
			
			drawTexturedModalRect(guiLeft + 26, guiTop + 22 + 41 - barHeight, srcX, srcY, 15, barHeight);			
		}
		
		/* liquid */
		filled = (float)machine.getLiquidStored() / machine.getLiquidCapacity();
		barHeight = (int)(filled * 61);
		if (barHeight > 0) {
			int srcX = xSize;
			int srcY = 42 + 61 - barHeight;			
			drawTexturedModalRect(guiLeft + 132, guiTop + 13 + 61 - barHeight, srcX, srcY, 16, barHeight);			
		}	
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

		/* hover text for energy */
		int mouseX = x - guiLeft;
		int mouseY = y - guiTop;
		if (mouseX >= 26 && mouseX <= 26 + 15 && mouseY >= 22 && mouseY <= 22 + 41) {
			String s = "Energy " + (int)machine.getEnergyStored() + "/" + (int)machine.getMaxEnergyStored() + "MJ";
			drawHoveringText(Arrays.asList(s.split("\n")), mouseX, mouseY, fontRenderer);
		}
		
		/* hover text for liquid */
		if (mouseX >= 132 && mouseX <= 132 + 16 && mouseY >= 13 && mouseY <= 13 + 60) {
			String s = (int)machine.getLiquidStored() + "/" + (int)machine.getLiquidCapacity() + "mB";
			drawHoveringText(Arrays.asList(s.split("\n")), mouseX, mouseY, fontRenderer);
		}
	}

}
