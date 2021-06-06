package net.astrocube.skywars.refill;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.skywars.api.refill.RefillAnnouncer;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import net.astrocube.skywars.team.TeamUtils;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.function.Consumer;

@Singleton
public class CoreRefillAnnouncer implements RefillAnnouncer {

	private @Inject MessageHandler messageHandler;

	@Override
	public void announceRefill(Set<ProvisionedTeam> teams, int seconds) {
		if (seconds != 0) {
			sendMessage(teams, player -> messageHandler.sendReplacing(
					player, "match.refill",
					"%seconds%", (seconds + "")
			));
		} else {
			sendMessage(teams, player -> messageHandler.sendIn(player, AlertModes.INFO, "match.refilled"));
		}
	}

	private void sendMessage(Set<ProvisionedTeam> teams, Consumer<Player> consumer) {
		TeamUtils.getMatchPlayers(teams).forEach(consumer);
	}

}
