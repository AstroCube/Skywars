package net.astrocube.skywars.api.refill;

import net.astrocube.api.bukkit.util.CountdownTimer;
import net.astrocube.skywars.api.map.MapConfiguration;
import net.astrocube.skywars.api.team.ProvisionedTeam;

import java.util.Set;

public interface RefillScheduler {

    void scheduleRefill(String match, Set<MapConfiguration.Chest> chests, Set<ProvisionedTeam> teams, int count);

    void scheduleRefill(String match, Set<MapConfiguration.Chest> chests, Set<ProvisionedTeam> teams);

    CountdownTimer getRefillTask(String match);

    int getSchedulerTimer(String match);

}
