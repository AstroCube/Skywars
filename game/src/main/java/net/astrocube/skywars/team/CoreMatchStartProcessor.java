package net.astrocube.skywars.team;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.match.MatchStartEvent;
import net.astrocube.api.bukkit.util.CountdownTimer;
import net.astrocube.skywars.api.cage.MatchCageSpawner;
import net.astrocube.skywars.api.team.MatchStartProcessor;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Set;

@Singleton
public class CoreMatchStartProcessor implements MatchStartProcessor {

	private @Inject MatchCageSpawner matchCageSpawner;
	private @Inject MessageHandler messageHandler;
	private @Inject Plugin plugin;

	@Override
	public void scheduleStart(Set<ProvisionedTeam> teams, String match) {

		CountdownTimer timer = new CountdownTimer(
				plugin,
				10,
				(time) -> TeamUtils.getMatchPlayers(teams).forEach(player -> announceCageOpen(player, time.getSecondsLeft())),
				() -> {
					matchCageSpawner.remove(match, teams);
					Bukkit.getPluginManager().callEvent(new MatchStartEvent(match));
				}
		);

		timer.scheduleTimer();
	}

	private void announceCageOpen(Player player, int time) {
		messageHandler.sendReplacing(
				player, "match.cages",
				"%seconds%", time
		);
	}

}
