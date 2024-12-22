package pl.cebula.smp.feature.vanish;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.util.MessageUtil;

public class VanishHandler {

    public void toggleVanish(Player player, User user, final SurvivalPlugin survivalPlugin) {
        user.setVanish(!user.isVanish());
        if (user.isVanish()) {
            player.setMetadata("vanish", new FixedMetadataValue(survivalPlugin, true));
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                if (!onlinePlayer.hasPermission("cebulasmp.vanish.see")) {
                    onlinePlayer.hidePlayer(player);
                }
            });
            MessageUtil.sendTitle(player, "", "&bvanish jest &aaktywny", 20, 50, 20);
        } else {
            player.setMetadata("vanish", new FixedMetadataValue(survivalPlugin, false));
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                onlinePlayer.showPlayer(player);
            });
            player.playSound(player.getLocation(), Sound.ENTITY_BAT_LOOP, 10, 10);
            MessageUtil.sendTitle(player, "", "&bvanish jest &cwyłączony", 20, 50, 20);
        }
    }
}
