package pl.cebula.smp.feature.user.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.MessageUtil;

@Command(name = "user")
@Permission("cebulasmp.user.admin")
public class UserCommand {

    private final UserService userService;

    public UserCommand(UserService userService) {
        this.userService = userService;
    }

    @Execute
    void delete(@Context Player player,@Arg User user) {
        Player target = Bukkit.getPlayer(user.getNickName());

        if (target != null) {
            target.kick();
        }

        this.userService.removeUser(user);
        MessageUtil.sendMessage(player,"&cUsunięto konto gracza: " + user.getNickName());
    }
}
