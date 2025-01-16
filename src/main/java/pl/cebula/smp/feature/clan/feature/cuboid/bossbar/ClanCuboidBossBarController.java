package pl.cebula.smp.feature.clan.feature.cuboid.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.service.ClanService;

public class ClanCuboidBossBarController implements Listener {

    private final ClanService clanService;
    private final SurvivalPlugin survivalPlugin;

    public ClanCuboidBossBarController(ClanService clanService, SurvivalPlugin survivalPlugin) {
        this.clanService = clanService;
        this.survivalPlugin = survivalPlugin;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(this.survivalPlugin, () -> {
            if (!this.clanService.isLocationOnClanCuboid(player.getLocation())) {
                BossBar bossBar = ClanCuboidBossBarManager.getBossBar(player.getUniqueId());
                if (bossBar == null) return;
                bossBar.removePlayer(player);
                ClanCuboidBossBarManager.removeBossBar(player.getUniqueId());
            }
        }, 1L);
    }
}
