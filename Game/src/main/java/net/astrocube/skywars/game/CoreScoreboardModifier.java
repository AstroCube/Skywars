package net.astrocube.skywars.game;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.skywars.api.game.ScoreboardModifier;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftGameBoard;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.GameBoard;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;

public class CoreScoreboardModifier implements ScoreboardModifier {

    private @Inject MessageHandler<Player> messageHandler;
    private @Inject Plugin plugin;

    @Override
    public void createBoard(Set<ProvisionedTeam> players) {

    }

    @Override
    public void updateAlive(Set<ProvisionedTeam> players) {

    }

    @Override
    public void updateRefillCountdown(Set<ProvisionedTeam> players) {

    }

    private void setupScoreboard(Player player, Match match) {

        String subMode = plugin.getConfig().getString("centauri.subMode", "");

        GameBoard board = new CraftGameBoard(
                messageHandler.get(
                        player,
                        "scoreboard." + subMode + ".title"
                )
        );

        board.addLine(7, ChatColor.GREEN + "");
        board.addLine(6, );
        board.addLine(5,
                ChatColor.YELLOW + "\u00BB " + this.translatableField.getUnspacedField(user.getLanguage(), "scoreboard_refill") + ": " +
                        ChatColor.WHITE + this.translatableField.getUnspacedField(user.getLanguage(), "scoreboard_empty")
        );
        board.addLine(4, ChatColor.YELLOW + "");
        board.addLine(3, ChatColor.YELLOW + "\u00BB " + this.translatableField.getUnspacedField(user.getLanguage(), "scoreboard_map") + ":");
        board.addLine(2, ChatColor.WHITE + this.matchMapProvider.getMatchMap(match).getName());
        board.addLine(1, ChatColor.AQUA + "");
        board.addLine(0, ChatColor.YELLOW + "www.seocraft.net");

        if (player.hasAttachedBoard()) player.removeScoreboard();
        player.setAttachedBoard(board);
    }

}
