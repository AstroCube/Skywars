package net.astrocube.skywars.translation;

import com.google.inject.Exposed;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import me.fixeddev.inject.ProtectedModule;
import me.yushust.message.MessageHandler;
import me.yushust.message.MessageRepository;
import me.yushust.message.format.bukkit.BukkitMessageAdapt;
import me.yushust.message.strategy.Strategy;
import net.astrocube.api.bukkit.translation.mode.CoreMessenger;
import net.astrocube.skywars.translation.interceptor.CenterMessageInterceptor;
import net.astrocube.skywars.translation.interceptor.ColorMessageInterceptor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TranslationModule extends ProtectedModule {

    @Provides @Singleton @Exposed
    public MessageHandler<Player> provideMessageProvider(Plugin plugin, CoreLanguageProvider languageProvider) {
        return MessageHandler.builder(Player.class)
                .setRepository(
                        MessageRepository.builder()
                                .setDefaultLanguage("es")
                                .setNodeFileLoader(BukkitMessageAdapt.getYamlFileLoader(plugin))
                                .setFileFormat("lang_%lang%.yml")
                                .setStrategy(new Strategy())
                                .setLoadSource(BukkitMessageAdapt.getPluginLoadSource(plugin))
                                .build()
                )
                .setMessenger(new CoreMessenger())
                .setLanguageProvider(languageProvider)
                .addInterceptor(new ColorMessageInterceptor())
                .addInterceptor(new CenterMessageInterceptor())
                .build();
    }
}