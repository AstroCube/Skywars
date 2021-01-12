package net.astrocube.skywars.kit;

import net.astrocube.api.core.virtual.user.User;
import net.astrocube.skywars.api.kit.Kit;
import net.astrocube.skywars.api.kit.KitMatcher;

public class CoreKitMatcher implements KitMatcher {

    @Override
    public Kit getPlayerKit(User user) {
        return null;
    }

}
