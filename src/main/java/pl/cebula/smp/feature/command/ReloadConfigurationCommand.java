package pl.cebula.smp.feature.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import pl.cebula.smp.configuration.ConfigService;
import pl.cebula.smp.util.MessageUtil;

@Command(name = "core")
@Permission("cebulasmp.admin.reload.config")
public class ReloadConfigurationCommand {

    private final ConfigService configService;

    public ReloadConfigurationCommand(ConfigService configService) {
        this.configService = configService;
    }

    @Execute(name = "reload")
    void reload(@Context CommandSender sender) {
        this.configService.reload();
        MessageUtil.sendMessage(sender, "&aPrzeładowano config.");
    }
}
