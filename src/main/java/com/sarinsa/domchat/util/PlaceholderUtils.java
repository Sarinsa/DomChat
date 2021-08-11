package com.sarinsa.domchat.util;

import com.earth2me.essentials.User;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.sarinsa.domchat.core.DomChat;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlaceholderUtils {

    public static final Placeholder[] DISPLAY = { Placeholder.TOWN, Placeholder.TITLE, Placeholder.RANK };

    public static final Placeholder[] CHAT_FORMAT = { Placeholder.RANK, Placeholder.USERNAME };

    public enum Placeholder {

        TOWN("{town}") {
            @Override
            public String getProcessed(Player player, String message) {
                Town town = TownyAPI.getInstance().getTown(player.getUniqueId());
                String townName;

                if (town == null) {
                    townName = ChatColor.GRAY + "No associated town";
                }
                else {
                    townName = town.getFormattedName();
                }
                return message.replace(this.getPlaceholder(), townName);
            }
        },
        TITLE("{title}") {
            @Override
            public String getProcessed(Player player, String message) {
                Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
                String title;

                if (resident == null || !resident.hasTitle()) {
                    title = ChatColor.GRAY + "No title";
                }
                else {
                    title = resident.getFormattedTitleName();
                }
                return message.replace(this.getPlaceholder(), title);
            }
        },
        USERNAME("{username}") {
            @Override
            public String getProcessed(Player player, String message) {
                User user = DomChat.INSTANCE.getEssentials().getUser(player);
                String username = user.getNickname() == null ? user.getDisplayName() : user.getFormattedNickname();

                return message.replace(this.getPlaceholder(), username);
            }
        },
        RANK("{rank}") {
            @Override
            public String getProcessed(Player player, String message) {
                net.luckperms.api.model.user.User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
                String groupName;

                if (user == null) {
                    // Generally shouldn't happen like ever
                    groupName = ChatColor.GRAY + "No rank";
                }
                else {
                    groupName = user.getPrimaryGroup();
                }
                return message.replace(this.getPlaceholder(), groupName);
            }
        };

        Placeholder(String placeholder) {
            this.placeholder = placeholder;
        }

        private final String placeholder;

        public String getPlaceholder() {
            return this.placeholder;
        }

        public abstract String getProcessed(Player player, String message);
    }
}
