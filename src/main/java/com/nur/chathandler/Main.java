package com.nur.chathandler;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Database db;
    public static Main instance;
    @Override
    public void onEnable() {
        instance = this;
        db = new SQLiteHandler(this);
        db.load();
        Bukkit.getPluginManager().registerEvents(new ChatEventHandler(), this);
        Bukkit.getPluginManager().registerEvents(new LoginHandler(), this);
        this.getCommand("color").setExecutor(new CommandColor());
        this.getCommand("setnamecolorother").setExecutor(new CommandSetNameColorOther());
        this.getCommand("setchatcolorother").setExecutor(new CommandSetChatColorOther());
    }


    public static Database getRDatabase() {
        return db;
    }
}