package com.nur.chathandler;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.io.IOException;

public class NickHandler {
    static final String starPrefix = "\u2726 ";

    public static void updatePlayerNick(Player player) throws IOException {
        String nameColorName = Main.getRDatabase().getNameColor(player.getUniqueId().toString());
        CustomNameColor nameColor;
        if(nameColorName.equals(""))nameColor = null;
        else nameColor = ColorsConfigHandler.getNameColorFromName(nameColorName);
        String formattedNewNick;
        if(nameColor == null)formattedNewNick = ChatColor.stripColor(NickHandler.getNickname(player));
        else formattedNewNick = nameColor.formatString(ChatColor.stripColor(NickHandler.getNickname(player)));
        ((Essentials) Bukkit.getPluginManager().getPlugin("Essentials")).getUser(player).setNickname(formattedNewNick);
        ((Essentials) Bukkit.getPluginManager().getPlugin("Essentials")).getUser(player).setDisplayNick();
    }

    public static String getNickname(Player p) {
        String nickWithoutStar;
        User essentialsUser = ((Essentials) Bukkit.getPluginManager().getPlugin("Essentials")).getUser(p);
        if(essentialsUser==null)nickWithoutStar = p.getName();
        else if(essentialsUser.getNickname()!=null&&essentialsUser.getNickname().contains("~"))nickWithoutStar = essentialsUser.getNickname().replace(starPrefix,"");
        else nickWithoutStar = p.getName();
        boolean hasStar = false;
        for (PermissionAttachmentInfo perm : p.getEffectivePermissions()) {
            if (perm.getValue() && "saico.chat.star".equalsIgnoreCase(perm.getPermission())) {
                hasStar = true;
                break;
            }
        }

        return (hasStar?starPrefix:"")+nickWithoutStar;
    }
}
