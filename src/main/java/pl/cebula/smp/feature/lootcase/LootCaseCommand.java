package pl.cebula.smp.feature.lootcase;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.cebula.smp.configuration.implementation.LootCaseConfiguration;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.util.ItemStackSerializable;
import pl.cebula.smp.util.MessageUtil;

@Command(name = "case")
@Permission("cebula.case.command")
public class LootCaseCommand {

    private final LootCaseConfiguration pluginConfiguration;

    public LootCaseCommand(LootCaseConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }


    @Execute
    void execute(@Context Player player) {
        MessageUtil.sendMessage(player, "Lista casów: ");
        for (LootCase lootCase : this.pluginConfiguration.lootCases) {
            MessageUtil.sendMessage(player, lootCase.getName());
        }
    }

    @Execute(name = "klucz")
    void execute(@Context CommandSender sender, @Arg Player player, @Arg String string, @Arg int i) {
        boolean found = false;
        for (LootCase lootCase : this.pluginConfiguration.lootCases) {
            if (lootCase.getName().equalsIgnoreCase(string)) {
                ItemStack itemStack = ItemStackSerializable.readItemStack(lootCase.getKeyItemStack());
                if (itemStack == null) return;
                itemStack.setAmount(i);
                player.getInventory().addItem(itemStack);
                MessageUtil.sendMessage(player, "Dodano " + i + " kluczy dla skrzyni: " + lootCase.getName());
                found = true;
                break;
            }
        }
        if (!found) {
            MessageUtil.sendMessage(player, "Nie znaleziono skrzyni o nazwie: " + string);
        }
    }

    @Execute(name = "kluczeall")
    void execute(@Context Player player, @Arg String string, @Arg Integer i) {
        if (i == null || i <= 0) {
            MessageUtil.sendMessage(player, "Podaj poprawną liczbę kluczy większą od 0.");
            return;
        }

        boolean found = false;
        for (LootCase lootCase : this.pluginConfiguration.lootCases) {
            if (lootCase.getName().equalsIgnoreCase(string)) {
                ItemStack itemStack = ItemStackSerializable.readItemStack(lootCase.getKeyItemStack());
                if (itemStack == null) return;
                itemStack.setAmount(i);
                Bukkit.getOnlinePlayers().forEach(player1 -> player1.getInventory().addItem(itemStack));
                MessageUtil.sendMessage(player, "Dodano " + i + " kluczy dla skrzyni: " + lootCase.getName() + " wszystkim graczom.");
                found = true;
                break;
            }
        }
        if (!found) {
            MessageUtil.sendMessage(player, "Nie znaleziono skrzyni o nazwie: " + string);
        }
    }

    @Permission("cebula.case.additem.admin")
    @Execute(name = "additem")
    void execute(@Context Player sender, @Arg String lootCasename,@Arg double chance) {
        this.pluginConfiguration.lootCases.forEach(lootCase -> {
            if (lootCase.getName().equals(lootCasename)) {
                lootCase.getDropItems().add(new LootCaseChance(ItemStackSerializable.write(sender.getInventory().getItemInMainHand()), chance));
                this.pluginConfiguration.save();
            }
        });
    }

    @Permission("cebula.case.additem.admin")
    @Execute(name = "setkey")
    void execute(@Context Player sender, @Arg String lootCasename) {
        this.pluginConfiguration.lootCases.forEach(lootCase -> {
            if (lootCase.getName().equals(lootCasename)) {
                lootCase.setKeyItemStack(ItemStackSerializable.write(sender.getInventory().getItemInMainHand()));
                this.pluginConfiguration.save();
            }
        });
    }
}
