package net.astrocube.skywars.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.board.Board;
import net.astrocube.api.bukkit.board.BoardProvider;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.skywars.api.game.ScoreboardModifier;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import net.astrocube.skywars.team.TeamUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class CoreScoreboardModifier implements ScoreboardModifier {

	private @Inject MessageHandler messageHandler;
	private @Inject BoardProvider boardProvider;
	private @Inject Plugin plugin;

	@Override
	public void updateInitial(Set<ProvisionedTeam> players, GameMap gameMap) {
		TeamUtils.getMatchPlayers(players).forEach(player ->
				updateScoreboard(
						player,
						gameMap.getName(),
						getRemainingFromSeconds(plugin.getConfig().getInt("wars.interval")),
						players.stream().filter(t -> t.getMembers().size() > 0)
								.collect(Collectors.toSet()).size() + ""
				)
		);
	}

	@Override
	public void updateAlive(Set<Player> players) {
		players.forEach(player -> fieldUpdate(player, "%survivors%", (players.size() - 1) + ""));
	}

	@Override
	public void updateRefillCountdown(Set<ProvisionedTeam> players, int seconds) {

		TeamUtils.getMatchPlayers(players).forEach(player -> {

			String timer = getRemainingFromSeconds(seconds);

			if (seconds == 0) {
				timer = messageHandler.get(player, "scoreboard.solo.empty");
			}

			fieldUpdate(player, "%time%", timer);

		});

	}

	private Board getBoardOrCreate(Player player, String subMode) {
		return boardProvider.get(player).orElseGet(() ->
				boardProvider.create(player, messageHandler.get(player, "scoreboard." + subMode + ".title")));
	}

	private void updateScoreboard(Player player, String map, String time, String survivors) {

		String subMode = plugin.getConfig().getString("centauri.subMode", "");

		List<String> content = messageHandler.replacingMany(
				player, "scoreboard." + subMode + ".board",
				"%time%", time,
				"%map%", map,
				"%survivors%", survivors
		);

		getBoardOrCreate(player, subMode).setLines(content);
	}

	private void fieldUpdate(Player player, String placeholder, String value) {
		// TODO: This should be changed
		String subMode = plugin.getConfig().getString("centauri.subMode", "");
		Board board = getBoardOrCreate(player, subMode);
		List<String> content = messageHandler.replacingMany(player, "scoreboard." + subMode + ".board");

		for (int i = 0; i < content.size(); i++) {
			String line = content.get(i);
			if (line.contains(placeholder)) {
				board.set(content.size() - i - 1, line.replace(placeholder, value));
			}
		}
	}

	private String getRemainingFromSeconds(int seconds) {
		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		int fSeconds = seconds % 60;

		if (hours == 0) {
			return String.format("%02d:%02d", minutes, fSeconds);
		}

		return String.format("%02d:%02d:%02d", hours, minutes, fSeconds);
	}

}
