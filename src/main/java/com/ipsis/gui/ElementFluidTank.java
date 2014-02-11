package com.ipsis.gui;

import java.util.List;

import com.ipsis.mackit.lib.Textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.IFluidTank;

public class ElementFluidTank extends ElementBase {

	public static final int DEFAULT_SCALE = 60;

	protected IFluidTank tank;
	protected int gaugeType;

	public ElementFluidTank(GuiBase gui, int posX, int posY, IFluidTank tank) {

		super(gui, posX, posY);
		this.tank = tank;

		this.texture = Textures.GUI_FLUID_TANK;
		this.texW = 64;
		this.texH = 64;

		this.sizeX = 16;
		this.sizeY = DEFAULT_SCALE;
	}

	public ElementFluidTank(GuiBase gui, int posX, int posY, IFluidTank tank, String texture) {

		super(gui, posX, posY);
		this.tank = tank;

		this.texture = new ResourceLocation(texture);
		this.texW = 64;
		this.texH = 64;

		this.sizeX = 16;
		this.sizeY = DEFAULT_SCALE;
	}

	public ElementFluidTank setGauge(int gaugeType) {

		this.gaugeType = gaugeType;
		return this;
	}

	@Override
	public void draw() {

		int amount = getScaled();

		gui.drawFluid(posX, posY + sizeY - amount, tank.getFluid(), sizeX, amount);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(posX, posY, 32 + gaugeType * 16, 1, sizeX, sizeY);
	}

	@Override
	public void addTooltip(List<String> list) {

		if (tank.getFluid() != null && tank.getFluidAmount() > 0) {
			list.add("New fluid");
			//list.add(StringHelper.getFluidName(tank.getFluid()));
		}
		list.add("" + tank.getFluidAmount() + " / " + tank.getCapacity() + " mB");
	}


	int getScaled() {

		return tank.getFluidAmount() * sizeY / tank.getCapacity();
	}
}
