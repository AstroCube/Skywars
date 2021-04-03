package net.astrocube.skywars.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.skywars.listener.damage.PlayerDeathListener;
import net.astrocube.skywars.listener.damage.PlayerFallListener;
import net.astrocube.skywars.listener.damage.PlayerItemDamageListener;
import net.astrocube.skywars.listener.game.*;
import net.astrocube.skywars.listener.ignite.BlockPlaceListener;
import net.astrocube.skywars.listener.ignite.EntityExplodeListener;
import net.astrocube.skywars.listener.interaction.PlayerInteractionListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class ListenerLoader implements Loader {

    private @Inject Plugin plugin;

    private @Inject PlayerItemDamageListener playerItemDamageListener;
    private @Inject PlayerDeathListener playerDeathListener;
    private @Inject PlayerFallListener playerFallListener;

    private @Inject BlockPlaceListener blockPlaceListener;
    private @Inject EntityExplodeListener entityExplodeListener;

    private @Inject GameReadyListener gameReadyListener;
    private @Inject GameInvalidationListener gameInvalidationListener;
    private @Inject GamePairEnableListener gamePairEnableListener;
    private @Inject GameDisqualificationListener gameDisqualificationListener;
    private @Inject GameUserJoinListener gameUserJoinListener;
    private @Inject MatchFinishListener matchFinishListener;
    private @Inject PlayerInteractionListener playerInteractionListener;

    @Override
    public void load() {
        plugin.getLogger().log(Level.INFO, "Initializing event listeners");

        registerEvent(playerItemDamageListener);
        registerEvent(playerDeathListener);
        registerEvent(playerFallListener);

        registerEvent(blockPlaceListener);
        registerEvent(entityExplodeListener);

        registerEvent(gameReadyListener);
        registerEvent(gameInvalidationListener);
        registerEvent(gamePairEnableListener);
        registerEvent(gameDisqualificationListener);
        registerEvent(gameUserJoinListener);

        registerEvent(matchFinishListener);
        registerEvent(playerInteractionListener);
    }

    private void registerEvent(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

}
