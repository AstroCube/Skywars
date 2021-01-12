package net.astrocube.skywars.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.astrocube.skywars.api.custom.CustomItemRepository;
import net.astrocube.skywars.api.custom.Customizable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

@RequiredArgsConstructor
public class CoreItemRepository<T extends Customizable> implements CustomItemRepository<T> {

    private final String name;
    private final Class<T> clazz;
    private final Plugin plugin;
    private final ObjectMapper mapper;

    private final Set<T> cache = new HashSet<>();
    private boolean generated = false;

    @Override
    public void generate() {

        if (generated) {
            throw new UnsupportedOperationException("Can not re-create repository");
        }

        File folder = new File(plugin.getDataFolder(), name);

        if (folder.exists() && folder.isDirectory()) {

            for (File file : folder.listFiles()) {

                if (!file.isDirectory() && FilenameUtils.getExtension(file.getName()).equalsIgnoreCase(".json")) {
                    try {
                        cache.add(mapper.readValue(file, clazz));
                    } catch (IOException info) {
                        plugin.getLogger().log(Level.WARNING, "Error while parsing {0}", file.getName());
                    }
                }

            }

        } else {
            folder.mkdir();
        }

        generated = true;
        plugin.getLogger().log(Level.INFO, "Loaded {0} objects from {1} repository.", new String[]{cache.size() + "", name});
    }

    @Override
    public Set<T> getRegisteredItems() {
        return cache;
    }

}
