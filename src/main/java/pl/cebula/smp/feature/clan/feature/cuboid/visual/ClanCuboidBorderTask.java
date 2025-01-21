package pl.cebula.smp.feature.clan.feature.cuboid.visual;

import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;

public class ClanCuboidBorderTask extends BukkitRunnable {
    private final ClanService clanService;
    private final ProtocolManager protocolManager;

    public ClanCuboidBorderTask(ClanService clanService, SurvivalPlugin survivalPlugin, ProtocolManager protocolManager) {
        this.clanService = clanService;
        this.protocolManager = protocolManager;
        this.runTaskTimerAsynchronously(survivalPlugin, 5, 0);
    }

    @Override
    public void run() {
        for (Clan clan : this.clanService.getAllClans()) {
            Location clanLocation = new Location(
                    Bukkit.getWorlds().getFirst(),
                    clan.getLocation().getX(),
                    clan.getLocation().getY(),
                    clan.getLocation().getZ()
            );
            for (Player player : clanLocation.getWorld().getPlayers()) {
                double distanceSquared = Math.pow(player.getLocation().getX() - clanLocation.getX(), 2)
                        + Math.pow(player.getLocation().getZ() - clanLocation.getZ(), 2);
                if (distanceSquared < Math.pow(80, 2)) {
                    if (this.clanService.isLocationOnClanCuboid(player.getLocation())) {
                        ClanCuboidBorderPacketHandler.sendBorderPacket(player, clan, this.protocolManager);
                    } else {
                        ClanCuboidBorderPacketHandler.sendBorderPacket(player, player.getWorld(), this.protocolManager);
                    }
                }
            }
        }
    }
}
