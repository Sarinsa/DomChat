package com.sarinsa.domchat.core;

import com.sarinsa.domchat.command.DomCommandExecutor;
import com.sarinsa.domchat.core.config.DomConfig;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class DomChat extends JavaPlugin {

    public static DomChat INSTANCE;
    public static PluginLogger LOGGER;

    /** The plugin config */
    private DomConfig pluginConfig;


    public DomChat() {
        INSTANCE = this;
        LOGGER = new PluginLogger(this);
    }

    @Override
    public void onEnable() {
        // Disable the plugin if the necessary dependency plugins are not present
        if (!this.checkDependencies()) {
            this.getPluginLoader().disablePlugin(this);
        }
        this.loadConfig();

        this.getCommand("domchat").setExecutor(new DomCommandExecutor());
    }

    @Override
    public void onDisable() {
    }

    /**
     * Loads the plugin's config. If the config
     * doesn't already exist, it gets created.
     */
    private void loadConfig() {
        this.pluginConfig = new DomConfig(this.getDataFolder(), "config.yml");
        this.pluginConfig.load();
    }

    /** Returns the Dom config */
    public DomConfig getPluginConfig() {
        return this.pluginConfig;
    }

    private boolean checkDependencies() {
        return true;
    }
}
