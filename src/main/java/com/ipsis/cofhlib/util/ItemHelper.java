package com.ipsis.cofhlib.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Contains various helper functions to assist with {@link Item} and
 * {@link ItemStack} manipulation and interaction.
 * 
 * @author King Lemming
 * 
 */
public final class ItemHelper {

	public static final String BLOCK = "block";
	public static final String ORE = "ore";
	public static final String DUST = "dust";
	public static final String INGOT = "ingot";
	public static final String NUGGET = "nugget";
	public static final String LOG = "log";

	private ItemHelper() {

	}

	public static ItemStack cloneStack(Item item, int stackSize) {

		if (item == null) {
			return null;
		}
		ItemStack stack = new ItemStack(item, stackSize);

		return stack;
	}

	public static ItemStack cloneStack(ItemStack stack, int stackSize) {

		if (stack == null) {
			return null;
		}
		ItemStack retStack = stack.copy();
		retStack.stackSize = stackSize;

		return retStack;
	}

	public static ItemStack copyTag(ItemStack container, ItemStack other) {

		if (other != null && other.stackTagCompound != null) {
			container.stackTagCompound = (NBTTagCompound) other.stackTagCompound
					.copy();
		}
		return container;
	}

	public static NBTTagCompound setItemStackTagName(NBTTagCompound tag,
			String name) {

		if (name == "") {
			return null;
		}
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		if (!tag.hasKey("display")) {
			tag.setTag("display", new NBTTagCompound());
		}
		tag.getCompoundTag("display").setString("Name", name);

		return tag;
	}

	public static String getNameFromItemStack(ItemStack stack) {

		if (stack == null || stack.stackTagCompound == null
				|| !stack.stackTagCompound.hasKey("display")) {
			return "";
		}
		return stack.stackTagCompound.getCompoundTag("display").getString(
				"Name");
	}

	public static ItemStack consumeItem(ItemStack stack) {

		if (stack.stackSize == 1) {
			if (stack.getItem().hasContainerItem(stack)) {
				return stack.getItem().getContainerItem(stack);
			} else {
				return null;
			}
		}
		stack.splitStack(1);
		return stack;
	}

	/**
	 * This prevents an overridden getDamage() call from messing up metadata
	 * acquisition.
	 */
	public static int getItemDamage(ItemStack stack) {

		return Items.diamond.getDamage(stack);
	}

	/**
	 * Gets a vanilla CraftingManager result.
	 */
	public static ItemStack findMatchingRecipe(InventoryCrafting inv,
			World world) {

		ItemStack[] dmgItems = new ItemStack[2];
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			if (inv.getStackInSlot(i) != null) {
				if (dmgItems[0] == null) {
					dmgItems[0] = inv.getStackInSlot(i);
				} else {
					dmgItems[1] = inv.getStackInSlot(i);
					break;
				}
			}
		}
		if (dmgItems[0] == null || dmgItems[0].getItem() == null) {
			return null;
		} else if (dmgItems[1] != null
				&& dmgItems[0].getItem() == dmgItems[1].getItem()
				&& dmgItems[0].stackSize == 1 && dmgItems[1].stackSize == 1
				&& dmgItems[0].getItem().isRepairable()) {
			Item theItem = dmgItems[0].getItem();
			int var13 = theItem.getMaxDamage()
					- dmgItems[0].getItemDamageForDisplay();
			int var8 = theItem.getMaxDamage()
					- dmgItems[1].getItemDamageForDisplay();
			int var9 = var13 + var8 + theItem.getMaxDamage() * 5 / 100;
			int var10 = Math.max(0, theItem.getMaxDamage() - var9);

			return new ItemStack(dmgItems[0].getItem(), 1, var10);
		} else {
			IRecipe recipe;
			for (int i = 0; i < CraftingManager.getInstance().getRecipeList()
					.size(); ++i) {
				recipe = (IRecipe) CraftingManager.getInstance()
						.getRecipeList().get(i);

				if (recipe.matches(inv, world)) {
					return recipe.getCraftingResult(inv);
				}
			}
			return null;
		}
	}

	/**
	 * Get a hashcode based on the ItemStack's ID and Metadata. As both of these
	 * are shorts, this should be collision-free for non-NBT sensitive
	 * ItemStacks.
	 * 
	 * @param stack
	 *            The ItemStack to get a hashcode for.
	 * @return The hashcode.
	 */
	public static int getHashCode(ItemStack stack) {

		return stack.getItemDamage()
				| Item.getIdFromItem(stack.getItem()) << 16;
	}

	/**
	 * Get a hashcode based on an ID and Metadata pair. As both of these are
	 * shorts, this should be collision-free if NBT is not involved.
	 * 
	 * @param id
	 *            ID value to use.
	 * @param metadata
	 *            Metadata value to use.
	 * @return The hashcode.
	 */
	public static int getHashCode(int id, int metadata) {

		return metadata | id << 16;
	}

	/**
	 * Extract the ID from a hashcode created from one of the getHashCode()
	 * methods in this class.
	 */
	public static int getIDFromHashCode(int hashCode) {

		return hashCode >>> 16;
	}

	/**
	 * Extract the Metadata from a hashcode created from one of the
	 * getHashCode() methods in this class.
	 */
	public static int getMetaFromHashCode(int hashCode) {

		return hashCode & 0xFF;
	}

	/* ORE DICTIONARY FUNCTIONS */
	public static boolean hasOreName(ItemStack stack) {

		return !getOreName(stack).equals("Unknown");
	}

	public static String getOreName(ItemStack stack) {

		return OreDictionary.getOreName(OreDictionary.getOreID(stack));
	}

	public static boolean isOreID(ItemStack stack, int oreID) {

		return OreDictionary.getOreID(stack) == oreID;
	}

	public static boolean isOreName(ItemStack stack, String oreName) {

		return OreDictionary.getOreName(OreDictionary.getOreID(stack)).equals(
				oreName);
	}

	public static boolean oreNameExists(String oreName) {

		return !OreDictionary.getOres(oreName).isEmpty();
	}

	public static ItemStack getOre(String oreName) {

		if (!oreNameExists(oreName)) {
			return null;
		}
		return cloneStack(OreDictionary.getOres(oreName).get(0), 1);
	}

	public static boolean isBlock(ItemStack stack) {

		return getOreName(stack).startsWith(BLOCK);
	}

	public static boolean isOre(ItemStack stack) {

		return getOreName(stack).startsWith(ORE);
	}

	public static boolean isDust(ItemStack stack) {

		return getOreName(stack).startsWith(DUST);
	}

	public static boolean isIngot(ItemStack stack) {

		return getOreName(stack).startsWith(INGOT);
	}

	public static boolean isNugget(ItemStack stack) {

		return getOreName(stack).startsWith(NUGGET);
	}

	public static boolean isLog(ItemStack stack) {

		return getOreName(stack).startsWith(LOG);
	}

	/* CRAFTING HELPER FUNCTIONS */
	public static boolean addGearRecipe(ItemStack gear, String ingot) {

		if (!oreNameExists(ingot)) {
			return false;
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(gear, new Object[] { " X ",
				"XIX", " X ", 'X', ingot, 'I', Items.iron_ingot }));
		return true;
	}

	public static boolean addReverseStorageRecipe(ItemStack nine, String one) {

		if (oreNameExists(one)) {
			return false;
		}
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(
				nine, 9), new Object[] { one }));
		return true;
	}

	public static boolean addStorageRecipe(ItemStack one, String nine) {

		if (!oreNameExists(nine)) {
			return false;
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(one, new Object[] { "III",
				"III", "III", 'I', nine }));
		return true;
	}

	public static void registerWithHandlers(String oreName, ItemStack stack) {

		OreDictionary.registerOre(oreName, stack);
		GameRegistry.registerCustomItemStack(oreName, stack);
		FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", stack);
	}

	/**
	 * Determine if a player is holding a registered Fluid Container.
	 */
	public static final boolean isPlayerHoldingFluidContainer(
			EntityPlayer player) {

		return FluidContainerRegistry.isContainer(player
				.getCurrentEquippedItem());
	}

	public static final boolean isPlayerHoldingFluidContainerItem(
			EntityPlayer player) {

		return FluidHelper.isPlayerHoldingFluidContainerItem(player);
	}

	public static final boolean isPlayerHoldingEnergyContainerItem(
			EntityPlayer player) {

		return EnergyHelper.isPlayerHoldingEnergyContainerItem(player);
	}

	public static Item getItemFromStack(ItemStack theStack) {

		return theStack == null ? null : theStack.getItem();
	}

	public static boolean areItemsEqual(Item itemA, Item itemB) {

		if (itemA == itemB) {
			return true;
		}
		if (itemA == null | itemB == null) {
			return false;
		}
		return itemA.equals(itemB);
	}

	public static final boolean isPlayerHoldingItem(Class<?> item,
			EntityPlayer player) {

		return item
				.isInstance(getItemFromStack(player.getCurrentEquippedItem()));
	}

	/**
	 * Determine if a player is holding an ItemStack of a specific Item type.
	 */
	public static final boolean isPlayerHoldingItem(Item item,
			EntityPlayer player) {

		return areItemsEqual(item,
				getItemFromStack(player.getCurrentEquippedItem()));
	}

	/**
	 * Determine if a player is holding an ItemStack with a specific Item ID,
	 * Metadata, and NBT.
	 */
	public static final boolean isPlayerHoldingItemStack(ItemStack stack,
			EntityPlayer player) {

		return itemsEqualWithMetadata(stack, player.getCurrentEquippedItem());
	}

	public static boolean itemsEqualWithoutMetadata(ItemStack stackA,
			ItemStack stackB) {

		if (stackA == stackB) {
			return true;
		}
		if (stackA == null | stackB == null) {
			return false;
		}
		return stackA.getItem().equals(stackB.getItem());
	}

	public static boolean itemsEqualWithoutMetadata(ItemStack stackA,
			ItemStack stackB, boolean checkNBT) {

		return itemsEqualWithoutMetadata(stackA, stackB)
				&& (!checkNBT || doNBTsMatch(stackA.stackTagCompound,
						stackB.stackTagCompound));
	}

	public static boolean itemsEqualWithMetadata(ItemStack stackA,
			ItemStack stackB) {

		return itemsEqualWithoutMetadata(stackA, stackB)
				&& (stackA.getHasSubtypes() == false || stackA.getItemDamage() == stackB
						.getItemDamage());
	}

	public static boolean itemsEqualWithMetadata(ItemStack stackA,
			ItemStack stackB, boolean checkNBT) {

		if (stackA == stackB) {
			return true;
		}
		return itemsEqualWithMetadata(stackA, stackB)
				&& (!checkNBT || doNBTsMatch(stackA.stackTagCompound,
						stackB.stackTagCompound));
	}

	public static boolean doNBTsMatch(NBTTagCompound nbtA, NBTTagCompound nbtB) {

		if (nbtA == nbtB) {
			return true;
		}
		if (nbtA != null & nbtB != null) {
			return nbtA.equals(nbtB);
		}
		return false;
	}

	public static boolean itemsEqualForCrafting(ItemStack stackA,
			ItemStack stackB) {

		return itemsEqualWithoutMetadata(stackA, stackB)
				&& (!stackA.getHasSubtypes() || ((stackA.getItemDamage() == OreDictionary.WILDCARD_VALUE || stackB
						.getItemDamage() == OreDictionary.WILDCARD_VALUE) || stackB
						.getItemDamage() == stackA.getItemDamage()));
	}

	public static boolean craftingEquivalent(ItemStack checked,
			ItemStack source, String oreDict, ItemStack output) {

		if (itemsEqualForCrafting(checked, source)) {
			return true;
		} else if (output != null && isBlacklist(output)) {
			return false;
		} else if (oreDict == null || oreDict.equals("Unknown")) {
			return false;
		} else {
			return getOreName(checked).equalsIgnoreCase(oreDict);
		}
	}

	public static boolean doOreIDsMatch(ItemStack stackA, ItemStack stackB) {

		int id = OreDictionary.getOreID(stackA);
		return id >= 0 && id == OreDictionary.getOreID(stackB);
	}

	public static boolean isBlacklist(ItemStack output) {

		Item item = output.getItem();
		return Item.getItemFromBlock(Blocks.birch_stairs) == item
				|| Item.getItemFromBlock(Blocks.jungle_stairs) == item
				|| Item.getItemFromBlock(Blocks.oak_stairs) == item
				|| Item.getItemFromBlock(Blocks.spruce_stairs) == item
				|| Item.getItemFromBlock(Blocks.planks) == item
				|| Item.getItemFromBlock(Blocks.wooden_slab) == item;
	}

	public static String getItemNBTString(ItemStack theItem, String nbtKey,
			String invalidReturn) {

		return theItem.stackTagCompound != null
				&& theItem.stackTagCompound.hasKey(nbtKey) ? theItem.stackTagCompound
				.getString(nbtKey) : invalidReturn;
	}

	/**
	 * Adds Inventory information to ItemStacks which themselves hold things.
	 * Called in addInformation().
	 */
	public static void addInventoryInformation(ItemStack stack,
			List<String> list) {

		addInventoryInformation(stack, list, 0, Integer.MAX_VALUE);
	}

	public static void addInventoryInformation(ItemStack stack,
			List<String> list, int minSlot, int maxSlot) {

		if (stack.stackTagCompound.hasKey("Inventory")
				&& stack.stackTagCompound.getTagList("Inventory",
						stack.stackTagCompound.getId()).tagCount() > 0) {

			if (StringHelper.displayShiftForDetail
					&& !StringHelper.isShiftKeyDown()) {
				list.add(StringHelper.shiftForInfo);
			}
			if (!StringHelper.isShiftKeyDown()) {
				return;
			}
			list.add(StringHelper.localize("info.cofh.contents") + ":");
			NBTTagList nbtList = stack.stackTagCompound.getTagList("Inventory",
					stack.stackTagCompound.getId());
			ItemStack curStack;
			ItemStack curStack2;

			ArrayList<ItemStack> containedItems = new ArrayList<ItemStack>();

			boolean[] visited = new boolean[nbtList.tagCount()];

			for (int i = 0; i < nbtList.tagCount(); i++) {
				NBTTagCompound tag = nbtList.getCompoundTagAt(i);
				int slot = tag.getInteger("Slot");

				if (visited[i] || slot < minSlot || slot > maxSlot) {
					continue;
				}
				visited[i] = true;
				curStack = ItemStack.loadItemStackFromNBT(tag);

				if (curStack == null) {
					continue;
				}
				containedItems.add(curStack);
				for (int j = 0; j < nbtList.tagCount(); j++) {
					NBTTagCompound tag2 = nbtList.getCompoundTagAt(j);
					@SuppressWarnings("unused")
					int slot2 = tag.getInteger("Slot");
					// TODO: ??

					if (visited[j] || slot < minSlot || slot > maxSlot) {
						continue;
					}
					curStack2 = ItemStack.loadItemStackFromNBT(tag2);

					if (curStack2 == null) {
						continue;
					}
					if (itemsEqualWithMetadata(curStack, curStack2)) {
						curStack.stackSize += curStack2.stackSize;
						visited[j] = true;
					}
				}
			}
			for (ItemStack item : containedItems) {
				int maxStackSize = item.getMaxStackSize();

				if (!StringHelper.displayStackCount
						|| item.stackSize < maxStackSize || maxStackSize == 1) {
					list.add(" " + StringHelper.BRIGHT_GREEN + item.stackSize
							+ " " + StringHelper.GRAY + item.getDisplayName());
				} else {
					if (item.stackSize % maxStackSize != 0) {
						list.add(" " + StringHelper.BRIGHT_GREEN + maxStackSize
								+ "x" + item.stackSize / maxStackSize + "+"
								+ item.stackSize % maxStackSize + " "
								+ StringHelper.GRAY + item.getDisplayName());
					} else {
						list.add(" " + StringHelper.BRIGHT_GREEN + maxStackSize
								+ "x" + item.stackSize / maxStackSize + " "
								+ StringHelper.GRAY + item.getDisplayName());
					}
				}
			}
		}
	}

}
