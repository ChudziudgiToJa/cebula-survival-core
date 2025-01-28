package pl.cebula.smp.feature.itemcooldown;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ItemCooldownController implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player player) {
            if (event.getEntityType() == EntityType.ENDER_PEARL) {
                player.setCooldown(Material.ENDER_PEARL, 20 * 15);
            }
        }
    }
}
