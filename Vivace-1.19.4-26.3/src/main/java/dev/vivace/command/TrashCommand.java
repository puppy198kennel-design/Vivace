
package dev.vivace.command;

import dev.vivace.Vivace;
import dev.vivace.gui.TrashManager;
import org.bukkit.entity.Player;

public class TrashCommand extends BaseCommand {

    private final TrashManager trashManager;

    public TrashCommand(Vivace plugin, TrashManager trashManager) {
        super(plugin, "trash");
        this.trashManager = trashManager;
    }

    @Override
    protected void execute(Player player, String[] args) {
        trashManager.open(player);
    }
}
