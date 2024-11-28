package pl.cebula.smp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.MessageUtil;

public class JoinQuitListener implements Listener {

    private final UserService userService;

    public JoinQuitListener(UserService userService) {
        this.userService = userService;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(this.userService.findUserByNickName(player.getName()) == null) {
            User user = new User(player);
            this.userService.createUser(user);
        }
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        User user = this.userService.findUserByUUID(event.getPlayer().getUniqueId());
        this.userService.saveUser(user);
        event.setQuitMessage(null);
    }
}
