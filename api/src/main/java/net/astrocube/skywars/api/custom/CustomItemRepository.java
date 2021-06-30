package net.astrocube.skywars.api.custom;

import java.util.Set;

public interface CustomItemRepository<T extends Customizable> {

	/**
	 * Generates item repository cache
	 */
	void generate();

	/**
	 * @return set of generated items.
	 */
	Set<T> getRegisteredItems();
}