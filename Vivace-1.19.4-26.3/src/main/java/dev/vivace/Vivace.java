
package dev.vivace;

import dev.vivace.command.*;
import dev.vivace.gui.TrashManager;
import dev.vivace.language.LanguageManager;
import dev.vivace.manager.FeatureManager;
import dev.vivace.manager.PermissionManager;

import org.bukkit.command.PluginCommand;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

public final class Vivace extends JavaPlugin {

    private LanguageManager languageManager;
    private FeatureManager featureManager;
    private PermissionManager permissionManager;
    private TrashManager trashManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        languageManager = new LanguageManager(this);
        languageManager.loadLanguages();

        featureManager = new FeatureManager(this);
        permissionManager = new PermissionManager(this);

        trashManager = new TrashManager(this);
        getServer().getPluginManager()
                .registerEvents(trashManager, this);

        registerCommands();

        getLogger().info("Vivace has been enabled!");
    }

    private void registerCommands() {
        register("ender", new EnderCommand(this));
        register("craft", new CraftCommand(this));

        register("anvil", new UtilityInventoryCommand(
                this, "anvil", InventoryType.ANVIL, "anvil-opened"
        ));

        register("stonecutter", new UtilityInventoryCommand(
                this, "stonecutter", InventoryType.STONECUTTER,
                "stonecutter-opened"
        ));

        register("coords", new CoordsCommand(this));
        register("trash", new TrashCommand(this, trashManager));

        registerVivaceCommand();
    }

    private void register(
            String name,
            org.bukkit.command.CommandExecutor executor
    ) {
        PluginCommand command = getCommand(name);

        if (command == null) {
            getLogger().severe(
                    "Could not register command: " + name
            );
            return;
        }

        command.setExecutor(executor);
    }

    private void registerVivaceCommand() {
        PluginCommand command = getCommand("vivace");

        if (command == null) {
            getLogger().severe(
                    "Could not register command: vivace"
            );
            return;
        }

        VivaceCommand executor = new VivaceCommand(this);

        command.setExecutor(executor);
        command.setTabCompleter(executor);
    }

    @Override
    public void onDisable() {
        getLogger().info("Vivace has been disabled!");
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public FeatureManager getFeatureManager() {
        return featureManager;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }
}
