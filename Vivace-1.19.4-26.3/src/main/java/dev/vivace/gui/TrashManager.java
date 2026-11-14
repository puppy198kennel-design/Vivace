
package dev.vivace.gui;

import dev.vivace.Vivace;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TrashManager implements Listener {

    private final Vivace plugin;

    public TrashManager(Vivace plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        TrashHolder holder = new TrashHolder();

        Inventory inventory = Bukkit.createInventory(
                holder,
                27,
                "Vivace - Trash"
        );

        holder.setInventory(inventory);

        inventory.setItem(22, createButton(
                Material.LIME_CONCRETE,
                "<green><bold>Confirm</bold></green>"
        ));

        inventory.setItem(26, createButton(
                Material.RED_CONCRETE,
                "<red><bold>Cancel</bold></red>"
        ));

        player.openInventory(inventory);

        plugin.getLanguageManager()
                .sendMessage(player, "trash-opened");
    }

    private ItemStack createButton(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(
                MiniMessage.miniMessage().deserialize(name)
        );

        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory top = event.getView().getTopInventory();

        if (!(top.getHolder() instanceof TrashHolder holder)) {
            return;
        }

        int slot = event.getRawSlot();

        if (slot < 0) {
            event.setCancelled(true);
            return;
        }
        if (slot >= top.getSize()) {
            if (event.isShiftClick()) {
                event.setCancelled(true);
            }
            return;
        }

        if (slot == 22) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            top.clear();
            holder.setResolved(true);

            player.closeInventory();

            plugin.getLanguageManager()
                    .sendMessage(player, "trash-confirmed");

            return;
        }

        if (slot == 26 || slot >= 18) {
            event.setCancelled(true);

            if (slot == 26) {
                event.getWhoClicked().closeInventory();
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        Inventory top = event.getView().getTopInventory();

        if (!(top.getHolder() instanceof TrashHolder)) {
            return;
        }

        // Protect only the bottom three rows of the trash GUI.
        // Raw slots in the player's own inventory start at top.getSize().
        for (int slot : event.getRawSlots()) {
            if (slot >= 18 && slot < top.getSize()) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Inventory top = event.getView().getTopInventory();

        if (!(top.getHolder() instanceof TrashHolder holder)) {
            return;
        }

        if (holder.isResolved()) {
            return;
        }

        holder.setResolved(true);

        Player player = (Player) event.getPlayer();

        for (int slot = 0; slot < 18; slot++) {
            ItemStack item = top.getItem(slot);

            if (item == null || item.getType().isAir()) {
                continue;
            }

            top.setItem(slot, null);

            var leftovers = player.getInventory().addItem(item);

            leftovers.values().forEach(leftover ->
                    player.getWorld().dropItemNaturally(
                            player.getLocation(),
                            leftover
                    )
            );
        }

        plugin.getLanguageManager()
                .sendMessage(player, "trash-cancelled");
    }
}
