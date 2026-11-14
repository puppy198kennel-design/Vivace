
package dev.vivace.manager;

import org.bukkit.plugin.java.JavaPlugin;

public class FeatureManager {

    private final JavaPlugin plugin;

    public FeatureManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean isEnabled(String feature) {
        return plugin.getConfig().getBoolean(
                "features." + feature,
                false
        );
    }

    public void reload() {
        plugin.reloadConfig();
    }
}
