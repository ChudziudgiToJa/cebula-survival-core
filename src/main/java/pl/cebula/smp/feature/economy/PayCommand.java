package pl.cebula.smp.feature.economy;


import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
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
    void execute(@Context Player player, @Arg Player target, @Arg double payout) {

        if (target == null) {
            MessageUtil.sendMessage(player, "&cGracz nie jest online.");
            return;
        }

        if (payout <= 0) {
            MessageUtil.sendMessage(player, "&cKwota musi być większa niż zero.");
            return;
        }

        User user = this.userService.findUserByNickName(player.getName());
        User targetUser = this.userService.findUserByNickName(target.getName());

        if (user == null) {
            MessageUtil.sendMessage(player, "&cNie znaleziono Twojego konta.");
            return;
        }

        if (targetUser == null) {
            MessageUtil.sendMessage(player, "&cNie znaleziono konta dla gracza " + target.getName() + ".");
            return;
        }

        if (user.getMoney() < payout) {
            MessageUtil.sendMessage(player, "&cNie masz wystarczająco dużo pieniędzy.");
            return;
        }

        if (target.getName().equals(player.getName())) {
            MessageUtil.sendMessage(player, "&cNie możesz wysłać monet sam sobie.");
            return;
        }

        user.setMoney(user.getMoney() - payout);
        targetUser.setMoney(targetUser.getMoney() + payout);

        this.userService.saveUser(user);
        this.userService.saveUser(targetUser);

        MessageUtil.sendMessage(player, "&aPrzelew na kwotę " + payout + " do gracza " + target.getName() + " zakończony sukcesem.");
        MessageUtil.sendMessage(target, "&aOtrzymałeś przelew w wysokości " + payout + " od gracza " + player.getName() + ".");
    }
}

