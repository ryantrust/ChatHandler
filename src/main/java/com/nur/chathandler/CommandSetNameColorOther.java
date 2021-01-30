package com.nur.chathandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class CommandSetNameColorOther implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0) return false;
        if (Bukkit.getPlayer(args[0]) == null) {
            commandSender.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "(!) " + ChatColor.RED + args[0] + " is not an online player!");
            return true;
        }
        if (args.length == 1) {
            Main.getRDatabase().setNameColor(Bukkit.getPlayer(args[0]).getUniqueId().toString(), "");
            commandSender.sendMessage("" + ChatColor.GREEN + ChatColor.BOLD + "(!) " + ChatColor.GREEN + "Removed " + ChatColor.DARK_AQUA + Bukkit.getPlayer(args[0]).getName() + "'s" + ChatColor.GREEN + " current name color!");
            try {
                NickHandler.updatePlayerNick(Bukkit.getPlayer(args[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }


        try {
            CustomNameColor nameColor = ColorsConfigHandler.getNameColorFromName(args[1].toLowerCase());
            if (nameColor != null) {
                Main.getRDatabase().setNameColor(Bukkit.getPlayer(args[0]).getUniqueId().toString(), args[1].toLowerCase());
                commandSender.sendMessage("" + ChatColor.GREEN + ChatColor.BOLD + "(!) " + ChatColor.GREEN + "Set " + ChatColor.DARK_AQUA + Bukkit.getPlayer(args[0]).getName() + "'s" + ChatColor.GREEN + " name color to " + ChatColor.DARK_AQUA + args[1] + ChatColor.GRAY + " (" + ChatColor.RESET + nameColor.formatString(Bukkit.getPlayer(args[0]).getName()) + ChatColor.GRAY + ")" + ChatColor.GREEN + "!");
                NickHandler.updatePlayerNick(Bukkit.getPlayer(args[0]));
            } else {
                commandSender.sendMessage("" + ChatColor.RED + ChatColor.BOLD + "(!) " + ChatColor.RED + "Name color " + args[1] + " is not defined.");
            }
            return true;
        } catch (IOException e) {
            Bukkit.broadcast("" + ChatColor.RED + ChatColor.BOLD + "(!) " + ChatColor.RED + "An error has occurred: " + e.getMessage(), "nur.consolespam");
            e.printStackTrace();
            return true;
        }
    }
}
