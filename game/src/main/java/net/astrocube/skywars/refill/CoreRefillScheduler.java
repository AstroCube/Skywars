package net.astrocube.skywars.refill;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.util.CountdownTimer;
import net.astrocube.skywars.api.chest.ChestSpawner;
import net.astrocube.skywars.api.game.ScoreboardModifier;
import net.astrocube.skywars.api.map.MapConfiguration;
import net.astrocube.skywars.api.refill.RefillAnnouncer;
import net.astrocube.skywars.api.refill.RefillScheduler;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Singleton
public class CoreRefillScheduler implements RefillScheduler {

    private final Plugin plugin;
    private final ChestSpawner chestSpawner;
    private final RefillAnnouncer refillAnnouncer;
    private final ScoreboardModifier scoreboardModifier;

    private final int refills;
    private final int interval;
    private final Map<String, CountdownTimer> timerMap;

    @Inject
    public CoreRefillScheduler(
            Plugin plugin, RefillAnnouncer refillAnnouncer,
            ChestSpawner chestSpawner, ScoreboardModifier scoreboardModifier) {
        this.plugin = plugin;
        this.refillAnnouncer = refillAnnouncer;
        this.chestSpawner = chestSpawner;
        this.scoreboardModifier = scoreboardModifier;

        this.timerMap = new HashMap<>();
        this.refills = plugin.getConfig().getInt("wars.refills", 0);
        this.interval = plugin.getConfig().getInt("wars.interval", 60);
    }

    @Override
    public void scheduleRefill(String match, Set<MapConfiguration.Chest> chests, Set<ProvisionedTeam> teams, int count) {
        if (count <= refills) {

            CountdownTimer timer = new CountdownTimer(
                    plugin,
                    interval,
                    (second) -> {

                        if (second.isImportantSecond()) {
                            refillAnnouncer.announceRefill(teams, second.getSecondsLeft());
                        }

                        scoreboardModifier.updateRefillCountdown(teams, second.getSecondsLeft());

                    },
                    () -> {
                        chestSpawner.spawnChests(match, chests);
                        refillAnnouncer.announceRefill(teams, 0);
                        scheduleRefill(match, chests, teams, count + 1);
                    });

            timer.scheduleTimer();
            timerMap.put(match, timer);

        } else {
            timerMap.remove(match);
        }
    }

    @Override
    public void scheduleRefill(String match, Set<MapConfiguration.Chest> chests, Set<ProvisionedTeam> teams) {
        scheduleRefill(match, chests, teams, 0);
    }

    @Override
    public CountdownTimer getRefillTask(String match) {
        return timerMap.get(match);
    }

    @Override
    public int getSchedulerTimer(String match) {
        return timerMap.containsKey(match) ? timerMap.get(match).getSecondsLeft() : -1;
    }

}
