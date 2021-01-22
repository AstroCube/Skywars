package net.astrocube.skywars;

import com.google.inject.Inject;
import me.fixeddev.inject.ProtectedBinder;
import me.yushust.message.MessageHandler;
import me.yushust.message.StringList;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.skywars.loader.InjectionLoaderModule;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftGameBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.GameBoard;

import java.util.Collections;

public class SkyWars extends JavaPlugin {

    private @Inject Loader loader;

    @Override
    public void onEnable() {
        loader.load();
        copyDefaults();
    }

    @Override
    public void configure(ProtectedBinder binder) {
        binder.install(new InjectionLoaderModule());
    }

    public void copyDefaults() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }


}
