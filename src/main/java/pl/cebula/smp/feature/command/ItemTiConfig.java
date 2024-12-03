package pl.cebula.smp.feature.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import org.bukkit.entity.Player;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;

@Command(name = "itemtoconfig")
public class ItemTiConfig {
    public ItemTiConfig(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    private final PluginConfiguration pluginConfiguration;


    @Execute
    void execute(@Context Player sender) {
        this.pluginConfiguration.itemStackToCfg = sender.getInventory().getItemInMainHand();
        this.pluginConfiguration.save();
    }


    @Execute(name = "give")
    void execute(@Context Player sender, @OptionalArg int i) {
        sender.getInventory().addItem(this.pluginConfiguration.itemStackToCfg);
    }
}
