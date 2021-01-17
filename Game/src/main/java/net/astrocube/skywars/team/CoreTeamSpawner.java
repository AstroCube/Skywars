package net.astrocube.skywars.team;

import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import net.astrocube.skywars.api.team.TeamSpawner;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Set;

@Singleton
public class CoreTeamSpawner implements TeamSpawner {

    @Override
    public void spawn(Set<ProvisionedTeam> provisioned, String match) {

        World world = Bukkit.getWorld("match_" + match);

        if (world == null) {
            Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(match, false));
            return;
        }

        provisioned.forEach(team ->
                team.getMembers().forEach(teamMember ->  {
                    Player player = Bukkit.getPlayerByIdentifier(teamMember.getUser());
                    if (player != null) {
                        player.teleport(TeamUtils.generateSpawn(team.getSpawn(), world));
                    }
                })
        );

    }

}
