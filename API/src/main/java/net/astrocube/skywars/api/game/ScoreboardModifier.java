package net.astrocube.skywars.api.game;

import net.astrocube.skywars.api.team.ProvisionedTeam;

import java.util.Set;

public interface ScoreboardModifier {

    void createBoard(Set<ProvisionedTeam> players);

    void updateAlive(Set<ProvisionedTeam> players, int alive);

    void updateRefillCountdown(Set<ProvisionedTeam> players, int seconds);

}
