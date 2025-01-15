package pl.cebula.smp.feature.clan.feature.cuboid.visual;

import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;

public class CuboidBorderController implements Listener {

    private final ClanService clanService;
    private final ProtocolManager protocolManager;
    private final SurvivalPlugin survivalPlugin;

    public CuboidBorderController(ClanService clanService, ProtocolManager protocolManager, SurvivalPlugin survivalPlugin) {
        this.clanService = clanService;
        this.protocolManager = protocolManager;
        this.survivalPlugin = survivalPlugin;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Clan clan = this.clanService.findClanByLocation(player.getLocation());
        if (clan == null) return;
        Bukkit.getScheduler().runTaskLaterAsynchronously(this.survivalPlugin, () -> {
            if (this.clanService.isLocationOnClanCuboid(player.getLocation())) {
                CuboidBorderPacketHandler.sendBorderPacket(player, clan, this.protocolManager);
            } else {
                CuboidBorderPacketHandler.sendBorderPacket(player, player.getWorld(), this.protocolManager);
            }
        }, 1L);
    }
}
