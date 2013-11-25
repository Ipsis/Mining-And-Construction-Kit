package ipsis.mackit.item.crafting;

import ipsis.mackit.fluid.ModFluids;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fluids.FluidStack;

public class ExtractorRecipe {	
	
	int itemID;
	int metadata;
	int power;
	
	static final int DEFUALT_EXTRACTOR_POWER = 100;
	
	FluidStack red;
	FluidStack yellow;
	FluidStack blue;
	FluidStack white;
	
	public ExtractorRecipe(int itemID, int metadata) {
		this.itemID = itemID;
		this.metadata = metadata;
		power = DEFUALT_EXTRACTOR_POWER;
	}
	
	public ExtractorRecipe setPower(int power) {
		if (power > 0)
			this.power = power;
		
		return this;
	}
	
	public ExtractorRecipe setRed(int amount) {
		if (amount > 0) {		
			if (red == null) {
				red = new FluidStack(ModFluids.fluidRedDye, amount);;	
			} else {
				red.amount = amount;
			}
		}
		
		return this;
	}
	
	public ExtractorRecipe setYellow(int amount) {
		if (amount > 0) {
			if (yellow == null) {
				yellow = new FluidStack(ModFluids.fluidYellowDye, amount);;	
			} else {
				yellow.amount = amount;
			}
		}
		
		return this;
	}
	
	public ExtractorRecipe setBlue(int amount) {
		if (amount > 0) {
			if (blue == null) {
				blue = new FluidStack(ModFluids.fluidBlueDye, amount);;	
			} else {
				blue.amount = amount;
			}
		}
		
		return this;
	}
	
	public ExtractorRecipe setWhite(int amount) {
		if (amount > 0) {
			if (white == null) {
				white = new FluidStack(ModFluids.fluidWhiteDye, amount);;	
			} else {
				white.amount = amount;
			}
		}
		
		return this;
	}
	
	public int getRedAmount() {
		if (red == null)
			return 0;
		
		return red.amount;
	}
	
	public int getYellowAmount() {
		if (yellow == null)
			return 0;
		
		return yellow.amount;
	}
	
	public int getBlueAmount() {
		if (blue == null)
			return 0;
		
		return blue.amount;
	}
	
	public int getWhiteAmount() {
		if (white == null)
			return 0;
		
		return white.amount;
	}
	
	public int getRecipePower() {
		return power;
	}
	
	/**
	 * returns the equivalent fluidstack for the dye that is in this recipe
	 * or null if not used
	 */
	public FluidStack getOutput(FluidStack dye) {
		
		if (dye == null)
			return null;
		
		if (red != null && red.isFluidEqual(dye))
			return red;
		
		if (yellow != null && yellow.isFluidEqual(dye))
			return yellow;
		
		if (blue != null && blue.isFluidEqual(dye))
			return blue;
		
		if (white != null && white.isFluidEqual(dye))
			return white;
		
		return null;
	}
	
	/**
	 * returns one of the fluids for this recipe
	 */
	public FluidStack getRandomOutput() {
		List<FluidStack> tmp = new ArrayList<FluidStack>();		
		
		if (red != null)
			tmp.add(red);
		
		if (yellow != null)
			tmp.add(yellow);
		
		if (blue != null)
			tmp.add(blue);
		
		if (white != null)
			tmp.add(white);
		
		int r = (int)(Math.random() * tmp.size());
		return tmp.get(r);
	}
	
	public boolean isOutput(FluidStack dye) {
		
		if (dye == null)
			return false;
		
		if (red != null && red.isFluidEqual(dye))
			return true;
		
		if (yellow != null && yellow.isFluidEqual(dye))
			return true;
		
		if (blue != null && blue.isFluidEqual(dye))
			return true;
		
		if (white != null && white.isFluidEqual(dye))
			return true;
		
		return false;
	}
	
	@Override
	public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("<EXT id:%d meta:%d>", itemID, metadata));
        if (red != null)
        	stringBuilder.append("red " + red.toString());
        if (yellow != null)
        	stringBuilder.append("yellow " + yellow.toString());
        if (blue != null)
        	stringBuilder.append("blue " + blue.toString());
        if (white != null)
        	stringBuilder.append("white " + white.toString());
        
        return stringBuilder.toString();
	}

}
