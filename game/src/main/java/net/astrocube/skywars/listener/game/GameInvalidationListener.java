package net.astrocube.skywars.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.util.CountdownTimer;
import net.astrocube.skywars.api.refill.RefillScheduler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameInvalidationListener implements Listener {

	@Inject private RefillScheduler refillScheduler;

	@EventHandler
	public void gameInvalidationListener(MatchInvalidateEvent event) {
		CountdownTimer cancellableRefill = this.refillScheduler.getRefillTask(event.getMatch());
		if (cancellableRefill != null) {
			cancellableRefill.cancelCountdown();
		}
	}
}