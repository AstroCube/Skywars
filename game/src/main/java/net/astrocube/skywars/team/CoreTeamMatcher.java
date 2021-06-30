package net.astrocube.skywars.team;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.map.configuration.CoordinatePoint;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.skywars.api.map.MapConfiguration;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import net.astrocube.skywars.api.team.TeamMatcher;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class CoreTeamMatcher implements TeamMatcher {

	private @Inject MessageHandler messageHandler;

	@Override
	public Set<ProvisionedTeam> linkTeams(Set<MatchDoc.Team> matchTeam, MapConfiguration configuration) {
		return matchTeam.stream().map(team -> {

			Optional<MapConfiguration.ConfigurableTeam> optionalConfigurableTeam = configuration
				.getTeams().stream().filter(configurableTeam -> configurableTeam.getName().equalsIgnoreCase(team.getName())).findFirst();

			if (!optionalConfigurableTeam.isPresent()) {
				TeamUtils.kickVoidedPlayers(team.getMembers(), messageHandler, "team.error.comparing");
				return null;
			}

			MapConfiguration.ConfigurableTeam configurableTeam =
				optionalConfigurableTeam.get();

			return new ProvisionedTeam() {
				@Override
				public Set<MatchDoc.TeamMember> getMembers() {
					return team.getMembers();
				}

				@Override
				public CoordinatePoint getSpawn() {
					return configurableTeam.getSpawn();
				}

				@Override
				public String getName() {
					return configurableTeam.getName();
				}

				@Override
				public String getColor() {
					return configurableTeam.getColor();
				}
			};

		}).filter(Objects::nonNull).collect(Collectors.toSet());
	}
}