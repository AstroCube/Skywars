package net.astrocube.skywars.api.chest;

import net.astrocube.skywars.api.map.MapConfiguration;
import org.bukkit.block.Chest;

public interface ChestFiller {

	/**
	 * Fills the provided chest with its corresponding tier
	 * @param chest    to be affected
	 * @param mapChest to be filled
	 */
	void fillChest(MapConfiguration.Chest chest, Chest mapChest);

}
