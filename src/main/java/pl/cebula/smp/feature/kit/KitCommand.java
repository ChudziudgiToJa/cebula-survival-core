package pl.cebula.smp.feature.kit;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import pl.cebula.smp.configuration.implementation.KitConfiguration;
import pl.cebula.smp.configuration.implementation.PluginConfiguration;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.MessageUtil;

@Command(name = "kit")
public class KitCommand {

    private final KitInventory kitInventory;
    private final KitConfiguration kitConfiguration;
    private final UserService userService;

    public KitCommand(KitInventory kitInventory, KitConfiguration kitConfiguration, UserService userService) {
        this.kitInventory = kitInventory;
        this.kitConfiguration = kitConfiguration;
        this.userService = userService;
    }

    @Execute
    void execute(@Context Player player) {
        this.kitInventory.show(player);
    }


    @Permission("cebula.kit.command.give")
    @Execute(name = "give")
    void execute(@Context Player player, @Arg Player target, @Arg String string) {
        this.kitConfiguration.kitList.forEach(kit -> {
            if (kit.getName().equals(string)) {
                kit.getItemStackArrayList().forEach(itemStack -> {
                    target.getInventory().addItem(itemStack);
                });
            }
        });
    }

    @Permission("cebula.kit.command.give")
    @Execute(name = "cleartime")
    void execute(@Context Player player, @Arg String targetName, @Arg String kitname) {
        User user = this.userService.findUserByNickName(targetName);

        if (user == null) {
            MessageUtil.sendMessage(player, "&cNie odnaleziono usera:");
            return;
        }

        user.getKits().forEach(kitData -> {
            if (kitData.getName().equals(kitname)) {
                user.getKits().remove(kitData);
                MessageUtil.sendMessage(player, "&aUsunięto czas oczekiwania na kit " + targetName + " dla: " + targetName);
            }
        });
    }
}
