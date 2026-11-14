
package dev.vivace.command;

import dev.vivace.Vivace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VivaceCommand implements CommandExecutor, TabCompleter {

    private final Vivace plugin;

    public VivaceCommand(Vivace plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (args.length == 0) {
            plugin.getLanguageManager().sendMessage(sender, "usage");
            return true;
        }

        switch (args[0].toLowerCase()) {

            case "reload" -> {
                if (!plugin.getPermissionManager().hasPermission(sender, "admin")) {
                    plugin.getLanguageManager().sendMessage(sender, "no-permission");
                    return true;
                }

                plugin.reloadConfig();
                plugin.getLanguageManager().loadLanguages();

                plugin.getLanguageManager().sendMessage(sender, "reload");
                return true;
            }

            case "info" -> {
                plugin.getLanguageManager().sendMessage(
                        sender,
                        "info",
                        Map.of(
                                "version", plugin.getDescription().getVersion(),
                                "api", "Paper"
                        )
                );
                return true;
            }

            default -> {
                plugin.getLanguageManager().sendMessage(sender, "usage");
                return true;
            }
        }
    }

    @Override
    public List<String> onTabComplete(
            CommandSender sender,
            Command command,
            String alias,
            String[] args
    ) {
        if (args.length == 1) {
            List<String> suggestions = new ArrayList<>();

            if (plugin.getPermissionManager().hasPermission(sender, "admin")) {
                suggestions.add("reload");
            }

            suggestions.add("info");

            String input = args[0].toLowerCase();

            suggestions.removeIf(s -> !s.startsWith(input));

            return suggestions;
        }

        return Collections.emptyList();
    }
}
