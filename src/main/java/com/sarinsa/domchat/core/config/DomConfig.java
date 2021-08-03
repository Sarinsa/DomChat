package com.sarinsa.domchat.core.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class DomConfig extends AbstractDomConfig {

    public DomConfig(File configDir, String configPath) {
        super(configDir, configPath);
    }

    /**
     * Creates the default values in the plugin config.
     */
    @Override
    public void setDefaultValues(FileConfiguration config) {
        config.set("woah", "insane");
    }
}
