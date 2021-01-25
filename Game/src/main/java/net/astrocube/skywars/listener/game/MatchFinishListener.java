package net.astrocube.skywars.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.match.MatchFinishEvent;
import net.astrocube.api.bukkit.util.CountdownTimer;
import net.astrocube.skywars.api.game.VictoryAnnouncer;
import net.astrocube.skywars.api.refill.RefillScheduler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Optional;

public class MatchFinishListener implements Listener {

    private @Inject RefillScheduler refillScheduler;
    private @Inject VictoryAnnouncer announcer;

    @EventHandler
    public void onMatchFinish(MatchFinishEvent event) {

        Optional.ofNullable(refillScheduler.getRefillTask(event.getMatch()))
                .ifPresent(CountdownTimer::cancelCountdown);

        event.getWinners().stream().map(Bukkit::getPlayerByIdentifier).forEach(player -> {
            announcer.sendVictoryTitle(player);
        });

    }

}
