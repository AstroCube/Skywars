package net.astrocube.skywars.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;

import javax.inject.Named;

public class LayoutLoader implements Loader {

    private @Inject @Named("listener") Loader listenerLoader;

    @Override
    public void load() {
        this.listenerLoader.load();
    }

}
