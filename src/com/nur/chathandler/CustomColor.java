package com.nur.chathandler;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomColor {
    private final String name;
    private final List<ChatColor> colorList;
    private final String colorName;
    private final String obtained;
    private final boolean bold;
    private final boolean underline;
    private final boolean italic;
    private final ItemStack item;

    CustomColor(String name, List<ChatColor> colorList, String colorName, String obtained, boolean bold, boolean underline, boolean italic, ItemStack item) {
        this.name = name;
        this.colorList = colorList;
        this.colorName = colorName;
        this.obtained = obtained;
        this.bold = bold;
        this.underline = underline;
        this.italic = italic;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public List<ChatColor> getColorList() {
        return colorList;
    }

    public String getColorName() {
        return colorName;
    }

    public String getObtained() {
        return obtained;
    }

    public boolean isBold() {
        return bold;
    }

    public boolean isUnderline() {
        return underline;
    }

    public boolean isItalic() {
        return italic;
    }

    public ItemStack getItem() {
        return item;
    }

    public String formatString(String text) {
        StringBuilder formattedText = new StringBuilder();
        int spacesFound = 0;
        for (int i = 0; i < text.length(); i++){
            if(" ".charAt(0) == text.charAt(i)) {
                spacesFound++;
                formattedText.append(" ");
                continue;
            }
            formattedText.append(colorList.get((i-spacesFound) % colorList.size())).append(isBold()?ChatColor.BOLD:"").append(isUnderline()?ChatColor.UNDERLINE:"").append(isItalic()?ChatColor.ITALIC:"").append(text.charAt(i));
        }
        return formattedText.toString();
    }
}
