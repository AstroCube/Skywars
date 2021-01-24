package net.astrocube.skywars.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.match.MatchFinishEvent;
import net.astrocube.api.bukkit.translation.mode.AlertMode;
import net.astrocube.skywars.api.game.DisqualificationHandler;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class CoreDisqualificationHandler implements DisqualificationHandler {

    private final Set<Registry> registries;
    private final MessageHandler<Player> messageHandler;

    @Inject
    public CoreDisqualificationHandler(MessageHandler<Player> messageHandler) {
        this.registries = new HashSet<>();
        this.messageHandler = messageHandler;
    }

    @Override
    public void ensureTeams(String match, Set<ProvisionedTeam> teams) {
        teams.forEach(team -> team.getMembers().forEach(teamMember -> registries.add(new Registry() {
            @Override
            public UUID getUUID() {
                return UUID.randomUUID();
            }

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

        if (userRegistry.isPresent()) {

            Registry temporal = userRegistry.get();

            registries.removeIf(reg -> reg.getUUID() == userRegistry.get().getUUID());

            registries.forEach(reg -> System.out.println(reg.getUser()));

            Set<String> remainingTeams = new HashSet<>();

            // Check if there is only one team at remaining teams
            for (Registry in : registries) {
                if (in.getTeam().equalsIgnoreCase(temporal.getTeam())) {
                    remainingTeams.add(in.getTeam());
                }
            }

            if (remainingTeams.size() == 1) {

                // Call victory event and remove all remaining registries
                Bukkit.getPluginManager().callEvent(
                        new MatchFinishEvent(
                                temporal.getMatch(),
                                registries.stream()
                                        .filter(t -> remainingTeams.contains(t.getTeam()))
                                        .map(Registry::getUser)
                                        .collect(Collectors.toSet())
                        )
                );

                registries.removeIf(reg -> reg.getMatch().equalsIgnoreCase(temporal.getTeam()));

            }

        }

    }

    @Override
    public void alertDisqualify(Player player, Player target, @Nullable Player killer) {

        if (killer != null) {
            messageHandler.sendReplacing(
                    player, AlertMode.MUTED, "match.death-player",
                    "%%killer%%", ChatColor.WHITE + killer.getName(),
                    "%%target%%", ChatColor.WHITE + target.getName()
            );
            return;
        }

        messageHandler.send(
                player, AlertMode.MUTED, "match.death-natural",
                "%%target%%", ChatColor.WHITE + target.getName()
        );

    }

}
