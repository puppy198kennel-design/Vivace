
package dev.vivace.command;

import dev.vivace.Vivace;
import org.bukkit.entity.Player;

public class EnderCommand extends BaseCommand {

    public EnderCommand(Vivace plugin) {
        super(plugin, "ender");
    }

    @Override
    protected void execute(Player player, String[] args) {
        player.openInventory(player.getEnderChest());

        plugin.getLanguageManager()
                .sendMessage(player, "ender-opened");
    }
}
