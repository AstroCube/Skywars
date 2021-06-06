package net.astrocube.skywars.api.chest.tier;

import net.astrocube.skywars.api.custom.Customizable;
import net.astrocube.skywars.api.custom.SerializableItem;
import net.astrocube.skywars.api.map.MapConfiguration;

import java.util.Set;

public interface ChestTier extends Customizable {

	/**
	 * @return set of items to be distributed at a chest.
	 */
	Set<Item> getProbabilities();

	/**
	 * @return configured tier name.
	 */
	MapConfiguration.Chest.Tier getTier();

	interface Item {

		/**
		 * @return material of the item to be created.
		 */
		SerializableItem getItem();

		/**
		 * @return minimum probability of the item quantity to be given.
		 */
		int getMinimum();

		/**
		 * @return maximum probability of the item quantity to be given.
		 */
		int getMaximum();

		/**
		 * @return chance of the item to be given at a chest.
		 */
		int getChance();

	}

}
