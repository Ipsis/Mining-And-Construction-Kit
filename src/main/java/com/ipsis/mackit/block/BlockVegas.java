package com.ipsis.mackit.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * Quick way of doubling your ore
 * Put your ore in and get double out ....
 * or maybe you get nothing instead.
 * 
 * Probably want to use a diamond and gold to create it.
 * 
 * It costs to increase your odds ==  gold nuggets
 * 
 * Ideally I want to disable this when you reach a certain point in the game ...?????
 * Or at least make it more expensive the more you use it.
 * 
 * Probably want config options to 
 *  - disable the block
 *  - set the payout level
 *  - set the gold payout level
 *
 */
public class BlockVegas extends BlockFaced {

	public BlockVegas(String name) {
		
		super(Material.iron, name, new String[]{ "", "", "", "", "", "" } );
	}
}
