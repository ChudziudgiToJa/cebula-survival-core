package pl.cebula.smp.feature.clan.feature.cuboid.bossbar;

import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.service.ClanService;

public class CuboidBossBarController implements Listener {

    private final ClanService clanService;
    private final ProtocolManager protocolManager;
    private final SurvivalPlugin survivalPlugin;

    public CuboidBossBarController(ClanService clanService, ProtocolManager protocolManager, SurvivalPlugin survivalPlugin) {
        this.clanService = clanService;
        this.protocolManager = protocolManager;
        this.survivalPlugin = survivalPlugin;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(this.survivalPlugin, () -> {
            if (!this.clanService.isLocationOnClanCuboid(player.getLocation())) {
                BossBar bossBar = CuboidBossBarManager.getBossBar(player.getUniqueId());
                if (bossBar == null) return;
                bossBar.removePlayer(player);
                CuboidBossBarManager.removeBossBar(player.getUniqueId());
            }
        }, 1L);
    }
}
