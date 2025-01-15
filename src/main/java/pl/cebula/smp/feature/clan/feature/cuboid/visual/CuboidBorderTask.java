package pl.cebula.smp.feature.clan.feature.cuboid.visual;

import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.service.ClanService;

public class CuboidBorderTask extends BukkitRunnable {
    private final ClanService clanService;
    private final ProtocolManager protocolManager;

    public CuboidBorderTask(ClanService clanService, SurvivalPlugin survivalPlugin, ProtocolManager protocolManager) {
        this.clanService = clanService;
        this.protocolManager = protocolManager;
        this.runTaskTimerAsynchronously(survivalPlugin, 5, 0);
    }

    @Override
    public void run() {
        this.clanService.getAllClans().forEach(clan -> {
            Location location = new Location(Bukkit.getWorlds().getFirst(), clan.getLocation().getX(), clan.getLocation().getY(), clan.getLocation().getZ());
            location.getWorld().getPlayers().stream()
                    .filter(nearbyPlayer -> nearbyPlayer.getLocation().distance(location) < 80)
                    .forEach(player -> {
                        if (this.clanService.isLocationOnClanCuboid(player.getLocation())) {
                            CuboidBorderPacketHandler.sendBorderPacket(player, clan, this.protocolManager);
                        } else {
                            CuboidBorderPacketHandler.sendBorderPacket(player, player.getWorld(), this.protocolManager);
                        }
                    });
        });
    }
}
