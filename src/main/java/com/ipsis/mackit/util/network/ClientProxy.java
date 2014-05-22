package com.ipsis.mackit.util.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ipsis.mackit.block.TilePortaChant;
import com.ipsis.mackit.container.GuiPortaChant;
import com.ipsis.mackit.helper.LogHelper;

public class ClientProxy extends CommonProxy {

	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {

		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null) {
		
			if (te instanceof TilePortaChant) {
				return new GuiPortaChant(player.inventory, (TilePortaChant)te);
			} else {
				return null;				
			}
		} else {
			return null;
		}
	}
}
