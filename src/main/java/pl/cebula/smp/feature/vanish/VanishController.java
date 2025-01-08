package pl.cebula.smp.feature.vanish;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;
import pl.cebula.smp.util.MessageUtil;

public class VanishController implements Listener {

    private final UserService userService;
    private final SurvivalPlugin survivalPlugin;

    public VanishController(UserService userService, SurvivalPlugin survivalPlugin) {
        this.userService = userService;
        this.survivalPlugin = survivalPlugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player joiningPlayer = event.getPlayer();

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            User onlineUser = userService.findUserByUUID(onlinePlayer.getUniqueId());
            if (onlineUser != null && onlineUser.isVanish() && !joiningPlayer.hasPermission("cebulasmp.vanish.see")) {
                joiningPlayer.hidePlayer(onlinePlayer);
            }
        });

        User joiningUser = userService.findUserByUUID(joiningPlayer.getUniqueId());
        if (joiningUser != null && joiningUser.isVanish()) {
            if (!joiningPlayer.hasPermission("cebulasmp.vanish.admin")) {
                joiningUser.setVanish(false);
                joiningPlayer.setMetadata("vanished", new FixedMetadataValue(survivalPlugin, false));
                return;
            }
            joiningPlayer.setMetadata("vanished", new FixedMetadataValue(survivalPlugin, true));
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (!player.hasPermission("cebulasmp.vanish.see")) {
                    player.hidePlayer(joiningPlayer);
                }
            });
            MessageUtil.sendMessage(joiningPlayer, "&b&lV &ajest aktywny");
        }
        joiningPlayer.setMetadata("vanished", new FixedMetadataValue(survivalPlugin, false));
    }


    @EventHandler
    public void onSendMessage(AsyncChatEvent event) {
        User user = this.userService.findUserByUUID(event.getPlayer().getUniqueId());
        if (user == null) return;
        if (user.isVanish()) {
            event.setCancelled(true);
            MessageUtil.sendMessage(event.getPlayer(), "&b&lV &cNie możesz pisać na chacie, aby się nie ujawniać");
        }
    }
}
