package com.sarinsa.domchat.core;

import com.earth2me.essentials.Essentials;
import com.sarinsa.domchat.command.DomCommandExecutor;
import com.sarinsa.domchat.core.config.DomConfig;
import com.sarinsa.domchat.event.DomEventListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class DomChat extends JavaPlugin {

    /** plugin instance */
    public static DomChat INSTANCE;

    /** EssentialsX plugin instance */
    private Essentials ESSENTIALS;

    /** Logger instance */
    public static PluginLogger LOGGER;

    /** Set to true if this plugin should stop all interactions */
    public boolean isDisabled = false;

    /** The plugin config */
    private DomConfig pluginConfig;


    public DomChat() {
        INSTANCE = this;
        LOGGER = new PluginLogger(this);
    }

    @Override
    public void onEnable() {
        // Disable the plugin if the necessary dependency plugins are not present
        this.loadConfig();

        this.getServer().getPluginManager().registerEvents(new DomEventListener(), this);

        this.getCommand("domchat").setExecutor(new DomCommandExecutor());

        if (!this.getServer().getPluginManager().isPluginEnabled("EssentialsX")) {
            this.getServer().getPluginManager().disablePlugin(this);
            LOGGER.severe(ChatColor.RED + "Could not find EssentialsX, disabling DomChat...");
        }
        else {
            this.ESSENTIALS = (Essentials) this.getServer().getPluginManager().getPlugin("EssentialsX");
        }
        this.isDisabled = false;
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

    public boolean isDisabled() {
        return this.isDisabled;
    }

    public void setDisabled(boolean disabled) {
        this.isDisabled = disabled;
    }

    public Essentials getEssentials() {
        return this.getEssentials();
    }
}
