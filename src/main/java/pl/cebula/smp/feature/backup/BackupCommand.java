package pl.cebula.smp.feature.backup;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Command(name = "backup")
@Permission("cebulasmp.backup.command")
public class BackupCommand {

    private final BackupInventory backupInventory;

    public BackupCommand(BackupInventory backupInventory) {
        this.backupInventory = backupInventory;
    }

    @Execute
    void execute(@Context Player player, @Arg Player target) {
        this.backupInventory.show(player, target);
    }
}
