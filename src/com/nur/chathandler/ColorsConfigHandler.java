package com.nur.chathandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nur.confighandler.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.*;

public class ColorsConfigHandler {
    static final String defaultChatColorName = "grey";

    public static CustomNameColor getNameColorFromName(String name) throws IOException, NullPointerException {
        JsonObject jsonObject = new JsonParser().parse(ConfigHandler.getConfigTextCached("namecolors.json")).getAsJsonObject();
        if(!jsonObject.has(name)) {
            //Bukkit.broadcast(""+ChatColor.RED+ChatColor.BOLD+"(!) "+ChatColor.RED+"Name color "+name+" is not defined.","nur.consolespam");
            Bukkit.getLogger().warning(""+ ChatColor.RED+ChatColor.BOLD+"(!) "+ChatColor.RED+"Name color "+name+" is not defined.");
            return null;
        }
        JsonObject nameColor = jsonObject.get(name).getAsJsonObject();
        JsonArray colorListJson = nameColor.get("colors").getAsJsonArray();
        List<ChatColor> colorList = new ArrayList<>();
        for (int i = 0; i < colorListJson.size(); i++) colorList.add(ChatColor.getByChar(colorListJson.get(i).getAsString()));
        String[] itemIDStrings = nameColor.get("item").getAsString().split(":");
        int type = Integer.parseInt(itemIDStrings[0]);
        int data = (itemIDStrings.length < 2 ? 0 : Integer.parseInt(itemIDStrings[1]));
        ItemStack item = new ItemStack(type, 1, (short) data);
        //item.setData(new MaterialData(type, (byte) data));
        return new CustomNameColor(name.toLowerCase(), colorList, nameColor.get("name").getAsString(), nameColor.get("obtained").getAsString(), nameColor.get("bold").getAsBoolean(), nameColor.get("underline").getAsBoolean(), nameColor.get("italics").getAsBoolean(), item);
    }

    public static List<CustomNameColor> getAllNameColors() throws IOException {
        List<CustomNameColor> nameColors = new ArrayList<CustomNameColor>();
        JsonObject jsonObject = new JsonParser().parse(ConfigHandler.getConfigTextCached("namecolors.json")).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> gsonNameColors = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> gsonNameColorEntry : gsonNameColors) {
            JsonObject gsonNameColor = gsonNameColorEntry.getValue().getAsJsonObject();
            JsonArray colorListJson = gsonNameColor.get("colors").getAsJsonArray();
            List<ChatColor> colorList = new ArrayList<>();
            for (int i = 0; i < colorListJson.size(); i++) colorList.add(ChatColor.getByChar(colorListJson.get(i).getAsString()));
            String[] itemIDStrings = gsonNameColor.get("item").getAsString().split(":");
            int type = Integer.parseInt(itemIDStrings[0]);
            int data = (itemIDStrings.length < 2 ? 0 : Integer.parseInt(itemIDStrings[1]));
            ItemStack item = new ItemStack(type, 1, (short) data);
            //item.setData(new MaterialData(type, (byte) data));
            //item.setDurability((short) data);
            nameColors.add(new CustomNameColor(gsonNameColorEntry.getKey(), colorList, gsonNameColor.get("name").getAsString(), gsonNameColor.get("obtained").getAsString(), gsonNameColor.get("bold").getAsBoolean(), gsonNameColor.get("underline").getAsBoolean(), gsonNameColor.get("italics").getAsBoolean(), item));
        }
        return nameColors;
    }

    public static List<CustomNameColor> getAllPlayerNameColors(Player p) throws IOException {
        List<CustomNameColor> userNameColors = new ArrayList<>();
        for(CustomNameColor nameColor : getAllNameColors()) {
            if (p.isPermissionSet("saico.color."+nameColor.getName())&&p.hasPermission("saico.color."+nameColor.getName())) userNameColors.add(nameColor);
        }
        return userNameColors;
    }

    public static CustomChatColor getChatColorFromName(String name) throws IOException, NullPointerException {
        JsonObject jsonObject = new JsonParser().parse(ConfigHandler.getConfigTextCached("chatcolors.json")).getAsJsonObject();
        if(!jsonObject.has(name)) {
            //Bukkit.broadcast(""+ChatColor.RED+ChatColor.BOLD+"(!) "+ChatColor.RED+"Chat color "+name+" is not defined.","nur.consolespam");
            if(!"".equals(name))Bukkit.getLogger().warning(""+ ChatColor.RED+ChatColor.BOLD+"(!) "+ChatColor.RED+"Chat color "+name+" is not defined.");
            return null;
        }
        JsonObject chatColor = jsonObject.get(name).getAsJsonObject();
        JsonArray colorListJson = chatColor.get("colors").getAsJsonArray();
        List<ChatColor> colorList = new ArrayList<>();
        for (int i = 0; i < colorListJson.size(); i++) colorList.add(ChatColor.getByChar(colorListJson.get(i).getAsString()));
        String[] itemIDStrings = chatColor.get("item").getAsString().split(":");
        int type = Integer.parseInt(itemIDStrings[0]);
        int data = (itemIDStrings.length < 2 ? 0 : Integer.parseInt(itemIDStrings[1]));
        ItemStack item = new ItemStack(type, 1, (short) data);
        //item.setData(new MaterialData(type, (byte) data));
        return new CustomChatColor(name.toLowerCase(), colorList, chatColor.get("name").getAsString(), chatColor.get("obtained").getAsString(), chatColor.get("bold").getAsBoolean(), chatColor.get("underline").getAsBoolean(), chatColor.get("italics").getAsBoolean(), item);
    }

    public static List<CustomChatColor> getAllChatColors() throws IOException {
        List<CustomChatColor> chatColors = new ArrayList<CustomChatColor>();
        JsonObject jsonObject = new JsonParser().parse(ConfigHandler.getConfigTextCached("chatcolors.json")).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> gsonChatColors = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> gsonChatColorEntry : gsonChatColors) {
            JsonObject gsonChatColor = gsonChatColorEntry.getValue().getAsJsonObject();
            JsonArray colorListJson = gsonChatColor.get("colors").getAsJsonArray();
            List<ChatColor> colorList = new ArrayList<>();
            for (int i = 0; i < colorListJson.size(); i++) colorList.add(ChatColor.getByChar(colorListJson.get(i).getAsString()));
            String[] itemIDStrings = gsonChatColor.get("item").getAsString().split(":");
            int type = Integer.parseInt(itemIDStrings[0]);
            int data = (itemIDStrings.length < 2 ? 0 : Integer.parseInt(itemIDStrings[1]));
            ItemStack item = new ItemStack(type, 1, (short) data);
            //item.setData(new MaterialData(type, (byte) data));
            //item.setDurability((short) data);
            chatColors.add(new CustomChatColor(gsonChatColorEntry.getKey(), colorList, gsonChatColor.get("name").getAsString(), gsonChatColor.get("obtained").getAsString(), gsonChatColor.get("bold").getAsBoolean(), gsonChatColor.get("underline").getAsBoolean(), gsonChatColor.get("italics").getAsBoolean(), item));
        }
        return chatColors;
    }

    public static List<CustomChatColor> getAllPlayerChatColors(Player p) throws IOException {
        List<CustomChatColor> userChatColors = new ArrayList<>();
        for(CustomChatColor chatColor : getAllChatColors()) {
            if (p.isPermissionSet("saico.chatcolor."+chatColor.getName())&&p.hasPermission("saico.chatcolor."+chatColor.getName())) userChatColors.add(chatColor);
        }
        return userChatColors;
    }

    public static CustomChatColor getDefaultChatColor(Player player) throws IOException {
        List<CustomChatColor> allChatColors = getAllChatColors();
        Collections.reverse(allChatColors);
        for (CustomChatColor chatColor : allChatColors) {
            if (player.isPermissionSet("saico.chatcolor."+chatColor.getName())&&player.hasPermission("saico.chatcolor."+chatColor.getName())) {
                return chatColor;
            }
        }
        Collections.reverse(allChatColors);
        return allChatColors.get(0);
    }
}