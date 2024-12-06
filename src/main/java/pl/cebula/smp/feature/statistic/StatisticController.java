package pl.cebula.smp.feature.statistic;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.cebula.smp.feature.user.User;
import pl.cebula.smp.feature.user.UserService;

public class StatisticController implements Listener {

    private final UserService userService;

    public StatisticController(UserService userService) {
        this.userService = userService;
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player victim) {
            if (victim.getKiller() != null) {
                Player killer = victim.getKiller();
                User user = this.userService.findUserByNickName(killer.getName());
                user.setKill(user.getKill() + 1);
            }
        }
    }

    @EventHandler
    public void onDead(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        User user = this.userService.findUserByNickName(player.getName());
        user.setDead(user.getDead() + 1);
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        User user = this.userService.findUserByNickName(player.getName());
        user.setPlaceBlock(user.getPlaceBlock() + 1);
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        User user = this.userService.findUserByNickName(player.getName());
        user.setBreakBlock(user.getPlaceBlock() + 1);
    }

}
