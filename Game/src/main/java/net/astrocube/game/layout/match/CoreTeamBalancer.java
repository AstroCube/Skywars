package net.astrocube.game.layout.match;

import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.game.layout.api.team.TeamBalancer;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class CoreTeamBalancer implements TeamBalancer {

    @Override
    public Set<MatchDoc.Team> generateTeams(Set<MatchAssignable> assignableList) {

        /*

            What is this?

            All the matches are assigned vía MatchAssignable sets, in order to keep
            track of all the parties, groups or SOLO matchmaking requests, here is where
            you need to balance all the teams obtained from your configuration files and
            create group-friendly teams. We understand that is not easy, because always
            parties can not be exactly an even number, but you can try your best to
            balance it.

            Below we will place all the users in a "SOLO" team for an example.

         */

        Set<MatchDoc.Team> teams = new HashSet<>();

        MatchDoc.Team layoutTeam = new MatchDoc.Team() {
            @Override
            public String getName() {
                return "Awesome Team";
            }

            @Override
            public Set<MatchDoc.TeamMember> getMembers() {
                return assignableList.stream().map(matchAssignable ->  {
                            matchAssignable.getInvolved().add(matchAssignable.getResponsible());
                            return matchAssignable.getInvolved();
                        })
                        .flatMap(Collection::stream)
                        .map(map -> new MatchDoc.TeamMember() {
                            @Override
                            public String getUser() {
                                return map;
                            }

                            @Override
                            public boolean isActive() {
                                return true;
                            }

                            @Override
                            public Date getJoinedAt() {
                                return new Date();
                            }
                        }).collect(Collectors.toSet());
            }

            @Override
            public String getColor() {
                return "yellow";
            }
        };

        teams.add(layoutTeam);

        return teams;
    }

}
