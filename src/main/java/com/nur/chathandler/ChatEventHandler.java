package com.nur.chathandler;

import com.earth2me.essentials.User;
import net.ess3.api.events.NickChangeEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;

public class ChatEventHandler implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void playerChatFormatting(AsyncPlayerChatEvent e) throws IOException {
        String chatColorName = Main.getRDatabase().getChatColor(e.getPlayer().getUniqueId().toString());
        CustomChatColor activeChatColor;
        if("".equals(chatColorName)) activeChatColor = ColorsConfigHandler.getDefaultChatColor(e.getPlayer());
        else if (ColorsConfigHandler.getChatColorFromName(chatColorName) != null)activeChatColor = ColorsConfigHandler.getChatColorFromName(chatColorName);
        else activeChatColor = ColorsConfigHandler.getDefaultChatColor(e.getPlayer());
        String formatting = ""+activeChatColor.getColorList().get(0)+(activeChatColor.isBold()?ChatColor.BOLD:"")+(activeChatColor.isUnderline()?ChatColor.UNDERLINE:"")+(activeChatColor.isItalic()?ChatColor.ITALIC:"");
        //e.setFormat("{factions_nameformatted}"+ChatColor.RESET+"%s"+ChatColor.RESET+"{active_title_formatted}"+ChatColor.getLastColors(e.getPlayer().getDisplayName().replaceFirst(ChatColor.RESET+"$",""))+": "+formatting+"%s");
        e.setFormat(""+ChatColor.RESET+"{deluxetags_tag}"+ChatColor.YELLOW+"#1 "+ChatColor.RESET+"%s"+ChatColor.getLastColors(e.getPlayer().getDisplayName().replaceFirst(ChatColor.RESET+"$",""))+ChatColor.GRAY+" » "+formatting+"%s");

        for(ChatColor color : ChatColor.values()) {
            if(e.getPlayer().hasPermission("chatcolor."+color.name()))e.setMessage(e.getMessage().replaceAll("&"+color.getChar(),color.toString()));
        }
    }

    @EventHandler
    public void onNickChange(NickChangeEvent e) throws IOException {
        e.setCancelled(true);
        String newNick = e.getValue();
        if(e.getValue() == null) newNick = e.getController().getName();
        User user = (User) e.getController();
        user.setNickname((e.getValue()==null?"":"~")+newNick);
        NickHandler.updatePlayerNick(user.getBase());
    }
}
