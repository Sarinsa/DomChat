package com.sarinsa.domchat.event;

import com.sarinsa.domchat.core.DomChat;
import com.sarinsa.domchat.util.PlaceholderUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class DomEventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        List<Text> displayComponents = processPlaceholders(DomChat.INSTANCE.getPluginConfig().getDisplayComponents(), player);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, (Content) displayComponents);

        TextComponent chatFormat = new TextComponent();
        chatFormat.setText(processChatFormat(player, DomChat.INSTANCE.getPluginConfig().getChatFormat()));
        chatFormat.setHoverEvent(hoverEvent);

        TextComponent message = new TextComponent();
        message.setText(event.getMessage());

        for (Player recipient : event.getRecipients()) {
            recipient.spigot().sendMessage(ChatMessageType.CHAT, chatFormat, message);
        }
        event.setCancelled(true);
    }

    private static List<Text> processPlaceholders(List<String> displayMessages, Player player) {
        List<Text> processedDisplayMessages = new ArrayList<>();

        for (String s : displayMessages) {
            String newString = s;

            for (PlaceholderUtils.Placeholder placeholder : PlaceholderUtils.DISPLAY) {
                newString = placeholder.getProcessed(player, newString);
            }
            processedDisplayMessages.add(new Text(newString));
        }
        return processedDisplayMessages;
    }

    private static String processChatFormat(Player player, String rawFormat) {
        for (PlaceholderUtils.Placeholder placeholder : PlaceholderUtils.CHAT_FORMAT) {
            rawFormat = placeholder.getProcessed(player, rawFormat);
        }
        return rawFormat;
    }
}
