package net.astrocube.skywars.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.translation.TranslationModule;
import net.astrocube.skywars.cage.CoreCage;
import net.astrocube.skywars.chest.ChestModule;
import net.astrocube.skywars.custom.CustomModule;
import net.astrocube.skywars.game.GameModule;
import net.astrocube.skywars.kit.KitModule;
import net.astrocube.skywars.perk.PerkModule;
import net.astrocube.skywars.refill.RefillModule;
import net.astrocube.skywars.team.TeamModule;

public class InjectionLoaderModule extends ProtectedModule {

	@Override
	public void configure() {
		install(new LoaderModule());
		install(new TranslationModule());
		install(new TeamModule());
		install(new CoreCage());
		install(new ChestModule());
		install(new GameModule());
		install(new RefillModule());
		install(new CustomModule());
		install(new PerkModule());
		install(new KitModule());
	}
}