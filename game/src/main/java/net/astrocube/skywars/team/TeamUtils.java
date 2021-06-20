package net.astrocube.skywars.team;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.map.configuration.CoordinatePoint;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class TeamUtils {

	/**
	 * Kick some members if there is an error during match process.
	 * @param members to be kicked
	 * @param handler where the language will be provided
	 * @param message to be used as translation
	 */
	public static void kickVoidedPlayers(Set<MatchDoc.TeamMember> members, MessageHandler handler, String message) {
		members.forEach(member -> {

			Player player = Bukkit.getPlayerByIdentifier(member.getUser());

			if (player != null) {
				player.kickPlayer(handler.get(player, message));
			}

		});
	}

	public static Location generateSpawn(CoordinatePoint coordinatePoint, World world) {
		return new Location(
				world,
				coordinatePoint.getX(),
				coordinatePoint.getY(),
				coordinatePoint.getZ()
		);
	}

	public static Set<Player> getMatchPlayers(Set<ProvisionedTeam> teams) {
		return teams.stream()
				.flatMap(team ->
						team.getMembers().stream()
								.map(member -> Bukkit.getPlayerByIdentifier(member.getUser()))
				)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
	}

}
