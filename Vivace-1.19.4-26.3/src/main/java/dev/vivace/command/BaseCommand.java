
package dev.vivace.command;

import dev.vivace.Vivace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BaseCommand implements CommandExecutor {

    protected final Vivace plugin;
    private final String featureName;

    protected BaseCommand(Vivace plugin, String featureName) {
        this.plugin = plugin;
        this.featureName = featureName;
    }

    @Override
    public final boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (!(sender instanceof Player player)) {
            plugin.getLanguageManager()
                    .sendMessage(sender, "player-only");
            return true;
        }

        if (!plugin.getFeatureManager().isEnabled(featureName)) {
            plugin.getLanguageManager()
                    .sendMessage(sender, "feature-disabled");
            return true;
        }

        if (!plugin.getPermissionManager()
                .hasPermission(sender, featureName)) {
            plugin.getLanguageManager()
                    .sendMessage(sender, "no-permission");
            return true;
        }

        execute(player, args);
        return true;
    }

    protected abstract void execute(Player player, String[] args);
}
