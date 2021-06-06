package net.astrocube.skywars.cage;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.skywars.api.cage.Cage;
import net.astrocube.skywars.api.cage.CageSpawner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;


@Singleton
public class CoreCageSpawner implements CageSpawner {

	private @Inject Plugin plugin;

	@Override
	public void spawnCage(Cage cage, Location location, int area) {
		Bukkit.getScheduler().runTask(plugin, () -> spawn(cage, location, area));
	}

	private void spawn(Cage cage, Location spawn, int diameter) {

		if (diameter % 2 == 0) {
			diameter -= 1;
		}

		int minY = spawn.getBlockY() - 1;
		int maxY = spawn.getBlockY() + 2;

		for (int y = minY; y <= maxY; y++) {
			spawn.setY(y);

			if (y == minY || y == maxY) {
				setSolidLevel(cage, spawn, diameter);

				continue;
			}
			setWallLevel(cage, spawn, diameter);
		}

	}

	private void setSolidLevel(Cage cage, Location levelCenter, int diameter) {
		int radius = diameter / 2;
		levelCenter = levelCenter.clone();

		ItemStack floor = generateStack(cage.getFloor());

		if (cage.pointedCorners()) {
			if (radius == 1) {
				levelCenter.getBlock().setType(floor.getType());
			} else {
				fill(levelCenter, radius, floor, floor);
			}
		} else if (cage.isOverlappingCorners()) {
			fill(levelCenter, radius, floor, generateStack(cage.getWalls()));
		} else {
			fill(levelCenter, radius, floor, floor);
		}
	}

	private void setWallLevel(Cage cage, Location levelCenter, int diameter) {
		int radius = (diameter / 2) + 1;
		levelCenter = levelCenter.clone();

		ItemStack wall = generateStack(cage.getWalls());

		if (cage.pointedCorners()) {
			fill(levelCenter, radius, new ItemStack(Material.AIR), wall, new ItemStack(Material.AIR));
		} else {
			fill(levelCenter, radius, new ItemStack(Material.AIR), wall);
		}
	}

	private void fill(Location center, int radius, ItemStack centerItem, ItemStack edgesItem) {
		fill(center, radius, centerItem, edgesItem, edgesItem);
	}

	private void fill(Location center, int radius, ItemStack centerItem, ItemStack edgesItem, ItemStack cornerItem) {
		Location currentBlock = center.clone();

		int maxX = center.getBlockX() + radius;
		int maxZ = center.getBlockZ() + radius;
		int minX = center.getBlockX() - radius;
		int minZ = center.getBlockZ() - radius;

		for (int x = minX; x <= maxX; x++) {
			currentBlock.setX(x);

			for (int z = minZ; z <= maxZ; z++) {
				currentBlock.setZ(z);

				if ((x == minX || x == maxX) && (z == minZ || z == maxZ)) {
					currentBlock.getBlock().setType(cornerItem.getType());
					continue;
				}

				if ((x == minX || x == maxX) || (z == minZ || z == maxZ)) {
					currentBlock.getBlock().setType(edgesItem.getType());
					continue;
				}
				currentBlock.getBlock().setType(centerItem.getType());
			}
		}
	}

	private ItemStack generateStack(String base) {

		ItemStack stack = new ItemStack(Material.GLASS);
		try {

			String[] divided = base.split(":");

			if (divided.length == 2) {
				return new ItemStack(Material.valueOf(divided[0].toUpperCase()), Short.parseShort(divided[1]));
			} else {
				return new ItemStack(Material.valueOf(divided[0]));
			}

		} catch (IllegalArgumentException ignore) {
		}

		return stack;
	}

}
