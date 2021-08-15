package com.sarinsa.domchat.core.config;

import com.sarinsa.domchat.util.PlaceholderUtils;
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
        displayComponents.add("&eTown ranks: &a" + PlaceholderUtils.Placeholder.TOWN_RANKS.getPlaceholder());

        config.set("display-components", displayComponents);

        config.set("chat-format", PlaceholderUtils.Placeholder.PREFIX.getPlaceholder() + " " + PlaceholderUtils.Placeholder.DISPLAY_NAME.getPlaceholder() + "&f: ");
    }

    public List<String> getDisplayComponents() {
        return this.get().getStringList("display-components");
    }

    public String getChatFormat() {
        return this.get().getString("chat-format");
    }
}
