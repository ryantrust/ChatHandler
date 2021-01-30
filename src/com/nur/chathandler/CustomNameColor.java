package com.nur.chathandler;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomNameColor extends CustomColor {
    CustomNameColor(String name, List<ChatColor> colorList, String colorName, String obtained, boolean bold, boolean underline, boolean italic, ItemStack item) {
        super(name, colorList, colorName, obtained, bold, underline, italic, item);
    }
}
