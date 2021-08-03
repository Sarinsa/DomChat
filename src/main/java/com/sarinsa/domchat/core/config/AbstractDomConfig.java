package com.sarinsa.domchat.core.config;

import com.sarinsa.domchat.core.DomChat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class AbstractDomConfig {

    private final File configDir;
    private final File configFile;

    private FileConfiguration config;

    public AbstractDomConfig(File configDir, String configPath) {
        this.configDir = configDir;
        this.configFile = new File(configDir, configPath);
    }

    public abstract void setDefaultValues(FileConfiguration config);

    public final FileConfiguration get() {
        return this.config;
    }

    public final void load() {
        if (!this.configDir.exists()) {
            if (!this.configDir.mkdirs()) {
                DomChat.LOGGER.severe("Failed to create config directory. This shouldn't happen, and the plugin will not work correctly.");
            }
        }
        try {
            this.config = YamlConfiguration.loadConfiguration(this.configFile);

            if (!this.configFile.exists()) {
                this.setDefaultValues(this.config);
                this.config.save(this.configFile);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void save() {
        try {
            this.config.save(this.configFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void reload() {
        this.config = YamlConfiguration.loadConfiguration(this.configFile);
    }
}
