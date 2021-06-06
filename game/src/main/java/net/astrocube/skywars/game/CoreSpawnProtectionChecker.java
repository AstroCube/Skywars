package net.astrocube.skywars.game;

import com.google.inject.Singleton;
import net.astrocube.skywars.api.game.SpawnProtectionChecker;
import net.astrocube.skywars.api.team.ProvisionedTeam;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class CoreSpawnProtectionChecker implements SpawnProtectionChecker {

	private final Set<String> protectedUsers = new HashSet<>();

	@Override
	public void registerMatch(Set<ProvisionedTeam> provisionedTeams) {
		provisionedTeams.forEach(team -> team.getMembers().forEach(member -> protectedUsers.add(member.getUser())));
	}

	@Override
	public void removeFromRegistry(String id) {
		protectedUsers.remove(id);
	}

	@Override
	public boolean hasProtection(String id) {
		return protectedUsers.contains(id);
	}

}
