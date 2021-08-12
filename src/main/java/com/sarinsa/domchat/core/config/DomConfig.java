package com.sarinsa.domchat.core.config;

import com.sarinsa.domchat.util.PlaceholderUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DomConfig extends AbstractDomConfig {

    public DomConfig(File configDir, String configPath) {
        super(configDir, configPath);
    }

    /**
     * Creates the default values in the plugin config.
     */
    @Override
    public void setDefaultValues(FileConfiguration config) {
        List<String> displayComponents = new ArrayList<>();

        displayComponents.add("&eTown: &a" + PlaceholderUtils.Placeholder.TOWN.getPlaceholder());
        displayComponents.add("&eTitle: &a" + PlaceholderUtils.Placeholder.TITLE.getPlaceholder());

        config.set("display-components", displayComponents);

        config.set("chat-format", "&7[" + PlaceholderUtils.Placeholder.RANK.getPlaceholder() + "&7] " + PlaceholderUtils.Placeholder.USERNAME.getPlaceholder() + "&f: ");
    }

    public List<String> getDisplayComponents() {
        return this.get().getStringList("display-components");
    }

    public String getChatFormat() {
        return this.get().getString("chat-format");
    }
}
