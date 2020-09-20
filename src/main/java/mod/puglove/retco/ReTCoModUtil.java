package mod.puglove.retco;

import net.minecraft.util.Direction;
import net.minecraftforge.energy.EnergyStorage;

public final class ReTCoModUtil {
  public static final Direction[] DIRECTIONS = Direction.values();

	/**
	 * This method calculates a comparator output for how "full" the energy storage is.
	 *
	 * @param energy The energy storage to test.
	 * @return A redstone value in the range [0,15] representing how "full" this energy storage is.
	 */
	public static int calcRedstoneFromEnergyStorage(final EnergyStorage energy) {
		if (energy == null)
			return 0;
		return Math.round(energy.getEnergyStored() / ((float) energy.getMaxEnergyStored()) * 15F);
	}
}
