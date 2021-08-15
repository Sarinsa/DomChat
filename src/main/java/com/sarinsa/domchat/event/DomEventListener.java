package com.sarinsa.domchat.event;

import com.sarinsa.domchat.core.DomChat;
import com.sarinsa.domchat.util.Constants;
import com.sarinsa.domchat.util.PlaceholderUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Iterator;
import java.util.List;

public class DomEventListener implements Listener {

    /**
     * This is where the magic happens!
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        if (DomChat.INSTANCE.isDisabled())
            return;

        Player player = event.getPlayer();

        BaseComponent[] displayComponents = processPlaceholders(DomChat.INSTANCE.getPluginConfig().getDisplayComponents(), player);
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(displayComponents));

        TextComponent chatFormat = new TextComponent();
        chatFormat.setText(processChatFormat(player, DomChat.INSTANCE.getPluginConfig().getChatFormat()));
        chatFormat.setHoverEvent(hoverEvent);

        TextComponent message = new TextComponent();
        String rawMessage = event.getMessage();

        if (player.hasPermission(Constants.Permissions.COLOR_PERMISSION))
            ChatColor.translateAlternateColorCodes('&', rawMessage);

        message.setText(rawMessage);

        for (Player recipient : event.getRecipients()) {
            recipient.spigot().sendMessage(ChatMessageType.CHAT, chatFormat, message);
        }
        event.setCancelled(true);

        // Sending the message to console manually since the event is canceled.
        Bukkit.getServer().getConsoleSender().sendMessage(chatFormat.getText() + rawMessage);
    }

    /**
     * Helper method for finding placeholders and replacing
     * them with their target value.
     */
    private static BaseComponent[] processPlaceholders(List<String> displayMessages, Player player) {
        TextComponent text = new TextComponent();
        TextComponent newLine = new TextComponent(ComponentSerializer.parse("{text: \"\n\"}"));
        Iterator<String> iterator = displayMessages.iterator();

        while (iterator.hasNext()) {
            String newString = iterator.next();

            for (PlaceholderUtils.Placeholder placeholder : PlaceholderUtils.PLACEHOLDERS) {
                newString = placeholder.getProcessed(player, newString);
            }
            text.addExtra(new TextComponent(ChatColor.translateAlternateColorCodes('&', newString)));

            if (iterator.hasNext()) {
                text.addExtra(newLine);
            }
        }
        return new BaseComponent[] { text };
    }

    /**
     * Processes the chat format (whatever text comes before the actual message in other words).
     */
    private static String processChatFormat(Player player, String rawFormat) {
        for (PlaceholderUtils.Placeholder placeholder : PlaceholderUtils.PLACEHOLDERS) {
            rawFormat = placeholder.getProcessed(player, rawFormat);
        }
        return ChatColor.translateAlternateColorCodes('&', rawFormat);
    }
}
