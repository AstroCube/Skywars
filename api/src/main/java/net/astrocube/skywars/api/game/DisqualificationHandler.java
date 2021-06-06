package net.astrocube.skywars.api.game;

import net.astrocube.skywars.api.team.ProvisionedTeam;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.UUID;

public interface DisqualificationHandler {

    void ensureTeams(String match, Set<ProvisionedTeam> teams);

    void disqualify(String user);

    void alertDisqualify(Player player, Player target, @Nullable Player killer);

    interface Registry {

        String getMatch();

        String getUser();

        String getTeam();

    }

}
