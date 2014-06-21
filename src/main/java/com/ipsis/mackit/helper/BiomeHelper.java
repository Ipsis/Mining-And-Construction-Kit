package com.ipsis.mackit.helper;


import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

import java.util.HashMap;

public class BiomeHelper {

    /** Map a biome type to a color */
    private static final HashMap<BiomeDictionary.Type, DyeHelper.DyeColor> colorMap = new HashMap<BiomeDictionary.Type, DyeHelper.DyeColor>();
    static {
        colorMap.put(BiomeDictionary.Type.FOREST, DyeHelper.DyeColor.GREEN);
        colorMap.put(BiomeDictionary.Type.PLAINS, DyeHelper.DyeColor.BLACK);
        colorMap.put(BiomeDictionary.Type.MOUNTAIN, DyeHelper.DyeColor.GRAY);
        colorMap.put(BiomeDictionary.Type.HILLS, DyeHelper.DyeColor.BLACK);
        colorMap.put(BiomeDictionary.Type.SWAMP, DyeHelper.DyeColor.GREEN);
        colorMap.put(BiomeDictionary.Type.WATER, DyeHelper.DyeColor.LIGHTBLUE);
        colorMap.put(BiomeDictionary.Type.DESERT, DyeHelper.DyeColor.YELLOW);
        colorMap.put(BiomeDictionary.Type.FROZEN, DyeHelper.DyeColor.BLACK);
        colorMap.put(BiomeDictionary.Type.JUNGLE, DyeHelper.DyeColor.LIME);
        colorMap.put(BiomeDictionary.Type.WASTELAND, DyeHelper.DyeColor.BLACK);
        colorMap.put(BiomeDictionary.Type.BEACH, DyeHelper.DyeColor.YELLOW);
        colorMap.put(BiomeDictionary.Type.NETHER, DyeHelper.DyeColor.RED);
        colorMap.put(BiomeDictionary.Type.END, DyeHelper.DyeColor.WHITE);
        colorMap.put(BiomeDictionary.Type.MUSHROOM, DyeHelper.DyeColor.RED);
        colorMap.put(BiomeDictionary.Type.MAGICAL, DyeHelper.DyeColor.PURPLE);

    }

    public static DyeHelper.DyeColor getColor(BiomeGenBase biome) {

        BiomeDictionary.Type types[] = BiomeDictionary.getTypesForBiome(biome);

        if (types != null && types.length > 0) {

            /* Some biomes have multiple types, but we just use the first one */
            if (colorMap.containsKey(types[0]))
                return colorMap.get(types[0]);
        }

        return DyeHelper.DyeColor.YELLOW;
    }
}
