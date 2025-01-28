package pl.cebula.smp.feature.clan.feature.cuboid.visual;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.cebula.smp.SurvivalPlugin;
import pl.cebula.smp.feature.clan.Clan;
import pl.cebula.smp.feature.clan.feature.cuboid.ClanCuboidHearthLocation;
import pl.cebula.smp.feature.clan.service.ClanService;

public class ClanCuboidBorderParticleTask extends BukkitRunnable {

    private final ClanService clanService;
    private final SurvivalPlugin survivalPlugin;

    public ClanCuboidBorderParticleTask(ClanService clanService, SurvivalPlugin survivalPlugin) {
        this.clanService = clanService;
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(survivalPlugin, 30, 0);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Clan clan : clanService.getAllClans()) {
                ClanCuboidHearthLocation clanLocation = clan.getLocation();
                Location playerLocation = player.getLocation();
                if (isWithinDistance(playerLocation, clanLocation)) {
                    double centerX = clanLocation.getX();
                    double centerZ = clanLocation.getZ();
                    double size = 20;
                    for (double i = -size; i <= size; i += 1) {
                        spawnParticle(player, clan, centerX + i, centerZ - size);
                        spawnParticle(player, clan, centerX + i, centerZ + size);
                        spawnParticle(player, clan, centerX - size, centerZ + i);
                        spawnParticle(player, clan, centerX + size, centerZ + i);
                    }
                }
            }
        }
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

    private boolean isWithinDistance(Location playerLocation, ClanCuboidHearthLocation clanLocation) {
        double dx = clanLocation.getX() - playerLocation.getX();
        double dz = clanLocation.getZ() - playerLocation.getZ();
        return dx * dx + dz * dz <= (double) 50 * (double) 50;
    }
}