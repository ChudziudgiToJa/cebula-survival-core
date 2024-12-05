package pl.cebula.smp.feature.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.util.MessageUtil;
import pl.cebula.smp.util.SimpleInventory;

import java.util.Objects;

@Command(name = "kosz")
public class TrashCommand {

    private final SurvivalPlugin survivalPlugin;

    public TrashCommand(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
    }

    @Execute
    void execute(@Context Player sender) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin,9 * 6, MessageUtil.smallText(MessageUtil.smallTextToColor("&e&lkosz")));
        Inventory inventory = simpleInventory.getInventory();
        sender.openInventory(inventory);
    }
}
