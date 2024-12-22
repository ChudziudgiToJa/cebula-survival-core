package pl.cebula.smp.feature.vanish;

import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.util.MessageUtil;

public class VanishHandler {

    public void toggleVanish(Player player, User user) {
        user.setVanish(!user.isVanish());
        if (user.isVanish()) {
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                if (!onlinePlayer.hasPermission("cebulasmp.vanish.see")) {
                    onlinePlayer.hidePlayer(player);
                }
            });
            MessageUtil.sendTitle(player, "", "&bvanish jest &aaktywny", 20, 50, 20);
        } else {
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                onlinePlayer.showPlayer(player);
            });
            player.playSound(player.getLocation(), Sound.ENTITY_BAT_LOOP, 10, 10);
            MessageUtil.sendTitle(player, "", "&bvanish jest &cwyłączony", 20, 50, 20);
        }
    }
}
