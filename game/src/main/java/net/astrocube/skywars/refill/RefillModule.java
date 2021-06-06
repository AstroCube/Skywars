package net.astrocube.skywars.refill;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.skywars.api.refill.RefillAnnouncer;
import net.astrocube.skywars.api.refill.RefillScheduler;

public class RefillModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(RefillAnnouncer.class).to(CoreRefillAnnouncer.class);
		bind(RefillScheduler.class).to(CoreRefillScheduler.class);
	}

}
