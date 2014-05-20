package com.ipsis.mackit.util.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ipsis.mackit.block.TilePortaChant;
import com.ipsis.mackit.container.ContainerPortaChant;
import com.ipsis.mackit.helper.LogHelper;

import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null) {
		
			if (te instanceof TilePortaChant) {
				LogHelper.error("getServerGuiElement");
				TilePortaChant cte = (TilePortaChant)te;
				return new ContainerPortaChant(player.inventory, cte);
			} else {
				return null;
			}			
		} else {
			return null;
		}
	}

	
}
