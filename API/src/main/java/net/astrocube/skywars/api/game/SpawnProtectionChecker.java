package net.astrocube.skywars.api.game;

import net.astrocube.skywars.api.team.ProvisionedTeam;

import java.util.Set;

public interface SpawnProtectionChecker {

    /**
     * Generates spawn protection once-pass for a
     * match to be checked after.
     * @param provisionedTeams of the match
     */
    void registerMatch(Set<ProvisionedTeam> provisionedTeams);

    /**
     * Remove a certain player from spawn protection.
     * @param id to be removed
     */
    void removeFromRegistry(String id);

    /**
     * @param id to be checked
     * @return if user has spawn protection
     */
    boolean hasProtection(String id);

}
