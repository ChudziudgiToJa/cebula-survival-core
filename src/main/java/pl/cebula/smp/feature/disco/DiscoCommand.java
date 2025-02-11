package pl.cebula.smp.feature.disco;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "disco")
public class DiscoCommand {

    private final DiscoService discoService;
    private final DiscoInventory discoInventory;

    public DiscoCommand(DiscoService discoService, DiscoInventory discoInventory) {
        this.discoService = discoService;
        this.discoInventory = discoInventory;
    }

    @Execute
    void openInventory(@Context Player player) {
        Disco disco = this.discoService.findByUuid(player.getUniqueId().toString());
        if (disco == null) return;
        this.discoInventory.show(player, disco);
    }

}
