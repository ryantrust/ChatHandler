package com.nur.chathandler;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

import java.io.IOException;

public class CommandColor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        ColorSelectGUI gui = null;
        try {
            gui = new ColorSelectGUI((HumanEntity) commandSender);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bukkit.getServer().getPluginManager().registerEvents(gui, Main.instance);
        //if(gui!=null)gui.openInventory((HumanEntity) sender);
        return true;
    }
}
