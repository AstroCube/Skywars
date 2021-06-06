package net.astrocube.skywars.api.perk;

import net.astrocube.api.bukkit.perk.AbstractPerk;

import java.util.Optional;
import java.util.Set;

public interface SkyWarsPerkManifest extends AbstractPerk {

	/**
	 * Returns the bought kits
	 * identifiers
	 */
	Set<String> getBoughtKits();

	/**
	 * Returns the last selected
	 * kit identifier
	 */
	Optional<String> getSelectedKit();

	/**
	 * Sets the selected kit identifier
	 */
	void setSelectedKit(String kitIdentifier);

	/**
	 * Returns the current money
	 * amount of the player
	 */
	int getMoney();

	/**
	 * Sets the new money amount
	 * of the player
	 */
	void setMoney(int money);

}
