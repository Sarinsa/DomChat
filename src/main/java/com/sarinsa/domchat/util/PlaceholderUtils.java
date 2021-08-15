package com.sarinsa.domchat.util;

import com.earth2me.essentials.User;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.sarinsa.domchat.core.DomChat;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderUtils {

    public static final Placeholder[] PLACEHOLDERS = Placeholder.values();

    public enum Placeholder {

        TOWN("{town}") {
            @Override
            public String getProcessed(Player player, String message) {
                Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
                String townName = ChatColor.GRAY + "None";

                if (resident != null) {
                    Town town = TownyAPI.getInstance().getResidentTownOrNull(resident);

                    if (town != null) {
                        townName = town.getFormattedName();
                    }
                }
                return message.replace(this.getPlaceholder(), townName);
            }
        },
        TITLE("{title}") {
            @Override
            public String getProcessed(Player player, String message) {
                Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
                String title = ChatColor.GRAY + "None";

                if (resident != null && resident.hasTitle()) {
                    title = resident.getFormattedTitleName();
                }
                return message.replace(this.getPlaceholder(), title);
            }
        },
        TOWN_RANKS("{town_ranks}") {
            @Override
            public String getProcessed(Player player, String message) {
                Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
                List<String> ranks = new ArrayList<>();

                if (resident != null) {
                    if (resident.isKing()) {
                        ranks.add("King");
                    }
                    if (resident.isMayor()) {
                        ranks.add("Mayor");
                    }
                    ranks.addAll(resident.getTownRanks());

                    if (ranks.isEmpty()) {
                        ranks.add("Resident");
                    }
                }
                else {
                    ranks.add(ChatColor.GRAY + "None");
                }
                return message.replace(this.getPlaceholder(), String.join(", ", ranks));
            }
        },
        DISPLAY_NAME("{display_name}") {
            @Override
            public String getProcessed(Player player, String message) {
                User user = DomChat.INSTANCE.getEssentials().getUser(player);
                String username = user.getNickname() == null ? user.getName() : user.getFormattedNickname();

                return message.replace(this.getPlaceholder(), username);
            }
        },
        PREFIX("{prefix}") {
            @Override
            public String getProcessed(Player player, String message) {
                net.luckperms.api.model.user.User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
                String groupName = "&7No prefix";

                if (user != null) {
                    String prefix = user.getCachedData().getMetaData().getPrefix();

                    if (prefix != null) {
                        groupName = prefix;
                    }
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
