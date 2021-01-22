package net.astrocube.skywars.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.mrbean.MrBeanModule;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.astrocube.skywars.SkyWars;
import net.astrocube.skywars.api.custom.CustomItemRepository;
import net.astrocube.skywars.api.custom.Customizable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
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

        mapper.registerModule(new MrBeanModule());

        if (generated) {
            throw new UnsupportedOperationException("Can not re-create repository");
        }

        File folder = new File(plugin.getDataFolder(), name);

        if (folder.exists() && folder.isDirectory()) {

            for (File file : folder.listFiles()) {

                if (!file.isDirectory() && FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("json")) {

                    try {
                        cache.add(mapper.readValue(file, clazz));
                    } catch (IOException ex) {
                        plugin.getLogger().log(Level.WARNING, "Error while parsing {0}", file.getName());
                        ex.printStackTrace();
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
