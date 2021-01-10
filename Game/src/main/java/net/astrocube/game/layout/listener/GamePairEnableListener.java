package net.astrocube.game.layout.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.game.GameModePairEvent;
import net.astrocube.api.bukkit.game.event.game.GamePairEnableEvent;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;


public class GamePairEnableListener implements Listener {

    private @Inject FindService<GameMode> findService;
    private @Inject Plugin plugin;

    @EventHandler
    public void onGameReady(GamePairEnableEvent event) {

        ;findService.find(plugin.getConfig().getString("centauri.mode")).callback(response -> {

            if (!response.isSuccessful()) {
                plugin.getLogger().log(Level.SEVERE, "There was an error trying to pair the game with Centauri",
                        response.getThrownException());
                return;
            }

            if (!response.getResponse().isPresent()) {
                plugin.getLogger().log(Level.SEVERE, "Can not obtain a GameMode with the requested ID");
                return;
            }

            GameMode mode = response.getResponse().get();

            if (mode.getSubTypes() == null) {
                plugin.getLogger().log(Level.SEVERE, "The requested GameMode does not have any SubMode");
                return;
            }

            Optional<SubGameMode> subGameMode = mode.getSubTypes().stream()
                    .filter(g -> g.getId().equalsIgnoreCase(plugin.getConfig().getString("centauri.subMode")))
                    .findFirst();

            if (!subGameMode.isPresent()) {
                plugin.getLogger().log(Level.SEVERE, "The requested GameMode was not found");
                return;
            }

            Bukkit.getPluginManager().callEvent(new GameModePairEvent(mode, subGameMode.get()));

        });

    }

}
