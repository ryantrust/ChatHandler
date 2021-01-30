package com.nur.chathandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.IOException;

public class LoginHandler implements Listener {
    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent event) throws IOException {
        NickHandler.updatePlayerNick(event.getPlayer());
    }
}
