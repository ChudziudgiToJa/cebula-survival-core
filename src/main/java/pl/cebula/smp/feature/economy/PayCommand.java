package pl.cebula.smp.feature.economy;


import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.MessageUtil;

@Command(name = "przelej")
public class PayCommand {
    private final UserService userService;

    public PayCommand(UserService userService) {
        this.userService = userService;
    }

    @Execute
    void execute(@Context Player player, @Arg User targetUser, @Arg double payout) {
        if (payout <= 0) {
            MessageUtil.sendMessage(player, "&cKwota musi być większa niż zero.");
            return;
        }

        User user = this.userService.findUserByNickName(player.getName());

        if (user == null) {
            MessageUtil.sendMessage(player, "&cNie znaleziono Twojego konta.");
            return;
        }

        if (user.getMoney() < payout) {
            MessageUtil.sendMessage(player, "&cNie masz wystarczająco dużo pieniędzy.");
            return;
        }

        if (targetUser.getNickName().equals(player.getName())) {
            MessageUtil.sendMessage(player, "&cNie możesz wysłać monet sam sobie.");
            return;
        }

        user.setMoney(user.getMoney() - payout);
        targetUser.setMoney(targetUser.getMoney() + payout);

        this.userService.saveUser(user);
        this.userService.saveUser(targetUser);

        MessageUtil.sendMessage(player, "&aPrzelew na kwotę " + payout + " do gracza " + user.getNickName() + " zakończony sukcesem.");

        Player player1 = Bukkit.getPlayer(user.getNickName());
        if (player1 != null) {
            MessageUtil.sendMessage(player1, "&aOtrzymałeś przelew w wysokości " + payout + " od gracza " + player.getName() + ".");
        }
    }
}

