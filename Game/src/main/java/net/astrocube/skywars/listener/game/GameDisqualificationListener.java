package net.astrocube.skywars.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.spectator.SpectatorAssignEvent;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.skywars.api.event.PlayerDisqualificationEvent;
import net.astrocube.skywars.api.game.DisqualificationHandler;
import net.astrocube.skywars.api.game.ScoreboardModifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

public class GameDisqualificationListener implements Listener {

    private @Inject DisqualificationHandler disqualificationHandler;
    private @Inject ActualMatchProvider actualMatchProvider;
    private @Inject ScoreboardModifier scoreboardModifier;
    private @Inject Plugin plugin;

    @EventHandler
    public void onGameDisqualification(PlayerDisqualificationEvent event) {

        try {

            Optional<Match> match = actualMatchProvider.provide(event.getPlayer().getDatabaseIdentifier());

            if (match.isPresent()) {


                Bukkit.getPluginManager().callEvent(new SpectatorAssignEvent(event.getPlayer(), match.get().getId()));

                Set<Player> players = MatchParticipantsProvider.getOnlinePlayers(match.get());
                scoreboardModifier.updateAlive(players);
                disqualificationHandler.disqualify(event.getPlayer().getDatabaseIdentifier());

                players.forEach(player ->
                        disqualificationHandler.alertDisqualify(player, event.getPlayer(), event.getKiller()));
            }

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error while obtaining player match", e);
        }

    }

}
