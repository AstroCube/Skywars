package net.astrocube.skywars.perk;

import com.google.inject.Scopes;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.skywars.api.perk.SkyWarsPerkProvider;

public class PerkModule extends ProtectedModule {

    @Override
    protected void configure() {
        bind(SkyWarsPerkProvider.class).to(CoreSkyWarsPerkProvider.class).in(Scopes.SINGLETON);
    }
}
