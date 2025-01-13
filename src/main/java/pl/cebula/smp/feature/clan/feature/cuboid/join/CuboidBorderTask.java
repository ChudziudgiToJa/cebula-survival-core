package pl.cebula.smp.feature.clan.feature.cuboid.join;

import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.feature.cuboid.CuboidPacketHandler;
import pl.cebula.smp.feature.clan.service.ClanService;

public class CuboidBorderTask extends BukkitRunnable {
    private final ClanService clanService;
    private final ProtocolManager protocolManager;

    public CuboidBorderTask(ClanService clanService, SurvivalPlugin survivalPlugin, ProtocolManager protocolManager) {
        this.clanService = clanService;
        this.protocolManager = protocolManager;
        this.runTaskTimerAsynchronously(survivalPlugin, 10, 0);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            Location playerLocation = player.getLocation();
            if (clanService.isBlockOnClanTerritory(playerLocation)) {
                Clan clan = clanService.findClanByLocation(playerLocation);
                if (clan != null) {
                    CuboidPacketHandler.sendBorderPacket(player, clan, this.protocolManager);
                }
            } else {
                CuboidPacketHandler.sendBorderPacket(player, player.getWorld() ,this.protocolManager);
            }
        });
    }
}
