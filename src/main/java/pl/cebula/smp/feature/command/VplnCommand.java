package pl.cebula.smp.feature.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import pl.cebula.smp.feature.economy.EconomyCommandType;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.MessageUtil;

@Command(name = "vpln")
@Permission("cebulasmp.vpln.admin")
public class VplnCommand {

    private final UserService userService;

    public VplnCommand(UserService userService) {
        this.userService = userService;
    }

    @Execute
    void execute(@Context CommandSender player, @Arg String s, @Arg EconomyCommandType economyCommandType, @Arg Double amount) {
        User user = this.userService.findUserByNickName(s);

        if (user == null) {
            MessageUtil.sendMessage(player, "&cGracz &f" + s + " &cnigdy nie był na serwerze.");
            return;
        }

        double currentMoney = user.getVpln();
        String formattedAmount = String.format("%.2f", amount);

        switch (economyCommandType) {
            case ADD -> {
                user.setVpln(currentMoney + amount);
                MessageUtil.sendMessage(player, "&aDodano &f" + formattedAmount + " &ado konta gracza &f" + s + "&a.");
            }
            case SET -> {
                user.setVpln(amount);
                MessageUtil.sendMessage(player, "&aUstawiono saldo gracza &f" + s + " &ana &f" + formattedAmount + "&a.");
            }
            case REMOVE -> {
                double newAmount = Math.max(0, currentMoney - amount);
                user.setVpln(newAmount);

                if (newAmount == 0) {
                    MessageUtil.sendMessage(player, "&aSaldo gracza &f" + s + " &azostało wyzerowane.");
                } else {
                    MessageUtil.sendMessage(player, "&aOdjęto &f" + formattedAmount + " &az konta gracza &f" + s + "&a.");
                }
            }
        }
    }
}
