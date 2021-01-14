package net.astrocube.skywars.game;

import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.event.match.MatchFinishEvent;
import net.astrocube.skywars.api.game.DisqualificationHandler;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class CoreDisqualificationHandler implements DisqualificationHandler {

    private final Set<Registry> registries;

    public CoreDisqualificationHandler() {
        this.registries = new HashSet<>();
    }

    @Override
    public void ensureTeams(String match, Set<ProvisionedTeam> teams) {
        teams.forEach(team -> team.getMembers().forEach(teamMember -> registries.add(new Registry() {
            @Override
            public String getMatch() {
                return match;
            }

            @Override
            public String getUser() {
                return teamMember.getUser();
            }

            @Override
            public String getTeam() {
                return match + "_" + team.getName();
            }
        })));
    }

    @Override
    public void disqualify(String user) {

        Optional<Registry> userRegistry = registries.stream()
                .filter(registry -> registry.getUser().equalsIgnoreCase(user))
                .findFirst();

        userRegistry.ifPresent(registry -> {

            registries.remove(registry);

            Set<String> remainingTeams = new HashSet<>();

            // Check if there is only one team at remaining teams
            for (Registry in : registries) {
                if (in.getTeam().equalsIgnoreCase(registry.getTeam())) {
                    remainingTeams.add(in.getTeam());
                }
            }

            if (remainingTeams.size() == 1) {

                // Call victory event and remove all remaining registries
                Bukkit.getPluginManager().callEvent(
                        new MatchFinishEvent(
                                registry.getMatch(),
                                registries.stream()
                                        .filter(t -> remainingTeams.contains(t.getTeam()))
                                        .map(Registry::getUser)
                                        .collect(Collectors.toSet())
                        )
                );

                registries.removeIf(reg -> reg.getMatch().equalsIgnoreCase(registry.getTeam()));

            }

        });

    }

}
