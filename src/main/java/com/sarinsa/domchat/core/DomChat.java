package com.sarinsa.domchat.core;

import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class DomChat extends JavaPlugin {

    public static DomChat INSTANCE;
    public static PluginLogger LOGGER;


    public DomChat() {
        INSTANCE = this;
        LOGGER = new PluginLogger(this);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
