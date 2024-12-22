package pl.cebula.smp.feature.vanish;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.MessageUtil;

@Permission("cebulasmp.command.vanish")
@Command(name = "vanish", aliases = {"v"})
public class VanishCommand {

    private final UserService userService;
    private final VanishHandler vanishHandler;
    private final SurvivalPlugin survivalPlugin;

    public VanishCommand(UserService userService, VanishHandler vanishHandler, SurvivalPlugin survivalPlugin) {
        this.userService = userService;
        this.vanishHandler = vanishHandler;
        this.survivalPlugin = survivalPlugin;
    }


    @Execute
    void own(@Context Player player) {
        User user = this.userService.findUserByUUID(player.getUniqueId());
        if (user == null) {
            MessageUtil.sendMessage(player, "&cNie odnaleziono Twojego użytkownika w bazie danych.");
            return;
        }
        vanishHandler.toggleVanish(player, user, this.survivalPlugin);
    }

    @Execute
    void target(@Context Player player, @OptionalArg String s) {
        if (s == null || s.isEmpty()) {
            MessageUtil.sendMessage(player, "&cPodaj nick gracza, aby zmienić jego status vanish.");
            return;
        }
        User user = this.userService.findUserByNickName(s);
        if (user == null) {
            MessageUtil.sendMessage(player, "&cNie odnaleziono Twojego użytkownika w bazie danych.");
            return;
        }
        vanishHandler.toggleVanish(player, user, this.survivalPlugin);
    }
}