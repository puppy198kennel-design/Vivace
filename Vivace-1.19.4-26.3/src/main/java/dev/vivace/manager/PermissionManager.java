
package dev.vivace.manager;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class PermissionManager {

    private final JavaPlugin plugin;

    public PermissionManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean hasPermission(
            CommandSender sender,
            String command
    ) {
        String permission = plugin.getConfig().getString(
                "permissions." + command,
                "vivace.command." + command
        );

        // An empty permission in config.yml means this command is public.
        if (permission == null || permission.isBlank()) {
            return true;
        }

        return sender.hasPermission(permission);
    }
}
