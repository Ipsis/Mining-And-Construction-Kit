package ipsis.mackit.creativetab;

import ipsis.mackit.lib.ItemIds;
import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabMacKit extends CreativeTabs {

	public CreativeTabMacKit(int tabID, String tabLabel) {
		super(tabID, tabLabel);
	}
	
	@Override
	public int getTabIconItemIndex() {		
		return ItemIds.MACKIT_CASING;
	}
}
