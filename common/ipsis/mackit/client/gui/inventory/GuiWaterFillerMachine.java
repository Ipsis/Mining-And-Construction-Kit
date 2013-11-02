package ipsis.mackit.client.gui.inventory;

import ipsis.mackit.core.util.LogHelper;
import ipsis.mackit.inventory.ContainerWaterFillerMachine;
import ipsis.mackit.lib.Reference;
import ipsis.mackit.tileentity.TileWaterFillerMachine;

import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiWaterFillerMachine extends GuiContainer {

	private TileWaterFillerMachine machine;
	
	public GuiWaterFillerMachine(InventoryPlayer invPlayer, TileWaterFillerMachine machine) {
		super(new ContainerWaterFillerMachine(invPlayer, machine));
		
		this.machine = machine;
		
		xSize = 174;
		ySize = 177;
	}
	
	private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/gui/waterFillerMachine.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1, 1, 1, 1);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		float filled = (float)machine.getEnergyStored() / machine.getMaxEnergyStored();
		int barHeight = (int)(filled * 77);
		if (barHeight > 0) {
			int srcX = xSize;
			int srcY = 77 - barHeight;
			
			drawTexturedModalRect(guiLeft + 154, guiTop + 10 + 77 - barHeight, srcX, srcY, 11, barHeight);
			
		}
		
		filled = (float)machine.getCreateTickCount() / machine.getCreateTicksMax();
		int barWidth = (int)(filled * 26);
		if (barWidth > 0) {
			int srcY = 77;
			int srcX = xSize;
			drawTexturedModalRect(guiLeft + 96, guiTop + 50, srcX, srcY, barWidth, 4);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

		/* hover text for energy */
		int mouseX = x - guiLeft;
		int mouseY = y - guiTop;
		if (mouseX >= 154 && mouseX <= 154 + 11 && mouseY >= 10 && mouseY <= 10 + 77) {
			String s = "Energy " + (int)machine.getEnergyStored() + "/" + (int)machine.getMaxEnergyStored();
			drawHoveringText(Arrays.asList(s.split("\n")), mouseX, mouseY, fontRenderer);

		}
	}
		

}
