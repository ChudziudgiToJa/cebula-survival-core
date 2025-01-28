package pl.cebula.smp.feature.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.cebula.smp.util.MessageUtil;

@Command(name = "live")
public class LiveCommand {

    @Execute
    void onCommand(@Context Player player, @Arg String s) {
        Bukkit.getOnlinePlayers().forEach(player1 -> {
            MessageUtil.sendMessage(player1, "&d&lLIVE: &7" + s);
        });
    }

}
