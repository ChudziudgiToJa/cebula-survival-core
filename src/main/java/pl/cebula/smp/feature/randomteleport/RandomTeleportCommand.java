package pl.cebula.smp.feature.randomteleport;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.util.MessageUtil;

@Command(name = "randomtp")
@Permission("cebulasmp.randomtp.admin")
public class RandomTeleportCommand {

    private final PluginConfiguration pluginConfiguration;

    public RandomTeleportCommand(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    @Execute(name = "set")
    void setButton(@Context Player player) {
        Block targetBlock = player.getTargetBlock(null, 6);
        if (this.pluginConfiguration.randomTeleportSettings.allowButtonsToSetRtp.contains(targetBlock.getType())) {
            this.pluginConfiguration.randomTeleportSettings.buttonsLocations.add(targetBlock.getLocation());
            this.pluginConfiguration.save();
            MessageUtil.sendMessage(player, "&aDodano guzik do listy rtp");
        } else {
            MessageUtil.sendMessage(player, "&cBlok sie nie zalicza do akceptowalnych guzików");
        }
    }

}
