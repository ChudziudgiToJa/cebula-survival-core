package pl.cebula.smp.feature.clan.feature.cuboid.visual;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.service.ClanService;

public class ClanCuboidBorderParticleTask extends BukkitRunnable {

    private final ClanService clanService;

    public ClanCuboidBorderParticleTask(ClanService clanService, final SurvivalPlugin survivalPlugin) {
        this.clanService = clanService;
        this.runTaskTimerAsynchronously(survivalPlugin, 20, 5);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Clan clan : clanService.getAllClans()) {
                Location clanLocation = clan.getClanLocation();
                if (isPlayerNearClan(player, clan.getClanLocation())) {
                    double centerX = clanLocation.getX() + 0.5;
                    double centerZ = clanLocation.getZ() + 0.5;
                    double size = 20;
                    for (double i = -size; i <= size; i += 0.3) {
                        spawnParticle(player, clan, centerX + i, centerZ - size);
                        spawnParticle(player, clan, centerX + i, centerZ + size);
                        spawnParticle(player, clan, centerX - size, centerZ + i);
                        spawnParticle(player, clan, centerX + size, centerZ + i);
                    }
                }
            }
        }
    }

    private boolean isPlayerNearClan(Player player, Location clanCenter) {
        double dx = player.getLocation().getX() - clanCenter.getX();
        double dz = player.getLocation().getZ() - clanCenter.getZ();
        return Math.sqrt(dx * dx + dz * dz) < 50;
    }

    private void spawnParticle(Player player, Clan clan, double x, double z) {
        Location particleLocation = new Location(player.getWorld(), x, player.getLocation().getY(), z);

        if (clan.getMemberArrayList().contains(player.getName()) || clan.getOwnerName().contains(player.getName())) {
            player.spawnParticle(Particle.DUST, particleLocation, 1, 0, 0, 0, 0,
                    new Particle.DustOptions(Color.fromRGB(0, 255, 0), 1));
        } else {
            player.spawnParticle(Particle.DUST, particleLocation, 1, 0, 0, 0, 0,
                    new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1));
        }
    }
}