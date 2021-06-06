package net.astrocube.skywars.cage;

import com.google.inject.Singleton;
import net.astrocube.skywars.api.cage.Cage;
import net.astrocube.skywars.api.cage.CageMatcher;
import net.astrocube.skywars.api.team.ProvisionedTeam;

@Singleton
public class CoreCageMatcher implements CageMatcher {

	@Override
	public Cage getHighestCage(ProvisionedTeam team) {

		Cage cage = new Cage() {
			@Override
			public String getRoof() {
				return "GLASS";
			}

			@Override
			public String getWalls() {
				return "GLASS";
			}

			@Override
			public String getFloor() {
				return "GLASS";
			}

			@Override
			public String getName() {
				return "Default";
			}

			@Override
			public boolean isOverlappingCorners() {
				return false;
			}

			@Override
			public boolean pointedCorners() {
				return true;
			}
		};

		// TODO: Retrieve cage from stats

		return cage;
	}

}
