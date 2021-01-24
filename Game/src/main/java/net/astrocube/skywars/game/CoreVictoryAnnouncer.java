package net.astrocube.skywars.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.skywars.api.game.VictoryAnnouncer;
import org.bukkit.entity.Player;
import org.github.paperspigot.Title;

@Singleton
public class CoreVictoryAnnouncer implements VictoryAnnouncer {

    private @Inject MessageHandler<Player> messageHandler;

    @Override
    public void sendVictoryTitle(Player player) {

        Title title = new Title(
                messageHandler.get(player, "match.victory.title"),
                messageHandler.get(player, "match.victory.sub")
        );

        player.sendTitle(title);

    }

}
