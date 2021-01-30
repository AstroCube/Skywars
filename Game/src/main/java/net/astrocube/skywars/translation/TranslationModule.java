package net.astrocube.skywars.translation;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import me.fixeddev.inject.ProtectedModule;
import me.yushust.message.MessageHandler;
import me.yushust.message.MessageProvider;
import me.yushust.message.bukkit.BukkitMessageAdapt;
import me.yushust.message.source.MessageSource;
import me.yushust.message.source.MessageSourceDecorator;
import net.astrocube.api.bukkit.translation.mode.CoreMessenger;
import net.astrocube.skywars.translation.interceptor.CenterMessageInterceptor;
import net.astrocube.skywars.translation.interceptor.ColorMessageInterceptor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TranslationModule extends ProtectedModule {

    @Provides @Singleton
    public MessageHandler provideMessageProvider(Plugin plugin, CoreLanguageProvider languageProvider) {

        MessageSource source = MessageSourceDecorator
                .decorate(BukkitMessageAdapt.newYamlSource(plugin, "lang_%lang%.yml"))
                .addFallbackLanguage("es")
                .get();

        MessageProvider messageProvider = MessageProvider.create(
                source,
                config -> {
                    config.specify(Player.class)
                            .setLinguist(languageProvider)
                            .setMessageSender(new CoreMessenger());
                    config.intercept(new ColorMessageInterceptor());
                    config.intercept(new CenterMessageInterceptor());
                }
        );

        return MessageHandler.of(messageProvider);
    }

}