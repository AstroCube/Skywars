package net.astrocube.skywars.refill;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.translation.mode.AlertMode;
import net.astrocube.skywars.api.refill.RefillAnnouncer;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import net.astrocube.skywars.team.TeamUtils;
import org.bukkit.entity.Player;

import java.util.Set;

@Singleton
public class CoreRefillAnnouncer implements RefillAnnouncer {

    private @Inject MessageHandler<Player> messageHandler;

    @Override
    public void announceRefill(Set<ProvisionedTeam> teams, int seconds) {
        TeamUtils.getMatchPlayers(teams).forEach(user -> {
            if (seconds != 0) {
                messageHandler.send(user, AlertMode.MUTED, "match.refill");
            } else {
                messageHandler.send(user, AlertMode.INFO, "match.refilled");
            }
        });
    }

}
