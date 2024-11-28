package pl.cebula.smp.feature.job;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;
import pl.cebula.smp.SurvivalPlugin;

@Command(name = "praca")
public class JobCommand {

    private final JobInventory jobInventory;

    public JobCommand(JobInventory jobInventory) {
        this.jobInventory = jobInventory;
    }

    @Execute
    void execute(@Context Player player) {
        this.jobInventory.show(player);
    }
}
