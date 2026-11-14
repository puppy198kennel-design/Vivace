
package dev.vivace.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class TrashHolder implements InventoryHolder {

    private Inventory inventory;
    private boolean resolved;

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
