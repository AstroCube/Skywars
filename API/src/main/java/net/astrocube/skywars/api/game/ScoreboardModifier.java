package net.astrocube.skywars.api.game;

import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import org.bukkit.entity.Player;

import java.util.Set;

public interface ScoreboardModifier {

    void createBoard(Set<ProvisionedTeam> players, GameMap gameMap);

    void updateAlive(Set<Player> players);

    void updateRefillCountdown(Set<ProvisionedTeam> players, int seconds);

}
