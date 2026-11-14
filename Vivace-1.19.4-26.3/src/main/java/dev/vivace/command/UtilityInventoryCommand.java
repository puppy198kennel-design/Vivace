
package dev.vivace.command;

import dev.vivace.Vivace;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class UtilityInventoryCommand extends BaseCommand {

    private final InventoryType inventoryType;
    private final String messageKey;

    public UtilityInventoryCommand(
            Vivace plugin,
            String feature,
            InventoryType inventoryType,
            String messageKey
    ) {
        super(plugin, feature);
        this.inventoryType = inventoryType;
        this.messageKey = messageKey;
    }

    @Override
    protected void execute(Player player, String[] args) {

        switch (inventoryType) {
            case ANVIL:
                player.openAnvil(null, true);
                break;

            case STONECUTTER:
                player.openStonecutter(null, true);
                break;

            default:
                return;
        }

        plugin.getLanguageManager()
                .sendMessage(player, messageKey);
    }
}
