package ipsis.mackit.item.crafting;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class StamperManager {
	
	public enum EnumStamperColours {
		BLACK, RED, GREEN, BROWN, BLUE, PURPLE, CYAN, LIGHT_GRAY, GRAY, PINK, LIME, YELLOW,
		LIGHT_BLUE, MAGENTA, ORANGE, WHITE
	}

	private static final StamperManager instance = new StamperManager();
	
	ItemStack[] recipes = new ItemStack[16];
	
	private StamperManager() {
		recipes[0] = new ItemStack(Item.dyePowder, 0); /* black */
		recipes[1] = new ItemStack(Item.dyePowder, 1); /* red */
		recipes[2] = new ItemStack(Item.dyePowder, 2); /* green */
		recipes[3] = new ItemStack(Item.dyePowder, 3); /* brown */
		recipes[4] = new ItemStack(Item.dyePowder, 4); /* blue */
		recipes[5] = new ItemStack(Item.dyePowder, 5); /* purple */
		recipes[6] = new ItemStack(Item.dyePowder, 6); /* cyan */
		recipes[7] = new ItemStack(Item.dyePowder, 7); /* light gray */
		recipes[8] = new ItemStack(Item.dyePowder, 8); /* gray */
		recipes[9] = new ItemStack(Item.dyePowder, 9); /* pink */
		recipes[10] = new ItemStack(Item.dyePowder, 10); /* lime */
		recipes[11] = new ItemStack(Item.dyePowder, 11); /* yellow */
		recipes[12] = new ItemStack(Item.dyePowder, 12); /* light blue */
		recipes[13] = new ItemStack(Item.dyePowder, 13); /* magenta */
		recipes[14] = new ItemStack(Item.dyePowder, 14); /* orange */
		recipes[15] = new ItemStack(Item.dyePowder, 15); /* white */
	}
	
    public static final StamperManager getInstance() {

        return instance;
    }
    
    public ItemStack getOutput(EnumStamperColours colour) {
    	return recipes[colour.ordinal()];
    }
}
