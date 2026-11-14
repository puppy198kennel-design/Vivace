package dev.vivace.command;

import dev.vivace.Vivace;
import org.bukkit.entity.Player;

import java.util.Map;

public class CoordsCommand extends BaseCommand {

    public CoordsCommand(Vivace plugin) {
        super(plugin, "coords");
    }

    @Override
    protected void execute(Player player, String[] args) {
        plugin.getLanguageManager().sendMessage(
                player,
                "coords",
                Map.of(
                        "x", String.valueOf(player.getLocation().getBlockX()),
                        "y", String.valueOf(player.getLocation().getBlockY()),
                        "z", String.valueOf(player.getLocation().getBlockZ())
                )
        );
    }
}
