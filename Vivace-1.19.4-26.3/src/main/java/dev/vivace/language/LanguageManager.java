
package dev.vivace.language;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LanguageManager {

    private final JavaPlugin plugin;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private final Map<String, YamlConfiguration> languages = new HashMap<>();

    private YamlConfiguration currentLanguage;

    public LanguageManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadLanguages() {
        File folder = new File(plugin.getDataFolder(), "languages");

        if (!folder.exists() && !folder.mkdirs()) {
            plugin.getLogger().severe("Could not create languages folder!");
            return;
        }

        plugin.saveResource("languages/ja_JP.yml", false);
        plugin.saveResource("languages/en_US.yml", false);

        languages.clear();

        loadLanguage("ja_JP");
        loadLanguage("en_US");

        String selected = plugin.getConfig()
                .getString("language", "ja_JP");

        currentLanguage = languages.get(selected);

        if (currentLanguage == null) {
            plugin.getLogger().warning(
                    "Language '" + selected + "' not found. Using ja_JP."
            );
            currentLanguage = languages.get("ja_JP");
        }
    }

    private void loadLanguage(String name) {
        File file = new File(
                plugin.getDataFolder(),
                "languages/" + name + ".yml"
        );

        languages.put(
                name,
                YamlConfiguration.loadConfiguration(file)
        );
    }

    public Component getMessage(String key) {
        return getMessage(key, java.util.Map.of());
    }

    public Component getMessage(
            String key,
            java.util.Map<String, String> placeholders
    ) {
        if (currentLanguage == null) {
            return Component.text(key);
        }

        String prefix = currentLanguage.getString("prefix", "");
        String message = currentLanguage.getString("messages." + key, key);

        var builder = net.kyori.adventure.text.minimessage.tag.resolver.TagResolver.builder();

        placeholders.forEach((name, value) ->
                builder.resolver(
                        net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.unparsed(name, value)
                )
        );

        Component prefixComponent = miniMessage.deserialize(prefix);
        Component messageComponent = miniMessage.deserialize(
                message,
                builder.build()
        );

        return prefixComponent
                .append(Component.space())
                .append(messageComponent);
    }

    public void sendMessage(
            org.bukkit.command.CommandSender sender,
            String key
    ) {
        sender.sendMessage(getMessage(key));
    }

    public void sendMessage(
            org.bukkit.command.CommandSender sender,
            String key,
            java.util.Map<String, String> placeholders
    ) {
        sender.sendMessage(getMessage(key, placeholders));
    }

    public void reload() {
        plugin.reloadConfig();
        loadLanguages();
    }
}
