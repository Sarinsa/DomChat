package com.sarinsa.domchat.core;

import com.earth2me.essentials.Essentials;
import com.sarinsa.domchat.command.DomCommandExecutor;
import com.sarinsa.domchat.core.config.DomConfig;
import com.sarinsa.domchat.event.DomEventListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class DomChat extends JavaPlugin {

    /** plugin instance */
    public static DomChat INSTANCE;

    /** EssentialsX plugin instance */
    private Essentials essentials;

    /** Logger instance */
    public static PluginLogger LOGGER;

    /** Set to true if this plugin should stop all interactions */
    private boolean isDisabled = false;

    /** The plugin config */
    private DomConfig pluginConfig;


    public DomChat() {
        INSTANCE = this;
        LOGGER = new PluginLogger(this);
    }

    @Override
    @SuppressWarnings("all")
    public void onEnable() {
        this.loadConfig();

        this.getServer().getPluginManager().registerEvents(new DomEventListener(), this);

        this.getCommand("domchat").setExecutor(new DomCommandExecutor());

        // Fetch Essentials instance
        this.handleDependencies();

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
        return this.essentials;
    }

    private void handleDependencies() {
        if (!this.getServer().getPluginManager().isPluginEnabled("Essentials")) {
            this.getServer().getPluginManager().disablePlugin(this);
            LOGGER.severe("Could not find EssentialsX, disabling DomChat...");
        }
        else {
            Plugin plugin = this.getServer().getPluginManager().getPlugin("Essentials");

            if (!(plugin instanceof Essentials)) {
                this.getServer().getPluginManager().disablePlugin(this);
                LOGGER.severe("Found a different plugin than expected with the name Essentials. What are the chances? Disabling DomChat...");
            }
            else {
                this.essentials = (Essentials) plugin;
            }
        }
    }
}
