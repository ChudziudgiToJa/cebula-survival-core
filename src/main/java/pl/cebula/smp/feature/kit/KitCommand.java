package pl.cebula.smp.feature.kit;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "kit")
public class KitCommand {

    private final KitInventory kitInventory;

    public KitCommand(KitInventory kitInventory) {
        this.kitInventory = kitInventory;
    }

    @Execute
    void execute(@Context Player player) {
        this.kitInventory.show(player);
    }
}
