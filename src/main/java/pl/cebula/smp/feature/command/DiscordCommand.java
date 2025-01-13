package pl.cebula.smp.feature.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.util.MessageUtil;

@Command(name = "discord")
public class DiscordCommand {

    private final PluginConfiguration pluginConfiguration;

    public DiscordCommand(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    @Execute
    void send(@Context Player player) {
        MessageUtil.sendMessage(player, "&9&lDISCORD&8: &f↴");
        player.sendMessage(this.pluginConfiguration.discordUrl);
    }
}
