package net.astrocube.skywars.listener.game;

import com.google.inject.Inject;

import net.astrocube.api.bukkit.game.event.match.MatchStartEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.skywars.api.kit.Kit;
import net.astrocube.skywars.api.kit.KitMatcher;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MatchStartListener implements Listener {

	private @Inject FindService<Match> matchFindService;
	private @Inject KitMatcher kitMatcher;

	@EventHandler
	public void onMatchStart(MatchStartEvent event) {
		matchFindService.find(event.getMatch()).callback(response -> {

			if (!response.isSuccessful() || !response.getResponse().isPresent()) {
				try {
					throw new GameControlException("The requested match was not found");
				} catch (GameControlException exception) {
					exception.printStackTrace();
				}
			}

			Match match = response.getResponse().get();

			match.getTeams().forEach(team -> team.getMembers().forEach(member -> {
				try {
					Kit.build(Bukkit.getPlayerByIdentifier(member.getUser()), kitMatcher.getPlayerKit(member));
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}));
		});
	}
}