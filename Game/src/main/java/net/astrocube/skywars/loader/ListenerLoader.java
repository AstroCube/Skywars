package net.astrocube.skywars.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.skywars.listener.GamePairEnableListener;
import net.astrocube.skywars.listener.GameReadyListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class ListenerLoader implements Loader {

    private @Inject Plugin plugin;

    private @Inject GameReadyListener gameReadyListener;
    private @Inject GamePairEnableListener gamePairEnableListener;

    @Override
    public void load() {
        plugin.getLogger().log(Level.INFO, "Initializing event listeners");
        registerEvent(gameReadyListener);
        registerEvent(gamePairEnableListener);
    }

    private void registerEvent(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

}
