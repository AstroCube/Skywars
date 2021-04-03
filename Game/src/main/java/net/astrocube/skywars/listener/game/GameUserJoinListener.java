package net.astrocube.skywars.listener.game;

import net.astrocube.api.bukkit.game.event.game.GameUserJoinEvent;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameUserJoinListener implements Listener {

    @EventHandler
    public void onJoin(GameUserJoinEvent event) {
        Player player = event.getPlayer();
        UserMatchJoiner.Origin origin = event.getOrigin();

        if (origin == UserMatchJoiner.Origin.WAITING) {
            // TODO: Add the fucking kit selection item
        }
    }

}
