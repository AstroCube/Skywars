package net.astrocube.game.layout.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.game.GameReadyEvent;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.event.match.MatchStartEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.map.MapConfigurationProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.game.layout.api.map.LayoutMapConfiguration;
import net.astrocube.game.layout.api.team.TeamBalancer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class GameReadyListener implements Listener {

    private @Inject FindService<Match> findService;
    private @Inject MapConfigurationProvider configurationProvider;
    private @Inject TeamBalancer teamBalancer;
    private @Inject Plugin plugin;

    @EventHandler
    public void onGameReady(GameReadyEvent event) {

        findService.find(event.getMatch()).callback(response -> {
            try {

                if (!response.isSuccessful() || !response.getResponse().isPresent()) {
                    throw new GameControlException("The requested match was not found");
                }

                LayoutMapConfiguration configuration = (LayoutMapConfiguration) configurationProvider.parseConfiguration
                        (event.getConfiguration(), LayoutMapConfiguration.class);

                Bukkit.getPluginManager().callEvent(
                        new MatchStartEvent(
                                event.getMatch(),
                                teamBalancer.generateTeams(response.getResponse().get().getPending())
                        )
                );

                plugin.getLogger().info("Spawn X:" + configuration.getSpawn().getX());
                plugin.getLogger().info("Spawn Y:" + configuration.getSpawn().getY());
                plugin.getLogger().info("Spawn Z:" + configuration.getSpawn().getZ());
                plugin.getLogger().info("Match started, now its state will be RUNNING.");

            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "There was an error processing game ready event.", e);
                Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(event.getMatch(),false));
            }

        });

    }

}
