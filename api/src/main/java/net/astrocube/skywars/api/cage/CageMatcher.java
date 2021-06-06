package net.astrocube.skywars.api.cage;

import net.astrocube.skywars.api.team.ProvisionedTeam;

public interface CageMatcher {

	static Cage getEmptyCage() {
		return new Cage() {
			@Override
			public String getRoof() {
				return "AIR";
			}

			@Override
			public String getWalls() {
				return "AIR";
			}

			@Override
			public String getFloor() {
				return "AIR";
			}

			@Override
			public String getName() {
				return "";
			}

			@Override
			public boolean isOverlappingCorners() {
				return true;
			}

			@Override
			public boolean pointedCorners() {
				return true;
			}
		};
	}

	Cage getHighestCage(ProvisionedTeam team);

}
