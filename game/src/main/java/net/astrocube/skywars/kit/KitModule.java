package net.astrocube.skywars.kit;

import com.google.inject.Scopes;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.skywars.api.kit.KitMatcher;

public class KitModule extends ProtectedModule {

	@Override
	protected void configure() {
		bind(KitMatcher.class)
			.to(CoreKitMatcher.class)
			.in(Scopes.SINGLETON);
	}
}