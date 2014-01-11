package com.ipsis.mackit.client.gui.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
* Slot which players can only remove items from.
*
* @author King Lemming
*
* From CoFHLib/cofh/gui/slot/SlotOutput.java
*
*/
public class SlotOutput extends Slot {

        public SlotOutput(IInventory inventory, int x, int y, int z) {

                super(inventory, x, y, z);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {

                return false;
        }

}
