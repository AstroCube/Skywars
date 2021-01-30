package net.astrocube.skywars.refill;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.skywars.api.refill.RefillAnnouncer;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import net.astrocube.skywars.team.TeamUtils;

import java.util.Set;

@Singleton
public class CoreRefillAnnouncer implements RefillAnnouncer {

    private @Inject MessageHandler messageHandler;

    @Override
    public void announceRefill(Set<ProvisionedTeam> teams, int seconds) {
        TeamUtils.getMatchPlayers(teams).forEach(user -> {
            if (seconds != 0) {
                messageHandler.sendReplacing(
                        user, AlertModes.MUTED, "match.refill",
                        "%%seconds%%", seconds + ""
                );
            } else {
                messageHandler.send(user, AlertModes.INFO, "match.refilled");
            }
        });
    }

}
