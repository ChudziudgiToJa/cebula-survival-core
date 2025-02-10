package pl.cebula.smp.feature.job;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.util.MessageUtil;

@Command(name = "praca")
public class JobCommand {

    private final JobInventory jobInventory;
    private final PluginConfiguration pluginConfiguration;

    public JobCommand(JobInventory jobInventory, PluginConfiguration pluginConfiguration) {
        this.jobInventory = jobInventory;
        this.pluginConfiguration = pluginConfiguration;
    }

    @Execute
    void execute(@Context Player player) {
        this.jobInventory.show(player);
    }


    @Execute(name = "addItem")
    @Permission("cebulasmp.job.admin")
    void adminAddItem(@Context Player player, @Arg("Typ pracy") JobType jobType, @Arg("szansa") double chance) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack.getType().equals(Material.AIR)) {
            MessageUtil.sendMessage(player, "&cMusisz trzymać przedmiot w ręce, aby go dodać.");
            return;
        }

        if (jobType == JobType.CLEAR) {
            MessageUtil.sendMessage(player, "&cNie możesz dodać itemu do dropu dla bezrobotnego.");
            return;
        }

        this.pluginConfiguration.jobSettings.addItem(jobType, new JobDropChance(itemStack, chance));
        this.pluginConfiguration.save();
        MessageUtil.sendMessage(player, "&aDodano przedmiot do listy dropu dla pracy: " + jobType);
    }

    @Execute(name = "removeItem")
    @Permission("cebulasmp.job.admin")
    void adminRemoveItem(@Context Player player, @Arg("Typ pracy") JobType jobType, @Arg("numer itemka") int i) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (jobType == JobType.CLEAR) {
            MessageUtil.sendMessage(player, "&cNie możesz edytować bezrobotnego.");
            return;
        }

        this.pluginConfiguration.jobSettings.removeItem(jobType,  i);
        this.pluginConfiguration.save();
        MessageUtil.sendMessage(player, "&aUsunięto z: " + jobType + " numer itemu: " + i);
    }
}
