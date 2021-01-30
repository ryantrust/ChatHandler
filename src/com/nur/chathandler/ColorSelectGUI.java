package com.nur.chathandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorSelectGUI implements Listener {
    private Inventory inv;
    private int screen; // 0 = main, 1 = name colors, 2 = chat colors
    /*private final ItemStack storeChest = createGuiItem(new ItemStack(Material.CHEST, 1), ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+"Color Information",
            ChatColor.GRAY+"Colors give you the ability to customize your "+ChatColor.UNDERLINE+"Chat"+ChatColor.GRAY+" & "+ChatColor.UNDERLINE+"Nametag"+ChatColor.GRAY+"!",
            ""+ChatColor.GRAY,
            ChatColor.LIGHT_PURPLE+"Obtained from:",
            ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+" * "+ChatColor.GRAY+"Store "+ChatColor.DARK_GRAY+"["+ChatColor.LIGHT_PURPLE+"store.saicopvp.com"+ChatColor.DARK_GRAY+"]",
            ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+" * "+ChatColor.GRAY+"Rare Rewards",
            ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+" * "+ChatColor.GRAY+"Tier 1 Rank",
            ""+ChatColor.GRAY,
            ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+"(!) "+ChatColor.LIGHT_PURPLE+"Click to go to our "+ChatColor.UNDERLINE+"Store"+ChatColor.LIGHT_PURPLE+"!");*/
    private final ItemStack storeChest = createGuiItem(new ItemStack(Material.CHEST, 1), ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+"Color Information",
            ChatColor.GRAY+"Colors give you the ability to customize your "+ChatColor.UNDERLINE+"Chat"+ChatColor.GRAY+" & "+ChatColor.UNDERLINE+"Nametag"+ChatColor.GRAY+"!",
            ""+ChatColor.GRAY,
            ChatColor.LIGHT_PURPLE+"Obtained from:",
            ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+" * "+ChatColor.GRAY+"Completing Main Ranks");
            //""+ChatColor.GRAY,
            //""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+"(!) "+ChatColor.LIGHT_PURPLE+"Click to go to our "+ChatColor.UNDERLINE+"Store"+ChatColor.LIGHT_PURPLE+"!");
    private ItemStack nameColorsOpen;
    private ItemStack chatColorsOpen;
    private final HumanEntity p;
    private Map<ItemStack, CustomColor> colorsOnPage = new HashMap<>();

    public ColorSelectGUI(HumanEntity p) throws IOException {
        this.p = p;
        inv = Bukkit.createInventory(null, 9, "");
        // Put the items for first screen into the inventory
        initializeFirstScreen();
    }

    public void initializeFirstScreen() throws IOException {
        closeInventory();
        screen = 0;
        updateNameColorsOpenItem();
        updateChatColorsOpenItem();
        inv = Bukkit.createInventory(null, 9, ""+ChatColor.LIGHT_PURPLE+ ChatColor.UNDERLINE+"Colors");
        inv.setItem(3, nameColorsOpen);
        inv.setItem(4, storeChest);
        inv.setItem(5, chatColorsOpen);
        openInventory();
    }

    public void updateNameColorsOpenItem() throws IOException {
        String activeNameColorName = Main.getRDatabase().getNameColor(p.getUniqueId().toString());
        String activeNameColorColorNameFormatted;
        if("".equals(activeNameColorName)) activeNameColorColorNameFormatted = ChatColor.RED + "None";
        else {
            CustomNameColor activeNameColor = ColorsConfigHandler.getNameColorFromName(activeNameColorName);
            assert activeNameColor != null;
            activeNameColorColorNameFormatted = activeNameColor.formatString(activeNameColor.getColorName());
        }
        nameColorsOpen = createGuiItem(new ItemStack(Material.NAME_TAG, 1),""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+"Name Colors",
                ChatColor.GRAY+"Bring some style with a Custom "+ChatColor.UNDERLINE+"Name Color"+ChatColor.GRAY+"!",
                ""+ChatColor.GRAY,
                ""+ChatColor.LIGHT_PURPLE+"Color Information:",
                ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+" * "+ChatColor.GRAY+"Color Type: "+ChatColor.LIGHT_PURPLE+"Name",
                ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+" * "+ChatColor.GRAY+"Color Selected: "+ChatColor.RESET+activeNameColorColorNameFormatted,
                ""+ChatColor.GRAY,
                ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+"(!) "+ChatColor.LIGHT_PURPLE+"Click to view all Name Colors!"
        );
    }

    public void updateChatColorsOpenItem() throws IOException {
        String activeChatColorName = Main.getRDatabase().getChatColor(p.getUniqueId().toString());
        String activeChatColorColorNameFormatted;
        if("".equals(activeChatColorName)) activeChatColorColorNameFormatted = ChatColor.RED + "None";
        else {
            CustomChatColor activeChatColor = ColorsConfigHandler.getChatColorFromName(activeChatColorName);
            assert activeChatColor != null;
            activeChatColorColorNameFormatted = activeChatColor.formatString(activeChatColor.getColorName());
        }
        chatColorsOpen = createGuiItem(new ItemStack(Material.BOOK, 1),""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+"Chat Colors",
                ChatColor.GRAY+"Show off your custom "+ChatColor.UNDERLINE+"Chat Color"+ChatColor.GRAY+"!",
                ""+ChatColor.GRAY,
                ""+ChatColor.LIGHT_PURPLE+"Information:",
                ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+" * "+ChatColor.GRAY+"Color Type: "+ChatColor.LIGHT_PURPLE+"Chat",
                ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+" * "+ChatColor.GRAY+"Color Selected: "+ChatColor.RESET+activeChatColorColorNameFormatted,
                ""+ChatColor.GRAY,
                ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+"(!) "+ChatColor.LIGHT_PURPLE+"Click to view all Chat Colors!"
        );
    }


    public void updateNameColors() throws IOException {
        screen = 1;
        List<CustomNameColor> nameColors = ColorsConfigHandler.getAllNameColors();
        closeInventory();
        inv = Bukkit.createInventory(null, (int) (Math.ceil(((double)Math.min(54,nameColors.size()))/9)*9), ""+ChatColor.LIGHT_PURPLE+ChatColor.UNDERLINE+"Name Colors");
        colorsOnPage = new HashMap<>();
        for(int i=0;i<Math.min(54,nameColors.size());i++) {
            CustomNameColor nameColor = nameColors.get(i);
            ItemStack colorItem = createGuiItem(nameColor.getItem(),""+ChatColor.WHITE+ChatColor.BOLD+"Name Color: "+ChatColor.RESET+nameColor.formatString(p.getName()),
                    p.hasPermission("saico.color."+nameColor.getName())?""+ChatColor.GREEN+ChatColor.BOLD+"Unlocked":""+ChatColor.RED+ChatColor.BOLD+"Locked",
                    ""+ChatColor.GRAY,
                    ""+ChatColor.LIGHT_PURPLE+"Color Information:",
                    ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+" * "+ChatColor.GRAY+"Color: "+ChatColor.RESET+nameColor.formatString(nameColor.getColorName()),
                    ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+" * "+ChatColor.GRAY+"Obtained: "+ChatColor.RESET+ChatColor.translateAlternateColorCodes('&',nameColor.getObtained()),
                    ""+ChatColor.GRAY,
                    p.hasPermission("saico.color."+nameColor.getName())?""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+"(!) "+ChatColor.LIGHT_PURPLE+"Click to Select this "+ChatColor.UNDERLINE+"Name Color"+ChatColor.LIGHT_PURPLE+"!":""+ChatColor.RED+ChatColor.BOLD+"(!) "+ChatColor.RED+"You don't have this "+ChatColor.UNDERLINE+"Name Color"+ChatColor.RED+"!"
            );
            inv.setItem(i%54, colorItem);
            colorsOnPage.put(colorItem,nameColor);
        }
        openInventory();
    }

    public void updateChatColors() throws IOException {
        screen = 2;
        List<CustomChatColor> chatColors = ColorsConfigHandler.getAllChatColors();
        closeInventory();
        inv = Bukkit.createInventory(null, (int) (Math.ceil(((double)Math.min(54,chatColors.size()))/9)*9), ""+ChatColor.LIGHT_PURPLE+ChatColor.UNDERLINE+"Chat Colors");
        colorsOnPage = new HashMap<>();
        for(int i=0;i<Math.min(54,chatColors.size());i++) {
            CustomChatColor chatColor = chatColors.get(i);
            ItemStack colorItem = createGuiItem(chatColor.getItem(),""+ChatColor.WHITE+ChatColor.BOLD+"Chat Color: "+ChatColor.RESET+((Player)p).getDisplayName()+": "+chatColor.formatString("Message"),
                    p.hasPermission("saico.chatcolor."+chatColor.getName())?""+ChatColor.GREEN+ChatColor.BOLD+"Unlocked":""+ChatColor.RED+ChatColor.BOLD+"Locked",
                    ""+ChatColor.GRAY,
                    ""+ChatColor.LIGHT_PURPLE+"Color Information:",
                    ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+" * "+ChatColor.GRAY+"Color: "+ChatColor.RESET+chatColor.formatString(chatColor.getColorName()),
                    ""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+" * "+ChatColor.GRAY+"Obtained: "+ChatColor.RESET+ChatColor.translateAlternateColorCodes('&',chatColor.getObtained()),
                    ""+ChatColor.GRAY,
                    p.hasPermission("saico.chatcolor."+chatColor.getName())?""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+"(!) "+ChatColor.LIGHT_PURPLE+"Click to Select this "+ChatColor.UNDERLINE+"Chat Color"+ChatColor.LIGHT_PURPLE+"!":""+ChatColor.RED+ChatColor.BOLD+"(!) "+ChatColor.RED+"You don't have this "+ChatColor.UNDERLINE+"Chat Color"+ChatColor.RED+"!"
            );
            inv.setItem(i%54, colorItem);
            colorsOnPage.put(colorItem,chatColor);
        }
        openInventory();
    }

    protected ItemStack createGuiItem(final ItemStack itemStack, final String name, final String... lore) {
        final ItemStack item = itemStack;
        final ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);
        item.setData(itemStack.getData());
        item.setDurability(itemStack.getDurability());
        return item;
    }

    public void openInventory() {
        openInventory(p);
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    public void closeInventory() {
        if(inv.getViewers().contains(p))p.closeInventory();
    }

    public void closeInventory(final HumanEntity ent) {
        if(inv.getViewers().contains(ent))ent.closeInventory();
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) throws IOException {
        if(inv.getViewers().contains(e.getWhoClicked())) {
            e.setCancelled(true);
            if(storeChest.equals(e.getCurrentItem())) {
                HumanEntity p = e.getWhoClicked();
                //p.sendMessage(""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+"(!) "+ChatColor.LIGHT_PURPLE+"SaiCoPvP Store: https://store.saicopvp.com/");
                closeInventory(p);
            }
            if(nameColorsOpen.equals(e.getCurrentItem())) {
                updateNameColors();
                openInventory();
            }
            if(chatColorsOpen.equals(e.getCurrentItem())) {
                updateChatColors();
                openInventory();
            }
            if(colorsOnPage.containsKey(e.getCurrentItem())) {
                if(screen==1&&p.hasPermission("saico.color."+colorsOnPage.get(e.getCurrentItem()).getName())) {
                    Main.getRDatabase().setNameColor(p.getUniqueId().toString(),colorsOnPage.get(e.getCurrentItem()).getName());
                    closeInventory();
                    NickHandler.updatePlayerNick((Player) p);
                } else if (screen==2&&p.hasPermission("saico.chatcolor."+colorsOnPage.get(e.getCurrentItem()).getName())) {
                    Main.getRDatabase().setChatColor(p.getUniqueId().toString(),colorsOnPage.get(e.getCurrentItem()).getName());
                    closeInventory();
                }
                //p.sendMessage(""+ChatColor.LIGHT_PURPLE+ChatColor.BOLD+"(!) "+ChatColor.LIGHT_PURPLE+"You have "+ChatColor.UNDERLINE+"Selected"+ChatColor.LIGHT_PURPLE+" the "+ChatColor.BOLD+"Title: "+ChatColor.DARK_GRAY+"["+ChatColor.translateAlternateColorCodes('&',colorsOnPage.get(e.getCurrentItem()).getName())+ChatColor.DARK_GRAY+"]"+ChatColor.LIGHT_PURPLE+"!");
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (inv.equals(e.getInventory())) e.setCancelled(true);
    }
}
