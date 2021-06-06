package net.astrocube.skywars.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import javax.annotation.Nullable;

public class PlayerDisqualificationEvent extends PlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final @Nullable Player killer;

    public PlayerDisqualificationEvent(Player player, @Nullable Player killer) {
        super(player);
        this.killer = killer;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public @Nullable Player getKiller() {
        return killer;
    }
}
