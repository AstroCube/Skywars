package net.astrocube.skywars.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.skywars.api.game.ScoreboardModifier;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import net.astrocube.skywars.team.TeamUtils;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftGameBoard;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.GameBoard;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Singleton
public class CoreScoreboardModifier implements ScoreboardModifier {

    private @Inject MessageHandler messageHandler;
    private @Inject Plugin plugin;

    @Override
    public void createBoard(Set<ProvisionedTeam> players, GameMap gameMap) {
        TeamUtils.getMatchPlayers(players).forEach(player ->
                setupScoreboard(
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

    private void setupScoreboard(Player player, String map, String time, String survivors) {

        String subMode = plugin.getConfig().getString("centauri.subMode", "");

        GameBoard board = new CraftGameBoard(
                messageHandler.get(
                        player,
                        "scoreboard." + subMode + ".title"
                )
        );

        StringList list = messageHandler.replacingMany(player,
                "scoreboard." + subMode + ".board",
                "%time%", time,
                "%map%", map,
                "%survivors%", survivors
        );

        Collections.reverse(list);

        for (int i = (list.size() - 1); i >= 0; i--) {
            board.addLine(i, list.get(i));
        }

        if (player.hasAttachedBoard()) {
            player.removeScoreboard();
        }

        player.setAttachedBoard(board);
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

    private void fieldUpdate(Player player, String field, String value) {

        String subMode = plugin.getConfig().getString("centauri.subMode", "");

        GameBoard board = player.getAttachedBoard();
        me.yushust.message.util.StringList list = messageHandler.getMany(player, "scoreboard." + subMode + ".board");

        Collections.reverse(list);

        if (board != null) {

            for (int i = (list.size() - 1); i >= 0; i--) {

                if (list.get(i).contains(field)) {
                    board.setLine(i, list.get(i).replace(field, value));
                }

            }

        }

    }

}
