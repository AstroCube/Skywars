package net.astrocube.skywars.api.kit;

import net.astrocube.api.core.virtual.user.User;

public interface KitMatcher {

	Kit getPlayerKit(User user);

}
