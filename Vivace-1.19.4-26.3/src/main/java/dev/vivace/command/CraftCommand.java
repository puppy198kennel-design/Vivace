
package dev.vivace.command;

import dev.vivace.Vivace;
import org.bukkit.entity.Player;

public class CraftCommand extends BaseCommand {

    public CraftCommand(Vivace plugin) {
        super(plugin, "craft");
    }

    @Override
    protected void execute(Player player, String[] args) {
        player.openWorkbench(null, true);

        plugin.getLanguageManager()
                .sendMessage(player, "craft-opened");
    }
}
